/*
 * The MIT License
 *
 * Copyright (c) 2014.  schors (https://github.com/flicus)
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software,
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED
 * TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
 * THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package org.schors.evlampia.tracker;

import org.apache.log4j.Logger;
import org.schors.evlampia.core.EvaExecutors;
import org.schors.evlampia.dao.DAOManager;
import org.schors.evlampia.json.fedex.FdxResponseWrapper;
import org.schors.evlampia.tracker.wsdl.axis.*;

import javax.xml.rpc.ServiceException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

public class TracksManager {

    private static final Logger log = Logger.getLogger(TracksManager.class);

    //RC213094378HK
    private static final Pattern pattern = Pattern.compile("\\D{2}\\d{9}\\D{2}");

    private Map<String, NamedTrackList> list = new ConcurrentHashMap<String, NamedTrackList>();
    private WebOperationHistoryStub binding = null;
    private String fileName;

    private Sender sender = null;

    public TracksManager(String fileName) {
        this.fileName = fileName;

        /*ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(fileName));
            list = (ConcurrentHashMap<String, NamedTrackList>) ois.readObject();
        } catch (Exception e) {
            log.error(e, e);
            list = new ConcurrentHashMap<String, NamedTrackList>();
        }*/

        try {
            binding = (WebOperationHistoryStub) new OperationHistory_ServiceLocator().getOperationHistory();
        } catch (ServiceException se) {
            if (se.getLinkedCause() != null)
                se.getLinkedCause().printStackTrace();
        } catch (Exception e) {
            log.error(e, e);
        }

        binding.setTimeout(60000);
        EvaExecutors.getInstance().getScheduler().scheduleAtFixedRate(new UpdateTracksTask(), 1, 2, TimeUnit.HOURS);
    }

    public TracksManager(String fileName, Sender sender) {
        this(fileName);
        this.sender = sender;
    }

    public void stop() {
        //executor.shutdown();
    }

    public interface Sender {
        public void sendMessage(String message, String to);
    }

    public void registerSender(Sender sender) {
        this.sender = sender;
    }

    private class UpdateTracksTask implements Runnable {

        public void run() {
            log.debug("Background update");
            try {

                for (Map.Entry<String, NamedTrackList> entry : list.entrySet()) {
                    for (Map.Entry<String, Track> trackEntry : entry.getValue().getTracks().entrySet()) {
                        Track track = trackEntry.getValue();
                        String oldStatus = track.getStatus();
                        checkTrack(track);
                        if (!oldStatus.equals(track.getStatus()) && sender != null) {
                            sender.sendMessage(new StringBuilder()
                                    .append(track.getName())
                                    .append(" | ")
                                    .append(track.getId())
                                    .append(" | ")
                                    .append(track.getStatus()).toString(), trackEntry.getKey());
                        }
                    }
                }

            } catch (Exception e) {
                log.error(e, e);
            }
        }
    }

    public void save() {
        DAOManager.getInstance().saveTrackList(fileName, list);
//        ObjectOutputStream oos = null;
//        try {
//            oos = new ObjectOutputStream(new FileOutputStream(fileName));
//            oos.writeObject(list);
//            oos.flush();
//            oos.close();
//        } catch (IOException e) {
//            log.error(e, e);
//        }
    }

    public void load() {
        DAOManager.getInstance().loadTrackList(fileName, list);
//        ObjectInputStream ois = null;
//        try {
//            ois = new ObjectInputStream(new FileInputStream(fileName));
//            list = (ConcurrentHashMap<String, NamedTrackList>) ois.readObject();
//        } catch (Exception e) {
//            log.error(e, e);
//            list = new ConcurrentHashMap<String, NamedTrackList>();
//        }

        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for (Map.Entry<String, NamedTrackList> entry : list.entrySet()) {
            sb.append(entry.getKey()).append(" : ").append(entry.getValue().toString()).append(", ");
        }
        sb.append("}");
        log.debug("### Loaded list: " + sb.toString());
    }

    public void addTrack(String listName, String newTrackId, String newTrackName) {
        log.debug(String.format("addTrack: %s, %s, %s", listName, newTrackId, newTrackName));
        NamedTrackList listToAddTo = list.get(listName);
        if (listToAddTo == null) {
            log.debug("no list exist, creating new");
            listToAddTo = new NamedTrackList(listName);
            list.put(listName, listToAddTo);
        }
        Track newTrack = new Track(newTrackName, newTrackId);
        checkTrack(newTrack);
        log.debug("adding new track");
        listToAddTo.addTrack(newTrack);
        save();
    }

    public void deleteTrack(String listName, String trackId) {
        log.debug(String.format("deleteTrack: %s, %s", listName, trackId));
        NamedTrackList listToRemoveFrom = list.get(listName);
        if (listToRemoveFrom != null) {
            listToRemoveFrom.removeTrack(trackId);
            if (listToRemoveFrom.size() == 0) {
                list.remove(listName);
            }
        }
        save();
    }

    public void deleteList(String listName) {
        list.remove(listName);
        save();
    }

    public List<String> getStatus(String listName) {
        log.debug(String.format("getStatus: %s", listName));
        List<String> result = null;
        NamedTrackList listToCheck = list.get(listName);
        if (listToCheck != null) {
            log.debug("named list found: " + listToCheck.size());
            result = new ArrayList<String>();
            for (Map.Entry<String, Track> entry : listToCheck.getTracks().entrySet()) {
                Track track = entry.getValue();
                StringBuilder sb = new StringBuilder()
                        .append(track.getName())
                        .append(" | ")
                        .append(track.getId())
                        .append(" | ")
                        .append(track.getStatus());
                result.add(sb.toString());
                log.debug("track: " + sb.toString());
            }
        }
        return result;
    }

    private synchronized void checkTrack(Track track) {
        log.debug(String.format("checkTrack: %s", track));

        if (pattern.matcher(track.getId()).matches()) { //this is rupost track id
            OperationHistoryRequest request = new OperationHistoryRequest();
            request.setBarcode(track.getId());
            request.setMessageType(0);

            OperationHistoryRecord[] result = null;

            try {
                result = binding.getOperationHistory(request, null);
            } catch (OperationHistoryFault ohf) {
                log.error(ohf, ohf);
            } catch (AuthorizationFault af) {
                log.error(af, af);
            } catch (RemoteException e) {
                log.error(e, e);
            }

            if (result != null && result.length > 0) {
                log.debug("returned resultlist size: " + result.length);
                OperationHistoryRecord lastRecord = result[result.length - 1];

                StringBuilder sb = new StringBuilder().append(String.format(Locale.ENGLISH, "%tc", lastRecord.getOperationParameters().getOperDate()))
                        .append(" | ")
                        .append(lastRecord.getAddressParameters().getOperationAddress().getDescription())
                        .append(" | ")
                        .append(lastRecord.getOperationParameters().getOperType().getName());

                log.debug("Post status: " + sb.toString());
                track.setStatus(sb.toString());
            }
        } else {    // its fedex track id for now
            try {
                URLConnection conn = new URL("https://www.fedex.com/trackingCal/track").openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
                conn.setRequestProperty("Cache-Control", "no-cache");
                conn.setRequestProperty("Pragma", "no-cache");

                conn.setDoOutput(true);
                OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
                out.write("data=");
                out.write(FedexTracker.getInstanse().makeRequest(track.getId()));
                out.write("&action=trackpackages&locale=en_US&format=json&version=99");
                out.write("\r\n");
                out.flush();
                out.close();
                String html = readStreamToString(conn.getInputStream(), "UTF-8");
                FdxResponseWrapper r = FedexTracker.getInstanse().parseResponse(html);

                if (!r.getTrackPackagesResponse().isSuccessful() && !"0".equals(r.getTrackPackagesResponse().getErrorList().get(0).getCode())) {
                    track.setStatus(r.getTrackPackagesResponse().getErrorList().get(0).getMessage());
                } else track.setStatus(r.getTrackPackagesResponse().getPackageList().get(0).getMainStatus()
                        + " | "
                        + r.getTrackPackagesResponse().getPackageList().get(0).getKeyStatus()
                        + " | " + r.getTrackPackagesResponse().getPackageList().get(0).getSubStatus());

            } catch (Exception e) {
                log.error(e, e);
            }
        }
    }

    private String readStreamToString(InputStream in, String encoding)
            throws IOException {
        StringBuffer b = new StringBuffer();
        InputStreamReader r = new InputStreamReader(in, encoding);
        int c;
        while ((c = r.read()) != -1) {
            b.append((char) c);
        }
        return b.toString();
    }
}

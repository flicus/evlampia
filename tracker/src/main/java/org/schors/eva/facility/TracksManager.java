/*
 * The MIT License (MIT)
 * Copyright (c) 2014 schors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.schors.eva.facility;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.log4j.Logger;
import org.schors.eva.Application;
import org.schors.eva.Version;
import org.schors.eva.dialog.Dialog;
import org.schors.eva.facility.axis.*;
import org.schors.eva.facility.fedex.FdxResponseWrapper;

import javax.xml.rpc.ServiceException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.net.URL;
import java.net.URLConnection;
import java.rmi.RemoteException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

@Facility(
        name = "trackManager",
        version = @Version(major = 1, minor = 0),
        dependsOn = {"executors", "redis"})
public class TracksManager extends AbstractFacility {
    private static final Logger log = Logger.getLogger(TracksManager.class);

    //RC213094378HK
    private static final Pattern pattern = Pattern.compile("\\D{2}\\d{9}\\D{2}");
    private Type trackType = new TypeToken<HashMap<String, HashMap<String, NamedTrackList>>>() {
    }.getType();


    private Map<String, Map<String, NamedTrackList>> list = new ConcurrentHashMap<>();
    private WebOperationHistoryStub binding = null;
    private Gson gson;
    private ScheduledFuture updateTask;

    public TracksManager(Application application) {
        super(application);
        gson = new Gson();
    }

    @Override
    public void start() {
        log.debug("start");
        status = FacilityStatus.STARTING;
        try {
            binding = (WebOperationHistoryStub) new OperationHistory_ServiceLocator().getOperationHistory();
            binding.setTimeout(60000);
            EvaExecutors evaExecutors = getFacility(EvaExecutors.class);
            updateTask = evaExecutors.getScheduler().scheduleAtFixedRate(new UpdateTracksTask(), 1, 2, TimeUnit.HOURS);
            status = FacilityStatus.STARTED;
        } catch (ServiceException se) {
            log.error("Unable to start", se);
            status = FacilityStatus.ERROR;
        } catch (Exception e) {
            log.error("Unable to start", e);
            status = FacilityStatus.ERROR;
        }
    }

    @Override
    public void stop() {
        log.debug("stop");
        updateTask.cancel(true);
        status = FacilityStatus.STOPPED;
    }

    public void save() {
        Redis redis = getFacility(Redis.class);
        try {
            String json = gson.toJson(list, trackType);
            redis.getJedis().set("tracks", json);
        } catch (Exception e) {
            log.error(e, e);
        }
    }

    public void load() {
        Redis redis = getFacility(Redis.class);

        list.clear();
        try {
            String json = redis.getJedis().get("tracks");
            HashMap<String, HashMap<String, NamedTrackList>> _res = gson.fromJson(json, trackType);
            for (Map.Entry<String, HashMap<String, NamedTrackList>> entry : _res.entrySet()) {
                ConcurrentHashMap<String, NamedTrackList> a1 = new ConcurrentHashMap<>();
                list.put(entry.getKey(), a1);
                for (Map.Entry<String, NamedTrackList> entry2 : entry.getValue().entrySet()) {
                    a1.put(entry2.getKey(), entry2.getValue());
                }
            }
        } catch (Exception e) {
            log.error(e, e);
        }

        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for (Map.Entry<String, Map<String, NamedTrackList>> entry : list.entrySet()) {
            sb.append("{").append(entry.getKey()).append(",");
            for (Map.Entry<String, NamedTrackList> entry2 : entry.getValue().entrySet()) {
                sb.append(entry2.getKey()).append(" : ").append(entry2.getValue().toString()).append(", ");
            }
            sb.append("}");
        }
        sb.append("}");
        log.debug("### Loaded list: " + sb.toString());
    }

    public void addTrack(String endpoint, String listName, String newTrackId, String newTrackName) {
        log.debug(String.format("addTrack: %s, %s, %s, %s", endpoint, listName, newTrackId, newTrackName));
        Map<String, NamedTrackList> endpointList = list.get(endpoint);
        if (endpointList == null) {
            endpointList = new ConcurrentHashMap<>();
            list.put(endpoint, endpointList);
        }

        NamedTrackList listToAddTo = endpointList.get(listName);
        if (listToAddTo == null) {
            log.debug("no list exist, creating new");
            listToAddTo = new NamedTrackList(listName);
            endpointList.put(listName, listToAddTo);
        }
        Track newTrack = new Track(newTrackName, newTrackId);
        checkTrack(newTrack);
        log.debug("adding new track: " + newTrack);
        listToAddTo.addTrack(newTrack);
        save();
    }

    public void deleteTrack(String endpoint, String listName, String trackId) {
        log.debug(String.format("deleteTrack: %s, %s, %s", endpoint, listName, trackId));
        Map<String, NamedTrackList> endpointList = list.get(endpoint);
        if (endpointList == null) return;

        NamedTrackList listToRemoveFrom = endpointList.get(listName);
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

    public List<String> getStatus(String endpoint, String listName) {
        log.debug(String.format("getStatus: %s, %s", endpoint, listName));
        List<String> result = null;
        Map<String, NamedTrackList> endpointList = list.get(endpoint);
        if (endpointList == null) {
            return result;
        }

        NamedTrackList listToCheck = endpointList.get(listName);
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

    private class UpdateTracksTask implements Runnable {

        public void run() {
            log.debug("Background update");
            try {
                for (Map.Entry<String, Map<String, NamedTrackList>> entry : list.entrySet()) {
                    for (Map.Entry<String, NamedTrackList> entry2 : entry.getValue().entrySet()) {
                        for (Map.Entry<String, Track> trackEntry : entry2.getValue().getTracks().entrySet()) {
                            Track track = trackEntry.getValue();
                            String oldStatus = track.getStatus();
                            checkTrack(track);
                            if (!oldStatus.equals(track.getStatus())) {
                                Dialog dialog = createDialog(entry.getKey());
                                if (dialog != null) {
                                    dialog.sendMessage(new StringBuilder()
                                            .append(track.getName())
                                            .append(" | ")
                                            .append(track.getId())
                                            .append(" | ")
                                            .append(track.getStatus()).toString());
                                } else {
                                    log.warn("Unable to create dialog for: " + entry.getKey());
                                }
                            }
                        }
                    }
                }
            } catch (Exception e) {
                log.error(e, e);
            }
        }
    }
}

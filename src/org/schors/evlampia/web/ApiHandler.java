package org.schors.evlampia.web;

import com.google.gson.Gson;
import org.apache.http.*;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpRequestHandler;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.schors.evlampia.dao.DAOManager;
import org.schors.evlampia.json.GetCloudRes;
import org.schors.evlampia.json.SearchReq;
import org.schors.evlampia.json.SearchRes;
import org.schors.evlampia.model.TagItem;
import org.schors.evlampia.search.LogEntry;
import org.schors.evlampia.search.SearchManager;
import org.schors.evlampia.search.SearchResult;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ApiHandler implements HttpRequestHandler {

    private final static Logger log = Logger.getLogger(ApiHandler.class);
    private static final Pattern pattern = Pattern.compile("/eva/api/(.+)/?");
    private final Gson gson = new Gson();

    public ApiHandler() throws Exception {

    }

    @Override
    public void handle(HttpRequest request, HttpResponse response, HttpContext context) throws HttpException, IOException {
        String target = (String)context.getAttribute("base");//request.getRequestLine().getUri();
        String method = request.getRequestLine().getMethod().toUpperCase();
        String command = null;
        Matcher m = pattern.matcher(target);
        if (m.find())
            command = m.group(1);

        switch (method) {
            case "GET":
                serveRead(command == null ? null : command.toLowerCase(), request, response, context);
                break;
            case "POST":
                serveWrite(command == null ? null : command.toLowerCase(), request, response, context);
                break;
            default:
                response.setStatusCode(HttpStatus.SC_METHOD_NOT_ALLOWED);
                response.setEntity(Util.makeMessageBody("Unsupported HTTP method"));
                log.debug("Unsupported HTTP method: " + method);
        }
    }

    private void serveWrite(String command, HttpRequest request, HttpResponse response, HttpContext context) throws IOException {
        StringEntity entity;
        switch (command) {
            case "search":
                HttpEntity e = ((HttpEntityEnclosingRequest) request).getEntity();
                String data = null;
                try {
                    data = EntityUtils.toString(e);
                } catch (IOException e1) {
                    log.error(e1, e1);
                }
                SearchReq req = gson.fromJson(data, SearchReq.class);

                SearchRes searchRes = new SearchRes();
                DAOManager.getInstance().updateTag(req.getToSearch());
                SearchResult<LogEntry> result = SearchManager.getInstanse().search(req.getToSearch(), req.getCount(), req.getStart());

                searchRes.setResult("ok");
                searchRes.setSearchResult(result);

                response.setStatusCode(HttpStatus.SC_OK);
                String aaa = gson.toJson(searchRes);

                log.debug("search: " + aaa);

                entity = new StringEntity(aaa, ContentType.create("application/json", "UTF-8"));
                response.setEntity(entity);
                break;
            default:
                response.setStatusCode(HttpStatus.SC_BAD_REQUEST);
                response.setEntity(Util.makeMessageBody("Unknown api request"));
                log.debug("Unknown api request: " + command);
        }
    }

    private void serveRead(String command, HttpRequest request, HttpResponse response, HttpContext context) {
        StringEntity entity;
        switch (command) {
            case "getcloud":
                //TagItem[] items = new TagItem[]{new TagItem("говно", 15), new TagItem("трутень", 13), new TagItem("орда", 13), new TagItem("колбаса", 10), new TagItem("портянки", 7), new TagItem("путин", 6), new TagItem("мед", 4), new TagItem("лошадь", 2)};
                List<TagItem> items = DAOManager.getInstance().getTags();
                GetCloudRes getCloudRes = new GetCloudRes(items);
                getCloudRes.setResult("ok");

                response.setStatusCode(HttpStatus.SC_OK);

                String aaa = gson.toJson(getCloudRes);
                log.debug("search: " + aaa);

                entity = new StringEntity(aaa, ContentType.create("application/json", "UTF-8"));
                response.setEntity(entity);
                break;
            default:
                response.setStatusCode(HttpStatus.SC_BAD_REQUEST);
                response.setEntity(Util.makeMessageBody("Unknown api request"));
                log.debug("Unknown api request: " + command);
        }

    }


}

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

package org.schors.evlampia.web;

import org.apache.http.*;
import org.apache.http.message.BasicHttpRequest;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpRequestHandler;
import org.apache.log4j.Logger;
import org.schors.evlampia.core.Jbot;
import org.schors.evlampia.core.TokenManager;

import java.io.IOException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RootHandler implements HttpRequestHandler {

    private static final Logger log = Logger.getLogger(RootHandler.class);
    private static final Pattern pattern = Pattern.compile("/eva/(.+?)/.+");

    private StaticContentHandler staticContentHandler;
    private ApiHandler apiHandler;

    public RootHandler(String docRoot) {
        staticContentHandler = new StaticContentHandler(docRoot);
        try {
            apiHandler = new ApiHandler();
        } catch (Exception e) {
            log.error(e, e);
        }
    }

    @Override
    public void handle(HttpRequest request, HttpResponse response, HttpContext context) throws HttpException, IOException {
        log.debug("Root handler");
        log.debug(requestInfo(request));
        String target = request.getRequestLine().getUri();
        String[] urlStrings = target.split("\\?");

        if ("/eva/".equals(urlStrings[0])) {
            Header[] headers = request.getHeaders("Cookie");
            if (headers != null && headers.length > 0) {
                Map<String, String> cookies = Util.parseQueryString(headers[0].getValue(), ";");
                String evaid = cookies.get("evaid");
                if (TokenManager.getInstance().checkToken(evaid) != null) {
                    HttpRequest r = new BasicHttpRequest("get", "/eva/static/1.html");
                    context.setAttribute("base", "/eva/static/1.html");
                    staticContentHandler.handle(r, response, context);
                    return;
                }
            }
            if (urlStrings.length < 2) {
                response.setStatusCode(HttpStatus.SC_FORBIDDEN);
                response.setEntity(Util.makeMessageBody("Обнови билет (!т)"));
                return;
            }
            Map<String, String> params = Util.parseQueryString(urlStrings[1], "&");
            String evaid = params.get("evaid");
            if (TokenManager.getInstance().checkToken(evaid) != null) {
                HttpRequest r = new BasicHttpRequest("get", "/eva/static/1.html");
                context.setAttribute("base", "/eva/static/1.html");
                response.addHeader("Set-Cookie", String.format("evaid=%s", evaid));
                staticContentHandler.handle(r, response, context);
            } else {
                response.setStatusCode(HttpStatus.SC_FORBIDDEN);
                response.setEntity(Util.makeMessageBody("Обнови билет (!т)"));
            }
        } else {
            Header[] headers = request.getHeaders("Cookie");
            if (headers != null && headers.length > 0) {
                Map<String, String> cookies = Util.parseQueryString(headers[0].getValue(), ";");
                String evaid = cookies.get("evaid");
                if (TokenManager.getInstance().checkToken(evaid) == null) {
                    response.setStatusCode(HttpStatus.SC_FORBIDDEN);
                    return;
                }
            }
            context.setAttribute("base", urlStrings[0]);
            Matcher m = pattern.matcher(target);
            if (m.find()) {
                target = m.group(1);
            }
            switch (target) {
                case "api":
                    apiHandler.handle(request, response, context);
                    break;
                case "static":
                    staticContentHandler.handle(request, response, context);
                    break;
                default:
                    response.setStatusCode(HttpStatus.SC_SERVICE_UNAVAILABLE);
                    response.setEntity(Util.makeMessageBody("Unknown service requested"));
                    log.debug("Unknown service requested: " + target);
            }
        }
    }

    public static String requestInfo(HttpRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("RequestLine: %s", request.getRequestLine())).append(Jbot.newline);
        for (Header header : request.getAllHeaders()) {
            sb.append(header.getName()).append(":").append(header.getValue()).append(Jbot.newline);
        }
        return sb.toString();
    }
}

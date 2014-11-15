/*
 * The MIT License (MIT)
 *
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

package org.schors.evlampia.web;

import org.apache.http.*;
import org.apache.http.entity.FileEntity;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpRequestHandler;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.Locale;

public class StaticContentHandler implements HttpRequestHandler {
    private static final Logger log = Logger.getLogger(StaticContentHandler.class);
    private final String docRoot;

    public StaticContentHandler(final String docRoot) {
        super();
        this.docRoot = docRoot;
    }

    @Override
    public void handle(HttpRequest request, HttpResponse response, HttpContext context) throws HttpException, IOException {
        log.debug("Static handler");
        String method = request.getRequestLine().getMethod().toUpperCase(Locale.ENGLISH);
        if (!method.equals("GET")) {
            throw new MethodNotSupportedException(method + " method not supported");
        }
        String target = (String) context.getAttribute("base");//request.getRequestLine().getUri();

        if (request instanceof HttpEntityEnclosingRequest) {
            HttpEntity entity = ((HttpEntityEnclosingRequest) request).getEntity();
            byte[] entityContent = EntityUtils.toByteArray(entity);
            log.debug("Incoming entity content (bytes): " + entityContent.length);
        }

        final File file = new File(this.docRoot, URLDecoder.decode(target, "UTF-8"));
        if (!file.exists()) {
            response.setStatusCode(HttpStatus.SC_NOT_FOUND);
            response.setEntity(Util.makeMessageBody(String.format("File %s not found", file.getName())));
            log.warn("File " + file.getPath() + " not found");
        } else if (!file.canRead() || file.isDirectory()) {
            response.setStatusCode(HttpStatus.SC_FORBIDDEN);
            response.setEntity(Util.makeMessageBody("Access denied"));
            log.warn("Cannot read file " + file.getPath());
        } else {
            response.setStatusCode(HttpStatus.SC_OK);
            FileEntity body = new FileEntity(file, Util.getContentType(file));
            response.setEntity(body);
            log.debug("Serving file " + file.getPath());
        }
    }
}

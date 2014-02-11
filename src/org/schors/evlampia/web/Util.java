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

import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

public class Util {

    public static ContentType getContentType(File file) {
        ContentType result = null;
        String extension = null;
        int dotIndex = file.getAbsolutePath().lastIndexOf('.');
        if (dotIndex >= 0) {
            extension = file.getAbsolutePath().substring(dotIndex + 1, (int) file.getAbsolutePath().length());
        }
        if (extension != null) {
            switch (extension) {
                case "html":
                case "htm":
                    result = ContentType.create("text/html", "UTF-8");
                    break;
                case "css":
                    result = ContentType.create("text/css", "UTF-8");
                    break;
                case "js":
                    result = ContentType.create("text/javascript", "UTF-8");
                    break;
            }
        }
        return result;
    }

    public static StringEntity makeMessageBody(String text) {
        return new StringEntity(String.format("<html><body><h1>%s</h1></body></html>", text), ContentType.create("text/html", "UTF-8"));
    }

    public static Map<String, String> parseQueryString(final String url, String token) throws UnsupportedEncodingException {
        final Map<String, String> qps = new TreeMap<String, String>();
        final StringTokenizer pairs = new StringTokenizer(url, token);
        while (pairs.hasMoreTokens()) {
            final String pair = pairs.nextToken();
            final StringTokenizer parts = new StringTokenizer(pair, "=");
            final String name = URLDecoder.decode(parts.nextToken(), "ISO-8859-1");
            final String value = URLDecoder.decode(parts.nextToken(), "ISO-8859-1");
            qps.put(name, value);
        }
        return qps;
    }
}

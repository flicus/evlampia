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

import org.apache.http.ConnectionClosedException;
import org.apache.http.HttpException;
import org.apache.http.HttpServerConnection;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpService;
import org.apache.log4j.Logger;

import java.io.IOException;

public class Worker implements Runnable {
    private static final Logger log = Logger.getLogger(Worker.class);
    private final HttpService httpservice;
    private final HttpServerConnection conn;

    public Worker(final HttpService httpService, final HttpServerConnection httpServerConnection) {
        super();
        this.httpservice = httpService;
        this.conn = httpServerConnection;
    }

    @Override
    public void run() {
        log.debug("New connection thread");
        HttpContext context = new BasicHttpContext(null);
        try {
            while (!Thread.interrupted() && this.conn.isOpen()) {
                this.httpservice.handleRequest(this.conn, context);
            }
        } catch (ConnectionClosedException ex) {
            log.error("Client closed connection");
        } catch (IOException ex) {
            log.error("I/O error: ", ex);
        } catch (HttpException ex) {
            log.error("Unrecoverable HTTP protocol violation: ", ex);
        } finally {
            try {
                this.conn.shutdown();
                log.debug("Connection closed");
            } catch (IOException ignore) {
            }
        }
    }
}
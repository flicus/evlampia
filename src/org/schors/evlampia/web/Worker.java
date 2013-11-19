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
        log.debug("New connection thread" );
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
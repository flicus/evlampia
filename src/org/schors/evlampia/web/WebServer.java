package org.schors.evlampia.web;

import org.apache.http.HttpResponseInterceptor;
import org.apache.http.impl.DefaultConnectionReuseStrategy;
import org.apache.http.impl.DefaultHttpResponseFactory;
import org.apache.http.impl.DefaultHttpServerConnection;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.params.SyncBasicHttpParams;
import org.apache.http.protocol.*;
import org.apache.log4j.Logger;
import org.schors.evlampia.Configuration;
import org.schors.evlampia.ConfigurationManager;
import org.schors.evlampia.EvaExecutors;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.ServerSocket;
import java.net.Socket;

public class WebServer implements Runnable {

    private static final Logger log = Logger.getLogger(WebServer.class);
    private final ServerSocket serverSocket;
    private final HttpParams httpParams;
    private final HttpService httpService;

    public WebServer() throws IOException {

        Configuration cfg = ConfigurationManager.getInstance().getConfiguration();
        this.serverSocket = new ServerSocket(cfg.getSearch().getPort());
        this.httpParams = new SyncBasicHttpParams();
        this.httpParams.setIntParameter(CoreConnectionPNames.SO_TIMEOUT, 5000)
                .setIntParameter(CoreConnectionPNames.SOCKET_BUFFER_SIZE, 8 * 1024)
                .setBooleanParameter(CoreConnectionPNames.STALE_CONNECTION_CHECK, false)
                .setBooleanParameter(CoreConnectionPNames.TCP_NODELAY, true);

        HttpProcessor httpProcessor = new ImmutableHttpProcessor(new HttpResponseInterceptor[]{
                new ResponseDate(),
                new ResponseServer(),
                new ResponseContent(),
                new ResponseConnControl()
        });

        HttpRequestHandlerRegistry registry = new HttpRequestHandlerRegistry();
        registry.register("/eva/*", new RootHandler(cfg.getSearch().getWebRoot()));
        //registry.register("/static/*", new StaticContentHandler(docRoot));
        //registry.register("/api/*", new ApiHandler());

        this.httpService = new HttpService(httpProcessor, new DefaultConnectionReuseStrategy(), new DefaultHttpResponseFactory(), registry, this.httpParams);

    }

    @Override
    public void run() {
        log.debug(String.format("Listening on port: %d", this.serverSocket.getLocalPort()));
        while (!Thread.interrupted()) {
            try {
                Socket socket = this.serverSocket.accept();
                DefaultHttpServerConnection connection = new DefaultHttpServerConnection();
                log.debug(String.format("Incoming connection from: %s", socket.getInetAddress().toString()));
                connection.bind(socket, this.httpParams);

                Runnable worker = new Worker(this.httpService, connection);
                EvaExecutors.getInstance().getExecutor().execute(worker);

            } catch (InterruptedIOException e) {
                break;
            } catch (IOException e) {
                log.error(e, e);
                break;
            }
        }
    }


}

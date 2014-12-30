/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 schors
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all
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

package org.schors.eva.core;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.concurrent.Executors;

public class PluginManager implements PluginObserver {
    private static final Logger log = Logger.getLogger(PluginManager.class);
    private final JarClassLoader loader;
    private Watcher watcher;

    public PluginManager(PluginLoader pluginLoader) {
        loader = new JarClassLoader(pluginLoader);
        watcher = new Watcher("c:\\temp\\jars");
        Executors.newSingleThreadExecutor().submit(watcher);
    }

    @Override
    public void onPluginDiscovery() {

    }

    public class Watcher implements Runnable {

        private Path root;
        private WatchService watchService;

        public Watcher(String path) {
            File f = new File(path);
            if (!f.exists() || !f.isDirectory())
                throw new RuntimeException("Wrong path to monitor: " + path);

            root = f.toPath();
            FileSystem fs = root.getFileSystem();
            try {
                watchService = fs.newWatchService();
                root.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);
            } catch (IOException e) {
                e.printStackTrace();
            }
//            finally {
//                try {
//                    watchService.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
        }

        @Override
        public void run() {
            log.debug("start watcher");
            boolean done = false;
            WatchKey key = null;
            while (!done) {
                try {
                    key = watchService.take();
                } catch (InterruptedException e) {
                    done = true;
                    continue;
                }
                WatchEvent.Kind<?> kind = null;
                for (WatchEvent<?> watchEvent : key.pollEvents()) {
                    kind = watchEvent.kind();
                    if (StandardWatchEventKinds.OVERFLOW == kind) {
                        continue;
                    } else if (StandardWatchEventKinds.ENTRY_CREATE == kind) {
                        Path newPath = ((WatchEvent<Path>) watchEvent).context();
                        log.debug("Watcher:: new entry:: " + root.resolve(newPath));
                        try {
                            loader.loadJar(root.resolve(newPath).toString());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                if (!key.reset()) {
                    done = true;
                }
            }
        }
    }
}

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

package org.schors.evlampia.search;

import org.apache.log4j.Logger;
import org.apache.lucene.store.Directory;
import org.joda.time.DateTime;
import org.joda.time.DateTimeFieldType;
import org.schors.evlampia.core.ConfigurationManager;
import org.schors.evlampia.core.EvaExecutors;
import org.schors.evlampia.dao.vbotDAOHTMLImplementation;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class SearchManager {

    private static final Logger log = Logger.getLogger(SearchManager.class);
    private Searcher<LogEntry, LogEntryAggregator> searcher;
    private LuceneIndexer indexer;
    private Directory directory;

    public SearchManager() {
    }

    public static SearchManager getInstanse() {
        return Singleton.instanse;
    }

    public void init(Directory d) {
        this.directory = d;
        searcher = Searcher.create(directory);
        indexer = new LuceneIndexer();

        DateTime dt = new DateTime();
        int hours = dt.get(DateTimeFieldType.hourOfDay());

        EvaExecutors.getInstance().getScheduler().scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                vbotDAOHTMLImplementation.getInstance().flush();
                try {
                    SearchManager.getInstanse().updateIndex(true);
                } catch (IOException e) {
                    log.error(e, e);
                }
            }
        }, 24 - hours, 24, TimeUnit.HOURS);
    }

    public void updateIndex(boolean recreate) throws IOException {
        try {
            File logsDir = new File(ConfigurationManager.getInstance().getConfiguration().getLogsPath());
            DirectoryTraverser traverser = new DirectoryTraverser(logsDir);
            indexer.open(directory, recreate);
            File file = null;
            while ((file = traverser.getNextFile()) != null) {
                LogFileParser p = null;
                try {
                    p = new LogFileParser(file);
                } catch (Exception e) {
                    log.error(e, e);
                }
                if (p != null) {
                    LogEntry entry;
                    while ((entry = p.getNextEntry()) != null) {
                        indexer.add(entry);
                    }
                }
            }
        } finally {
            indexer.close();
            searcher.reopenIndex();
        }
    }

    public SearchResult<LogEntry> search(String toSearch, int count, int start) {
        SearchResult<LogEntry> result = null;
        try {
            result = searcher.Result(toSearch, count, start);
        } catch (Exception e) {
            log.error(e, e);
        }
        return result;
    }

    public void shutDown() {
        try {
            indexer.shutDown();
            searcher.shutDown();
            directory.close();
        } catch (IOException e) {
            //booo
        }
    }

    private static class Singleton {
        public static SearchManager instanse = new SearchManager();
    }
}

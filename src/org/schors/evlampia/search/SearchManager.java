package org.schors.evlampia.search;

import org.apache.log4j.Logger;
import org.apache.lucene.store.Directory;
import org.schors.evlampia.ConfigurationManager;

import java.io.File;
import java.io.IOException;

public class SearchManager {

    private static final Logger log = Logger.getLogger(SearchManager.class);
    private Searcher<LogEntry, LogEntryAggregator> searcher;
    private LuceneIndexer indexer;
    private Directory directory;

    public SearchManager() {
    }

    public void init(Directory d) {
        this.directory = d;
        searcher = Searcher.create(directory);
        indexer = new LuceneIndexer();
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

    public static SearchManager getInstanse() {
        return Singleton.instanse;
    }
}

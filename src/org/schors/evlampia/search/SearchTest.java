package org.schors.evlampia.search;


import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.File;

public class SearchTest {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            DirectoryTraverser traverser = new DirectoryTraverser(new File("d:\\tmp\\lucene\\logs"));
            LuceneIndexer indexer = new LuceneIndexer();
            Directory d = FSDirectory.open(new File("d:\\tmp\\lucene\\index"));
            indexer.open(d, true);
            File file = null;
            while ((file = traverser.getNextFile()) != null) {
                LogFileParser p = new LogFileParser(file);
                if (p != null) {
                    LogEntry entry;
                    while ((entry = p.getNextEntry()) != null) {
                        //System.out.println(entry);
                        indexer.add(entry);
                    }
                }
            }
            indexer.close();

            /*Directory d = FSDirectory.open(new File("f:\\tmp\\lucene\\index"));
            Searcher<LogEntry, LogEntryAggregator> searcher = new Searcher<LogEntry, LogEntryAggregator>(d, LogEntryAggregator.class);
            String story = "tmp";
            System.out.println("Enter: ");
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
            do {
                story = br.readLine();
                if (story == null || story.length() == 0) {
                    continue;
                }
                SearchResult<LogEntry> result = searcher.Result(story.trim(), 5, 0);
                System.out.println(result.hits);
                for (LogEntry entry : result.getItems()) {
                    System.out.println(entry);
                }
            } while (story != null && story.length() != 0);
         */
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

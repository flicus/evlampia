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

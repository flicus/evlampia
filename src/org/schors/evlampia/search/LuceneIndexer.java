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

package org.schors.evlampia.search;

import org.apache.log4j.Logger;
import org.apache.lucene.analysis.ru.RussianAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Index;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.util.Version;

import java.io.IOException;

public class LuceneIndexer {

    private static final Logger log = Logger.getLogger(LuceneIndexer.class);

    private IndexWriter indexWriter;
    private IndexWriterConfig config;

    public LuceneIndexer() {

    }

    public void open(Directory directory, boolean recreate) throws IOException {
        config = new IndexWriterConfig(Version.LUCENE_36, new RussianAnalyzer(Version.LUCENE_36));
        config.setOpenMode(recreate ? IndexWriterConfig.OpenMode.CREATE : IndexWriterConfig.OpenMode.APPEND);
        indexWriter = new IndexWriter(directory, config);
    }

    public void close() {
        try {
            synchronized (LuceneIndexer.class) {
                if (indexWriter != null) {
                    indexWriter.close();
                    indexWriter = null;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void add(LogEntry logEntry) {
        Document doc = new Document();
        doc.add(new Field(Constants.url, logEntry.getUrl(), Store.YES, Index.NO));
        doc.add(new Field(Constants.author, logEntry.getAuthor(), Store.YES, Index.ANALYZED));
        doc.add(new Field(Constants.message, logEntry.getMessage(), Store.YES, Index.ANALYZED, Field.TermVector.WITH_POSITIONS_OFFSETS));
        doc.add(new Field(Constants.date, logEntry.getDate(), Store.YES, Index.NO));

        try {
            synchronized (LuceneIndexer.class) {
                if (indexWriter != null) {
                    indexWriter.addDocument(doc);
                }
            }

        } catch (Exception e) {
            log.error(e);
        }
    }

    public Directory getDirectory() {
        if (indexWriter != null)
            return indexWriter.getDirectory();
        return null;
    }

    public IndexWriter getWriter() {
        return indexWriter;
    }

    public void shutDown() {
        try {
            indexWriter.close();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

}

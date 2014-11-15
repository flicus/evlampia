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
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.util.Version;

import java.io.IOException;
import java.util.ArrayList;

abstract class Aggregator<T> {
    protected Query query;
    protected IndexSearcher indexSearcher;

    abstract T aggregate(ScoreDoc sd) throws Exception;
}

public class Searcher<T, A extends Aggregator<T>> implements SearchResult.IResult<T> {

    private static final Logger log = Logger.getLogger(Searcher.class);
    private static volatile IndexReader indexReader;
    private final Class<A> classA;
    private final boolean docsScoreInOrder;
    private IndexSearcher indexSearcher;
    private QueryParser parser = new MultiFieldQueryParser(Version.LUCENE_36, new String[]{Constants.author, Constants.message}, Constants.analyzer);

    public Searcher(Directory directory, Class<A> classA) throws Exception {
        this(directory, classA, false);
    }

    public Searcher(Directory directory, Class<A> classA, boolean docsScoreInOrder) throws Exception {
        this.classA = classA;
        this.docsScoreInOrder = docsScoreInOrder;

        parser.setDefaultOperator(QueryParser.AND_OPERATOR);
        parser.setAllowLeadingWildcard(true);
        parser.setMultiTermRewriteMethod(MultiTermQuery.SCORING_BOOLEAN_QUERY_REWRITE);

        if (indexReader == null) {
            synchronized (Searcher.class) {
                if (indexReader == null) {
                    indexReader = IndexReader.open(directory);
                }
            }
        }
        indexSearcher = new IndexSearcher(indexReader);
    }

    public static Searcher<LogEntry, LogEntryAggregator> create(Directory directory) {
        Searcher<LogEntry, LogEntryAggregator> s = null;
        try {
            s = new Searcher<LogEntry, LogEntryAggregator>(directory, LogEntryAggregator.class);
        } catch (Exception ex) {
            log.error(ex, ex);
        }
        return s;
    }

    public SearchResult<T> Result(String story, int count, int start) throws Exception {
        int nDocs = start + count;

        //Query query = parser.parse(QueryParser.escape(story));
        Query query = parser.parse(story);
        TopScoreDocCollector collector = TopScoreDocCollector.create(Math.max(nDocs, 1), docsScoreInOrder);
        indexSearcher.search(query, collector);
        TopDocs topDocs = collector.topDocs();

        if (nDocs <= 0)
            return new SearchResult<T>(topDocs.totalHits, null);

        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        int length = scoreDocs.length - start;

        if (length <= 0)
            return new SearchResult<T>(topDocs.totalHits, null);

        ArrayList<T> items = new ArrayList<T>(length);
        A aggregator = classA.newInstance();
        aggregator.query = query;
        aggregator.indexSearcher = indexSearcher;

        for (int i = start; i < scoreDocs.length; i++) {
            items.add(i - start, aggregator.aggregate(scoreDocs[i]));
        }

        return new SearchResult<T>(topDocs.totalHits, items);
    }

    public void reopenIndex() {
        try {
            indexReader.reopen();
        } catch (IOException e) {
            log.error(e, e);
        }
    }

    public void shutDown() {
        try {
            indexReader.close();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}

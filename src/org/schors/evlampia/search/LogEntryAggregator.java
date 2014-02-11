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

import org.apache.lucene.document.Document;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.highlight.*;

import java.io.IOException;

public class LogEntryAggregator extends Aggregator<LogEntry> {

    private Highlighter highlighter;

    private final String tryHighlight(String text, String[] fields)
            throws IOException, InvalidTokenOffsetsException {

        if (null == text)
            return null;

        if (null == highlighter) {
            final QueryScorer scorer = new QueryScorer(query.rewrite(indexSearcher.getIndexReader()));
            highlighter = new Highlighter(new SimpleHTMLFormatter("<span class='highlighted'>", "</span>"), scorer);
            highlighter.setTextFragmenter(new SimpleSpanFragmenter(scorer, 330));
        }

        for (final String field : fields) {
            final String highlighted = highlighter.getBestFragment(Constants.analyzer, field, text);
            if (null != highlighted)
                return highlighted;
        }

        return text;
    }

    LogEntry aggregate(ScoreDoc sd) throws Exception {
        final Document doc = indexSearcher.doc(sd.doc);
        //final String author = doc.get(Constants.author);
        final String message = doc.get(Constants.message);
        final String hl = tryHighlight(message, new String[]{Constants.message});

        return new LogEntry(doc.get(Constants.author), hl/*doc.get(Constants.message)*/, doc.get(Constants.date), "http://0xffff.net/logs/vnations@conference.jabber.ru" + doc.get(Constants.url), sd.score);
    }
}

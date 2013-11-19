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

        return new LogEntry(doc.get(Constants.author), hl/*doc.get(Constants.message)*/, doc.get(Constants.date), "http://0xffff.net/logs/vnations@conference.jabber.ru"+doc.get(Constants.url), sd.score);
    }
}

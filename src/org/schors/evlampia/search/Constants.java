package org.schors.evlampia.search;

import org.apache.lucene.analysis.ru.RussianAnalyzer;
import org.apache.lucene.util.Version;

public abstract class Constants {
    public static final String author = "author";
    public static final String message = "message";
    public static final String url = "url";
    public static final String date = "date";
    public final static RussianAnalyzer analyzer = new RussianAnalyzer(Version.LUCENE_36);
}

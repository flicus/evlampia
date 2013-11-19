/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.schors.evlampia.search;

import java.io.*;
import java.net.URLDecoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogFileParser {

    //&lt;tirvindi&gt;
    private static final Pattern pattern = Pattern.compile("<a\\s*id=\".*?\"\\s*href=\"(.*?)\">\\s*\\[(\\d{2}):(\\d{2}):(\\d{2})\\]\\s*</a>\\s*</span>\\s*<span\\s*class=\".*?\">\\s*&lt;(.*?)&gt;</span>([\\w\\W\\d\\D\\s\\n\\r\\t]*?)<br/>");
    private String data;
    private Matcher matcher;
    private File file;

    public LogFileParser(File file) throws Exception {
        this.file = file;
        StringBuilder sb = new StringBuilder();
        BufferedReader br = null;
        try {
            if (file.exists()) {
                br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
                while (br.ready()) {
                    sb.append(br.readLine());
                }
                br.close();
            }
        } catch (Exception e) {

        } finally {
            if (br != null) br.close();
        }
        data = sb.toString();
        matcher = pattern.matcher(data);
    }

    public LogEntry getNextEntry() {
        LogEntry entry = null;
        if (matcher.find()) {
            entry = new LogEntry();
            String author = null;
            try {
                author = URLDecoder.decode(matcher.group(5), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            File p1 = file.getParentFile();
            File p2 = p1.getParentFile();
            StringBuilder p = new StringBuilder("/").append(p2.getName()).append("/").append(p1.getName()).append("/").append(file.getName());

            entry.setAuthor(author);
            entry.setDate(matcher.group(2) + ":" + matcher.group(3) + ":" + matcher.group(4));
            entry.setMessage(matcher.group(6));
            entry.setUrl(p + matcher.group(1));
        }
        return entry;
    }


}

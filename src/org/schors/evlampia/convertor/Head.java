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

package org.schors.evlampia.convertor;

import org.jdom2.Attribute;
import org.jdom2.Element;

import java.util.ArrayList;
import java.util.List;

public class Head implements Convertable {
    private List<Attribute> meta = new ArrayList<>();
    private List<String> links = new ArrayList<>();
    private List<String> scripts = new ArrayList<>();
    private List<String> styles = new ArrayList<>();
    private String title;

    public Head() {
    }

    public Head(String title) {
        this.title = title;
    }

    public List<Attribute> getMeta() {
        return meta;
    }

    public void setMeta(List<Attribute> meta) {
        this.meta = meta;
    }

    public List<String> getLinks() {
        return links;
    }

    public void setLinks(List<String> links) {
        this.links = links;
    }

    public List<String> getScripts() {
        return scripts;
    }

    public void setScripts(List<String> scripts) {
        this.scripts = scripts;
    }

    public List<String> getStyles() {
        return styles;
    }

    public void setStyles(List<String> styles) {
        this.styles = styles;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public Element toElement() {
        Element head = new Element("head");
        Element meta = new Element("meta");
        for (Attribute a : this.meta) {
            meta.setAttribute(a);
        }
        head.addContent(meta);

        Element title = new Element("title");
        title.addContent(this.title);
        head.addContent(title);

        for (String s : links) {
            Element link = new Element("link");
            link.setAttribute("href", s);
            link.setAttribute("rel", "stylesheet");
            head.addContent(link);
        }

        for (String s : scripts) {
            Element script = new Element("script");
            script.setAttribute("src", s);
            script.addContent(" ");
            head.addContent(script);
        }

        for (String s : styles) {
            Element style = new Element("style");
            style.setAttribute("type", "text/css");
            style.addContent(s);
            head.addContent(style);
        }

        return head;
    }
}

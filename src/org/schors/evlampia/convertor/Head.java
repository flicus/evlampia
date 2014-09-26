package org.schors.evlampia.convertor;

import org.jdom2.Attribute;
import org.jdom2.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (c) 2013 Amdocs jNetX.
 * http://www.amdocs.com
 * All rights reserved.
 * <p/>
 * This software is the confidential and proprietary information of
 * Amdocs jNetX. You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license
 * agreement you entered into with Amdocs jNetX.
 * <p/>
 * User: Sergey Skoptsov (sskoptsov@amdocs.com)
 * Date: 26.09.2014
 * Time: 17:03
 * <p/>
 * $Id:
 */

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

package org.schors.evlampia.convertor;

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

public class Body implements Convertable {

    private List<Record> records = new ArrayList<>();
    private String left;
    private String right;

    public Body() {
    }

    public Body(List<Record> records) {
        this.records = records;
    }

    public Body(List<Record> records, String left, String right) {
        this.records = records;
        this.left = left;
        this.right = right;
    }

    @Override
    public String toString() {
        return "Body{" +
                "records=" + records +
                ", left='" + left + '\'' +
                ", right='" + right + '\'' +
                '}';
    }

    public String getLeft() {
        return left;
    }

    public void setLeft(String left) {
        this.left = left;
    }

    public String getRight() {
        return right;
    }

    public void setRight(String right) {
        this.right = right;
    }

    public List<Record> getRecords() {
        return records;
    }

    public void setRecords(List<Record> records) {
        this.records = records;
    }

    @Override
    public Element toElement() {
        Element body = new Element("body");
        Element div = new Element("div");
        div.setAttribute("style", "position: absolute;left: -10000px;");
        div.setAttribute("id", "listRender");
        body.addContent(div);

        div = new Element("div");
        div.setAttribute("class", "mark");
        div.addContent("Записки Евлампии");
        body.addContent(div);

        div = new Element("div");
        div.setAttribute("class", "header");
        body.addContent(div);

        Element subdiv = new Element("div");
        subdiv.setAttribute("class", "left");
        subdiv.addContent(left);
        div.addContent(subdiv);

        subdiv = new Element("div");
        subdiv.setAttribute("class", "right");
        subdiv.addContent(right);
        div.addContent(subdiv);

        div = new Element("div");
        div.setAttribute("class", "clear");
        body.addContent(div);

        div = new Element("div");
        div.setAttribute("class", "log");
        for (Record r : records) {
            div.addContent(r.toElement());
        }

        body.addContent(div);

        return body;
    }

}

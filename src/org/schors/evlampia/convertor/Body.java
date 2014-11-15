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

package org.schors.evlampia.convertor;

import org.jdom2.Element;

import java.util.ArrayList;
import java.util.List;

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
        div.addContent(" ");
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

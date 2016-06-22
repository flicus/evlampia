package org.schors.evlampia.convertor;

import org.jdom2.Element;

public class Html implements Convertable {
    private Head head;
    private Body body;

    public Html() {
    }

    public Html(Head head, Body body) {
        this.head = head;
        this.body = body;
    }

    public Head getHead() {
        return head;
    }

    public void setHead(Head head) {
        this.head = head;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "Html{" +
                "head=" + head +
                ", body=" + body +
                '}';
    }

    @Override
    public Element toElement() {
        Element html = new Element("html");
        html.addContent(head.toElement());
        html.addContent(body.toElement());
        return html;
    }
}

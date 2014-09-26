package org.schors.evlampia.convertor;

import org.jdom2.Element;

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

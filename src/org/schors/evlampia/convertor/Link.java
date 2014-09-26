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
 * Time: 18:48
 * <p/>
 * $Id:
 */

public class Link implements Convertable {

    private String link;

    public Link() {
    }

    public Link(String link) {
        this.link = link;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public String toString() {
        return "Link{" +
                "link='" + link + '\'' +
                '}';
    }

    @Override
    public Element toElement() {
        Element a = new Element("a");
        a.setAttribute("target", "_blank");
        a.setAttribute("href", link);
        a.addContent(link);
        return a;
    }
}

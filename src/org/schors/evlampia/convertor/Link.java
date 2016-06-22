package org.schors.evlampia.convertor;

import org.jdom2.Element;

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

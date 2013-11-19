package org.schors.evlampia.model;


public class TagItem {
    private String text;
    private int weight;

    public TagItem(String text, int weight) {
        this.text = text;
        this.weight = weight;
    }

    public TagItem() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "TagItem{" +
                "text='" + text + '\'' +
                ", weight=" + weight +
                '}';
    }
}

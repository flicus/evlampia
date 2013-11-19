package org.schors.evlampia.model;

public class Channel {
    private String name;
    private String[] modules;

    public String[] getModules() {
        return modules;
    }

    public void setModules(String[] modules) {
        this.modules = modules;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Channel{" + "name=" + name + "modules=" + modules + '}';
    }

    

}

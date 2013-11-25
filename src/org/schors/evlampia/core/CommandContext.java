package org.schors.evlampia.core;

import org.jivesoftware.smack.Connection;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: flicus
 * Date: 09.10.13
 * Time: 10:45
 * To change this template use File | Settings | File Templates.
 */
public class CommandContext {
    private String from;
    private String body;
    private Map<String, Object> facilities = new HashMap<>();
    private String[] parsedCommand;


    public CommandContext() {
    }

    public CommandContext(String from, String body, Map<String, Object> facilities) {
        this.from = from;
        this.body = body;
        this.facilities = facilities;
    }

    public CommandContext(String from, String body, Map<String, Object> facilities, String[] parsedCommand) {
        this.from = from;
        this.body = body;
        this.facilities = facilities;
        this.parsedCommand = parsedCommand;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Map<String, Object> getFacilities() {
        return facilities;
    }

    public void setFacilities(Map<String, Object> facilities) {
        this.facilities = facilities;
    }

    public String[] getParsedCommand() {
        return parsedCommand;
    }

    public void setParsedCommand(String[] parsedCommand) {
        this.parsedCommand = parsedCommand;
    }

    @Override
    public String toString() {
        return "CommandContext{" +
                "from='" + from + '\'' +
                ", body='" + body + '\'' +
                ", facilities=" + facilities +
                ", parsedCommand=" + Arrays.toString(parsedCommand) +
                '}';
    }
}

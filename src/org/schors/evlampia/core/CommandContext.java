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

package org.schors.evlampia.core;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

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

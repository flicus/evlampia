/*
 * The MIT License (MIT)
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

package org.schors.eva;

public class CommandContext {

    private FacilityManager facilityManager;
    private String[] parsedCommand;
    private String who;
    private Endpoint endpoint;

    public CommandContext(FacilityManager facilityManager) {
        this.facilityManager = facilityManager;
    }

    public CommandContext(FacilityManager facilityManager, String[] parsedCommand, String who) {
        this.facilityManager = facilityManager;
        this.parsedCommand = parsedCommand;
        this.who = who;
    }

    public CommandContext(FacilityManager facilityManager, String[] parsedCommand, String who, Endpoint endpoint) {
        this.facilityManager = facilityManager;
        this.parsedCommand = parsedCommand;
        this.who = who;
        this.endpoint = endpoint;
    }

    public <T extends AbstractFacility> T getFacility(Class<T> type) {
        return facilityManager.getFacility(type);
    }

    public String[] getParsedCommand() {
        return parsedCommand;
    }

    public String getWho() {
        return who;
    }

    public Endpoint getEndpoint() {
        return endpoint;
    }

}

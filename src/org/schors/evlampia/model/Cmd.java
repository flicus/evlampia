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

package org.schors.evlampia.model;

import java.util.Arrays;

public class Cmd {
    private String cmdClass;
    private String shortDescription;
    private String longDescription;
    private String[] prefixes;

    public Cmd() {
    }

    public Cmd(String cmdClass, String shortDescription, String longDescription, String[] prefixes) {
        this.cmdClass = cmdClass;
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
        this.prefixes = prefixes;
    }

    @Override
    public String toString() {
        return "Cmd{" +
                "cmdClass='" + cmdClass + '\'' +
                ", shortDescription='" + shortDescription + '\'' +
                ", longDescription='" + longDescription + '\'' +
                ", prefixes=" + Arrays.toString(prefixes) +
                '}';
    }

    public String getCmdClass() {
        return cmdClass;
    }

    public void setCmdClass(String cmdClass) {
        this.cmdClass = cmdClass;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public String[] getPrefixes() {
        return prefixes;
    }

    public void setPrefixes(String[] prefixes) {
        this.prefixes = prefixes;
    }
}

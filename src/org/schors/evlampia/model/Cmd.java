package org.schors.evlampia.model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

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
 * Date: 25.11.13
 * Time: 16:16
 * <p/>
 * $Id:
 */

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

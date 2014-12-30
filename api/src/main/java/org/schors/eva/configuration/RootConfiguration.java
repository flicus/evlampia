/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 schors
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all
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

package org.schors.eva.configuration;

import org.apache.log4j.Logger;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@XmlRootElement(name = "configuration")
public class RootConfiguration extends AbstractConfiguration {
    @XmlTransient
    private static final Logger log = Logger.getLogger(RootConfiguration.class);

    @XmlTransient
    private long startTime = System.currentTimeMillis();

    @XmlAttribute
    private String version = "1.0";
    private Map<Class, AbstractConfiguration> modules = new HashMap<>();

    public String getVersion() {
        return version;
    }

    public long getStartTime() {
        return startTime;
    }

    public List<AbstractConfiguration> getModules() {
        List<AbstractConfiguration> list = new ArrayList<>();
        list.addAll(modules.values());
        return list;
    }

    @XmlElement(name = "section")
    public void setModules(List<AbstractConfiguration> modules) {
        this.modules.clear();
        for (AbstractConfiguration module : modules) {
            this.modules.put(module.getClass(), module);
        }
    }

    public Map<Class, AbstractConfiguration> getModulesExt() {
        return modules;
    }
}

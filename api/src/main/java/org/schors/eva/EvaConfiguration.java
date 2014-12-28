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

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@XmlRootElement(name = "configuration")
public class EvaConfiguration extends AbstractConfiguration {

    @XmlAttribute
    private String version = "1.0";

    private Map<Class, AbstractConfiguration> modules = new HashMap<>();

    public <T extends AbstractConfiguration> void putSection(T section) {
        modules.put(section.getClass(), section);
    }

    public <T extends AbstractConfiguration> T getSection(Class<T> clazz) {
        return clazz.cast(modules.get(clazz));
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

    public String getVersion() {
        return version;
    }
}

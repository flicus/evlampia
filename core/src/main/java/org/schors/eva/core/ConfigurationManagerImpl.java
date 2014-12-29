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

package org.schors.eva.core;

import org.apache.log4j.Logger;
import org.schors.eva.AbstractConfiguration;
import org.schors.eva.RootConfiguration;
import org.schors.eva.configuration.ConfigurationManager;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class ConfigurationManagerImpl implements ConfigurationManager {

    private static final Logger log = Logger.getLogger(ConfigurationManagerImpl.class);

    private RootConfiguration configuration;

    public ConfigurationManagerImpl() {
        this.configuration = new RootConfiguration();
    }

    @Override
    public void registerConfiguration(Class<? extends AbstractConfiguration> configuration) {

    }

    @Override
    public void save() {

        File file = new File("e:\\tmp\\eva.xml");
        Class[] classes = new Class[configuration.getModulesExt().size() + 1];
        classes[0] = RootConfiguration.class;
        int i = 1;
        for (Class type : configuration.getModulesExt().keySet()) {
            classes[i++] = type;
        }
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(classes);

            Marshaller marshaller = jaxbContext.createMarshaller();

            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(this, file);
            marshaller.marshal(this, System.out);
        } catch (JAXBException e) {
            log.error(e, e);
        }

    }

    @Override
    public void load() {
        File file = new File("e:\\tmp\\eva.xml");
        Class[] classes = new Class[configuration.getModulesExt().size() + 1];
        classes[0] = RootConfiguration.class;
        int i = 1;
        for (Class type : configuration.getModulesExt().keySet()) {
            classes[i++] = type;
        }
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(classes);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            this.configuration = (RootConfiguration) unmarshaller.unmarshal(file);
        } catch (JAXBException e) {
            log.error(e, e);
        }

    }

    @Override
    public boolean needUpdate() {
        boolean result = false;
        for (Class type : configuration.getModulesExt().keySet()) {
            if (configuration.getModulesExt().get(type) == null) {
                result = true;
                break;
            }
        }
        return result;
    }
}

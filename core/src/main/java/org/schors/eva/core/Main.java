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

package org.schors.eva.core;

import org.apache.log4j.Logger;
import org.schors.eva.Application;
import org.schors.eva.DependencyResolver;
import org.schors.eva.command.CommandManager;
import org.schors.eva.configuration.AbstractConfiguration;
import org.schors.eva.configuration.ConfigurationManager;
import org.schors.eva.facility.AbstractFacility;
import org.schors.eva.facility.FacilityManager;
import org.schors.eva.protocol.ProtocolManager;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main implements PluginLoader, Application {
    private static final Logger log = Logger.getLogger(Main.class);
    private FacilityManager facilityManager;
    private CommandManager commandManager;
    private PluginManager pluginManager;
    private ProtocolManager protocolManager;
    private DependencyResolver dependencyResolver;
    private ExecutorService pool;
    private ConfigurationManager configurationManager;

    public Main() {
        configurationManager = new ConfigurationManagerImpl();
        facilityManager = new FacilityManagerImpl(this);
        commandManager = new CommandManagerImpl(this);
        pluginManager = new PluginManager(this);
        protocolManager = new ProtocolManagerImpl();
        dependencyResolver = new DependencyResolverImpl();
        pool = Executors.newCachedThreadPool();
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.run();
    }

    @Override
    public void onFacilityDiscovered(final Class<? extends AbstractFacility> clazz) {
        pool.execute(new Runnable() {
            @Override
            public void run() {
                String name = facilityManager.registerFacility(clazz);
            }
        });
    }

    @Override
    public void onCommandDiscovered(final Class<?> clazz) {
        pool.execute(new Runnable() {
            @Override
            public void run() {
                commandManager.addNewCommand(clazz);
            }
        });
    }

    @Override
    public void onProtocolDiscovered(final Class<?> clazz) {
        pool.execute(new Runnable() {
            @Override
            public void run() {
                protocolManager.registerProtocol(clazz);
            }
        });
    }

    @Override
    public void onConfigurationDiscovered(Class<? extends AbstractConfiguration> clazz) {
        Object o = null;
        try {
            o = clazz.newInstance();
        } catch (Exception e) {
            log.error(e, e);
        }
        configurationManager.putSection(clazz.cast(o));
    }

    @Override
    public void onStartLoading() {

    }

    @Override
    public void onStopLoading() {
//        configurationManager.save();
        configurationManager.load();
        if (configurationManager.needUpdate()) {
            configurationManager.save();
        }
        facilityManager.tryStart();
    }

    public void run() {
        synchronized (Thread.currentThread()) {
            try {
                Thread.currentThread().wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public FacilityManager getFacilityManager() {
        return facilityManager;
    }

    @Override
    public CommandManager getCommandManager() {
        return commandManager;
    }

    @Override
    public ProtocolManager getProtocolManager() {
        return protocolManager;
    }

    @Override
    public DependencyResolver getDependencyResolver() {
        return dependencyResolver;
    }

    @Override
    public ExecutorService getThreadPool() {
        return pool;
    }

    @Override
    public ConfigurationManager getConfigurationManager() {
        return configurationManager;
    }
}

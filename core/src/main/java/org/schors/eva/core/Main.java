package org.schors.eva.core;

import org.schors.eva.CommandManager;
import org.schors.eva.FacilityManager;

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
 * Date: 25.12.2014
 * Time: 18:24
 * <p/>
 * $Id:
 */

public class Main implements PluginLoader {
    private FacilityManager facilityManager;
    private CommandManager commandManager;
    private PluginManager pluginManager;


    public Main() {
        facilityManager = new FacilityManagerImpl();
        commandManager = new CommandManagerImpl(facilityManager);
        pluginManager = new PluginManager(this);
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.run();
    }

    @Override
    public void onFacilityDiscovered(Class<?> clazz) {
        facilityManager.addNewFacility(clazz);
    }

    @Override
    public void onCommandDiscovered(Class<?> clazz) {
        commandManager.addNewCommand(clazz);
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
}

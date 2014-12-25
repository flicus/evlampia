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

package org.schors.eva.core;

import org.schors.eva.CommandContext;
import org.schors.eva.CommandManager;
import org.schors.eva.FacilityManager;
import org.schors.eva.Waiter;
import org.schors.eva.annotations.Command;
import org.schors.eva.annotations.CommandExecute;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class CommandManagerImpl implements CommandManager {

    private Map<String, CommandAdapter> cache = new ConcurrentHashMap<>();
    private Set<CommandAdapter> commands = new LinkedHashSet<>();
    private Executor executor = Executors.newCachedThreadPool();
    private FacilityManager facilityManager;

    public CommandManagerImpl(FacilityManager facilityManager) {
        this.facilityManager = facilityManager;
    }

    public void addNewCommand(final Class<?> command) {

        executor.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("CommandManagerImpl::addNewCommand:: " + command);
                boolean ok = true;
                CommandAdapter commandAdapter = null;
                String[] prefixes = null;
                String[] depends = null;
                if (command.isAnnotationPresent(Command.class)) {
                    prefixes = command.getAnnotation(Command.class).prefixes();
                    depends = command.getAnnotation(Command.class).dependsOn();
                }
                if (prefixes == null || prefixes.length <= 0) {
                    ok = false;
                }

                if (ok) {
                    if (depends != null && depends.length > 0) {
                        System.out.println("CommandManagerImpl:: dependecies wait");
                        for (String facility : depends) {
                            System.out.println("CommandManagerImpl::waiting:: " + facility);
                            Waiter waiter = facilityManager.waitForFacility(facility);
                            waiter.get();
                            System.out.println("CommandManagerImpl::gotDep:: " + facility);
                        }
                    }
                }

                if (ok) {
                    try {
                        Object o = command.newInstance();
                        commandAdapter = new CommandAdapter(o, prefixes);
                    } catch (Exception e) {
                        ok = false;
                    }
                }

                if (ok) {
                    System.out.println("CommandManagerImpl::addNewCommand::done:: " + commandAdapter);
                    commands.add(commandAdapter);
                }
            }
        });
    }

    public void processCommand(final CommandContext context) {
        String[] cmds = context.getParsedCommand();
        CommandAdapter adapter = null;
        if (commands.size() > 0) {
            adapter = cache.get(cmds[0]);
            if (adapter == null) {
                for (CommandAdapter cmda : commands) {
                    if (cmda.isApply(cmds[0])) {
                        adapter = cmda;
                        cache.put(cmds[0], adapter);
                        break;
                    }
                }
            }
            if (adapter != null) {
                final CommandAdapter a = adapter;
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        a.execute(context);
                    }
                });
            }
        }
    }

    public class CommandAdapter<T> {
        private T command;
        private Method execute;
        private Set<String> prefixes = new HashSet<String>();

        public CommandAdapter(T command, String[] prefixes) {
            this.command = command;
            for (String prefix : prefixes) {
                this.prefixes.add(prefix);
            }
            for (Method method : command.getClass().getMethods()) {
                if (method.isAnnotationPresent(CommandExecute.class)) execute = method;
                if (execute != null) break;
            }
        }

        public void execute(CommandContext context) {
            try {
                execute.invoke(command, context);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public boolean isApply(String prefix) {
            return prefixes.contains(prefix);
        }

        @Override
        public String toString() {
            return "CommandAdapter{" +
                    "command=" + command +
                    ", execute=" + execute +
                    ", prefixes=" + prefixes +
                    '}';
        }
    }
}

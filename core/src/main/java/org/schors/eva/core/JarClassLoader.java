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

import org.schors.eva.annotations.Command;
import org.schors.eva.annotations.CommandExecute;
import org.schors.eva.annotations.Facility;
import org.schors.eva.annotations.ProtocolAdapter;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class JarClassLoader extends ClassLoader {

    private Map<String, JarClassDef> mappings = new HashMap<String, JarClassDef>();
    private Map<String, Class<?>> cache = new HashMap<String, Class<?>>();
    private PluginLoader pluginLoader;

    public JarClassLoader(PluginLoader pluginLoader) {
        super();
        this.pluginLoader = pluginLoader;
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        Class<?> result = cache.get(name);
        if (result == null) {
            result = super.findSystemClass(name);
        }
        if (result == null && mappings.containsKey(name)) {
            try {
                result = readClass(name, mappings.get(name));
                if (result != null) {
                    cache.put(name, result);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public void loadJar(String jarFileName) throws IOException {
        System.out.println("JarClassLoader:: loading jar:: " + jarFileName);
        JarFile jarFile = new JarFile(jarFileName);
        Enumeration<JarEntry> entries = jarFile.entries();
        while (entries.hasMoreElements()) {
            JarEntry entry = entries.nextElement();
            if (entry.isDirectory() || !entry.getName().endsWith(".class")) continue;
            String className = stripClassName(normalize(entry.getName()));
            System.out.println("JarClassLoader:: Class found:: " + className);
            JarClassDef jarClassDef = new JarClassDef(entry, jarFile);
            mappings.put(className, jarClassDef);
            Class<?> clazz = readClass(className, jarClassDef);
            if (clazz != null) {
                if (clazz.isAnnotationPresent(Facility.class)) {
                    cache.put(className, clazz);
                    pluginLoader.onFacilityDiscovered(clazz);
                } else if (clazz.isAnnotationPresent(Command.class)) {
                    boolean hasExecute = false;
                    for (Method method : clazz.getMethods()) {
                        if (method.isAnnotationPresent(CommandExecute.class)) {
                            hasExecute = true;
                            break;
                        }
                    }
                    if (hasExecute) {
                        cache.put(className, clazz);
                        pluginLoader.onCommandDiscovered(clazz);
                    }
                } else if (clazz.isAnnotationPresent(ProtocolAdapter.class)) {
                    cache.put(className, clazz);
                    pluginLoader.onProtocolDiscovered(clazz);
                }
            }
        }
    }

    private Class<?> readClass(String className, JarClassDef jarClassDef) throws IOException {
        Class<?> clazz = null;
        byte[] classData = loadClassData(jarClassDef.getJarFile(), jarClassDef.getJarEntry());
        if (classData != null) {
            clazz = defineClass(className, classData, 0, classData.length);
        }
        return clazz;
    }

    private byte[] loadClassData(JarFile jarFile, JarEntry entry) throws IOException {
        long size = entry.getSize();
        if (size == -1 || size == 0) return null;

        byte[] data = new byte[(int) size];
        InputStream in = jarFile.getInputStream(entry);
        in.read(data);
        return data;
    }

    private String stripClassName(String className) {
        return className.substring(0, className.length() - 6);
    }

    private String normalize(String className) {
        return className.replace("/", ".");
    }
}

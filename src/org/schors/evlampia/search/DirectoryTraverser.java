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

package org.schors.evlampia.search;

import java.io.File;
import java.util.LinkedList;

public class DirectoryTraverser {

    private File root;
    private LinkedList<File> fileList = new LinkedList<File>();
    private LinkedList<File> dirList = new LinkedList<File>();

    public DirectoryTraverser(File root) {
        if (root.exists() && root.isDirectory()) {
            this.root = root;
        }
        parseDir(root);
    }

    public File getNextFile() {
        File file = null;
        if (fileList.size() > 0) {
            file = fileList.poll();
        } else {
            if (dirList.size() > 0) {
                while (fileList.size() == 0 && dirList.size() != 0) {
                    parseDir(dirList.poll());
                }
                if (fileList.size() > 0)
                    file = fileList.poll();
            }
        }
        return file;
    }

    private void parseDir(File dir) {
        if (dir.isDirectory()) {
            for (File file : dir.listFiles()) {
                if (file.isDirectory()) {
                    dirList.offer(file);
                } else {
                    fileList.offer(file);
                }
            }
        }
    }
}

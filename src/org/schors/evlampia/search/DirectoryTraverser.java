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

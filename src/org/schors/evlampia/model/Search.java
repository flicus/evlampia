package org.schors.evlampia.model;

public class Search {
    private String indexDirectory;
    private String urlPrefix;
    private String webRoot;
    private int port;

    public Search() {
    }

    public Search(String indexDirectory, String urlPrefix, String webRoot, int port) {
        this.indexDirectory = indexDirectory;
        this.urlPrefix = urlPrefix;
        this.webRoot = webRoot;
        this.port = port;
    }

    public String getWebRoot() {
        return webRoot;
    }

    public void setWebRoot(String webRoot) {
        this.webRoot = webRoot;
    }

    public String getIndexDirectory() {
        return indexDirectory;
    }

    public void setIndexDirectory(String indexDirectory) {
        this.indexDirectory = indexDirectory;
    }

    public String getUrlPrefix() {
        return urlPrefix;
    }

    public void setUrlPrefix(String urlPrefix) {
        this.urlPrefix = urlPrefix;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public String toString() {
        return "Search{" +
                "indexDirectory='" + indexDirectory + '\'' +
                ", urlPrefix='" + urlPrefix + '\'' +
                ", webRoot='" + webRoot + '\'' +
                ", port=" + port +
                '}';
    }
}

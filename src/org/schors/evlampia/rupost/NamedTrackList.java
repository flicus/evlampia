package org.schors.evlampia.rupost;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class NamedTrackList implements Serializable {
    private static final long serialVersionUID = 1451012011077990017L;
    private String name;
    private Map<String, Track> tracks;


    public NamedTrackList() {
        tracks = new ConcurrentHashMap<String, Track>();
    }

    public NamedTrackList(String name) {
        this.tracks = new ConcurrentHashMap<String, Track>();
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addTrack(Track track) {
        tracks.put(track.getId(), track);
    }

    public void removeTrack(String trackId) {
        tracks.remove(trackId);
    }

    public int size() {
        return tracks.size();
    }

    public Map<String, Track> getTracks() {
        return tracks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NamedTrackList that = (NamedTrackList) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (tracks != null ? !tracks.equals(that.tracks) : that.tracks != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (tracks != null ? tracks.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("NamedTrackList{").append("name='").append(name).append(", tracks={");
        for (Map.Entry<String, Track> entry : tracks.entrySet()) {
            sb.append(entry.getKey()).append(", ").append(entry.getValue()).append(" ,");
        }
        sb.append("}");
        return sb.toString();
    }
}

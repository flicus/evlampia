/*
 * The MIT License
 *
 * Copyright (c) 2014.  schors (https://github.com/flicus)
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software,
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED
 * TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
 * THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

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

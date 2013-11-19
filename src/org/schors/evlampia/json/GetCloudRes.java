package org.schors.evlampia.json;

import org.schors.evlampia.model.TagItem;

import java.util.Arrays;
import java.util.List;

public class GetCloudRes extends Response {

    private List<TagItem> tags;

    public GetCloudRes(List<TagItem> tags) {
        this.tags = tags;
    }

    public GetCloudRes() {
    }

    public List<TagItem> getTags() {
        return tags;
    }

    public void setTags(List<TagItem> tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "GetCloudRes{" +
                "tags=" + tags +
                '}';
    }
}

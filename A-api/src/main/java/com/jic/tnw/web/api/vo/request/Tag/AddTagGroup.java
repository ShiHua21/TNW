package com.jic.tnw.web.api.vo.request.Tag;

import java.util.List;

/**
 * @author tp
 * @date 2018/4/3
 */
public class
AddTagGroup {

    private String name;
    private List<Integer> scope;
    private String tags;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Integer> getScope() {
        return scope;
    }

    public void setScope(List<Integer> scope) {
        this.scope = scope;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }
}

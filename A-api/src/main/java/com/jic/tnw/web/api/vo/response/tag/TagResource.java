package com.jic.tnw.web.api.vo.response.tag;

import com.jic.tnw.db.mysql.tables.pojos.TagGroup;
import org.springframework.hateoas.ResourceSupport;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author tp
 * @date 2018/4/2
 */
public class TagResource extends ResourceSupport {
    private Integer tag_id;
    private String name;
    private LocalDateTime created_time;
    private Integer org_id;
    private String  org_code;
    private List<TagGroup> tagGroupList;

    public List<TagGroup> getTagGroupList() {
        return tagGroupList;
    }

    public void setTagGroupList(List<TagGroup> tagGroupList) {
        this.tagGroupList = tagGroupList;
    }

    public Integer getTag_id() {
        return tag_id;
    }

    public void setTag_id(Integer tag_id) {
        this.tag_id = tag_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getCreated_time() {
        return created_time;
    }

    public void setCreated_time(LocalDateTime created_time) {
        this.created_time = created_time;
    }

    public Integer getOrg_id() {
        return org_id;
    }

    public void setOrg_id(Integer org_id) {
        this.org_id = org_id;
    }

    public String  getOrg_code() {
        return org_code;
    }

    public void setOrg_code(String  org_code) {
        this.org_code = org_code;
    }
}

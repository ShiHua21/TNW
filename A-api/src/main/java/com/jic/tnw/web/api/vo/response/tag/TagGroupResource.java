package com.jic.tnw.web.api.vo.response.tag;

import com.jic.tnw.db.mysql.tables.pojos.Tag;
import org.springframework.hateoas.ResourceSupport;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author tp
 * @date 2018/4/3
 */
public class TagGroupResource extends ResourceSupport {

    private Integer tag_group_Id;
    private String name;
    private String scope;
    private Integer tag_num;
    private LocalDateTime updated_time;
    private LocalDateTime created_time;
    private List<Tag> tagList;

    public List<Tag> getTagList() {
        return tagList;
    }

    public void setTagList(List<Tag> tagList) {
        this.tagList = tagList;
    }

    public Integer getTag_group_Id() {
        return tag_group_Id;
    }

    public void setTag_group_Id(Integer tag_group_Id) {
        this.tag_group_Id = tag_group_Id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public Integer getTag_num() {
        return tag_num;
    }

    public void setTag_num(Integer tag_num) {
        this.tag_num = tag_num;
    }

    public LocalDateTime getUpdated_time() {
        return updated_time;
    }

    public void setUpdated_time(LocalDateTime updated_time) {
        this.updated_time = updated_time;
    }

    public LocalDateTime getCreated_time() {
        return created_time;
    }

    public void setCreated_time(LocalDateTime created_time) {
        this.created_time = created_time;
    }
}

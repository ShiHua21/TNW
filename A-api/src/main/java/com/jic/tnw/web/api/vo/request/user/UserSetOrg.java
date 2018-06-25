package com.jic.tnw.web.api.vo.request.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.validation.annotation.Validated;

/**
 * Created by lee5hx on 2018/3/5.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Validated
public class UserSetOrg {

    private String[] orgIds;

    private String[] ids;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("User.UserSetOrg (");
        sb.append(ids);
        sb.append(",");
        sb.append(orgIds);
        sb.append(")");
        return sb.toString();
    }

    public String[] getIds() {
        return ids;
    }

    public void setIds(String[] ids) {
        this.ids = ids;
    }

    public String[] getOrgIds() {
        return orgIds;
    }

    public void setOrgIds(String[] orgIds) {
        this.orgIds = orgIds;
    }


}

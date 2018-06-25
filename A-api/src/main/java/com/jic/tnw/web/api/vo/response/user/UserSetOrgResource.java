package com.jic.tnw.web.api.vo.response.user;

import org.springframework.hateoas.ResourceSupport;


public class UserSetOrgResource extends ResourceSupport {

    private String orgIds;
    private String orgCodes;

    public String getOrgIds() {
        return orgIds;
    }

    public void setOrgIds(String orgIds) {
        this.orgIds = orgIds;
    }

    public String getOrgCodes() {
        return orgCodes;
    }

    public void setOrgCodes(String orgCodes) {
        this.orgCodes = orgCodes;
    }
}

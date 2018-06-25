package com.jic.tnw.user.service;

import com.jic.tnw.db.mysql.tables.pojos.Org;
import com.jic.tnw.user.service.dto.AddOrgDTO;
import com.jic.tnw.user.service.dto.EditOrgDTO;
import com.jic.tnw.user.service.dto.OrgTree;

import java.util.Map;

/**
 * Created by lee5hx on 2018/3/6.
 */
public interface OrgService {

    Org addOrg(AddOrgDTO addOrg, Integer uid);

    Org updateOrg(Integer orgId, EditOrgDTO editOrg, Integer uid);

    Org deleteOrg(Integer orgId);

    Org getOrg(Integer orgId);

    OrgTree getOrgTree();

    Map<Integer,Org> getOrgMap();

    String getOrgNameById(String ids);
}

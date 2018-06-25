package com.jic.tnw.user.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jic.tnw.db.mysql.tables.pojos.Org;
import com.jic.tnw.db.repository.OrgRepository;

import com.jic.tnw.user.service.dto.AddOrgDTO;
import com.jic.tnw.user.service.dto.EditOrgDTO;
import com.jic.tnw.user.service.dto.OrgTree;
import com.jic.tnw.user.service.dto.user.JelUser;
import com.jic.tnw.common.exception.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;


/**
 * Created by lee5hx on 2017/10/19.
 */
@Service
public class OrgServiceImpl implements OrgService {

    private static final Log LOGGER = LogFactory.getLog(OrgServiceImpl.class);

    private final ObjectMapper mapper;

    private final OrgRepository orgRepository;

    private final UserService userService;


    @Autowired
    OrgServiceImpl(ObjectMapper mapper,
                   OrgRepository orgRepository,
                   UserService userService) {
        this.mapper = mapper;
        this.orgRepository = orgRepository;
        this.userService = userService;

    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    @CachePut(value = "Org",key = "#result.id.toString()")
    public Org addOrg(AddOrgDTO addOrg, Integer uid) {

        LOGGER.info(String.format("User.Org Add %s", addOrg.toString()));
        Integer parentId = Integer.parseInt(addOrg.getParentId());
        Org parentOrg = orgRepository.findById(parentId);
        if (parentOrg == null) {
            throw new OrgParentNotExistsException();
        }

        Org org = orgRepository.findByCode(addOrg.getCode());
        if (org != null) {
            throw new OrgCodeExistsException();
        }
        Integer newOrgSeq = parentOrg.getChildrenNum() + 1;
        Integer newOrgDepth = parentOrg.getDepth() + 1;
        orgRepository.updateChildrenNumById(parentId, parentOrg.getChildrenNum() + 1);
        org = new Org();
        org.setParentId(parentOrg.getId());
        org.setName(addOrg.getName());
        org.setCode(addOrg.getCode());
        org.setDescription(addOrg.getDescription());
        org.setChildrenNum(0);
        org.setSeq(newOrgSeq);
        org.setDepth(newOrgDepth);
        org.setCreatedUserId(uid);
        org.setCreatedTime(LocalDateTime.now());
        org = orgRepository.add(org);
        String orgCode = String.format("%s%s.", parentOrg.getOrgCode(), org.getId());
        orgRepository.updateOrgCodeById(org.getId(), orgCode);

        return org;
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    @CachePut(value = "Org",key = "#orgId.toString()")
    public Org updateOrg(Integer orgId, EditOrgDTO editOrg, Integer uid) {
        LOGGER.info(String.format("User.Org Id=%d Edit %s",
                orgId,
                editOrg.toString()
        ));
        LOGGER.debug(String.format("Org-Id=%d", orgId));
        Org org = orgRepository.findById(orgId);
        if (org == null) {
            throw new OrgNotExistsException();
        }
        Org codeOrg = orgRepository.findByCode(editOrg.getCode());
        if ((!editOrg.getCode().equals(org.getCode())) && codeOrg != null) {
            //编码已被占用,请换一个
            throw new OrgCodeAlreadyOccupiedException();
        }
        Org updateOrg = new Org();
        updateOrg.setId(orgId);
        updateOrg.setName(editOrg.getName());
        updateOrg.setCode(editOrg.getCode());
        updateOrg.setDescription(editOrg.getDescription());
        updateOrg.setLastUpdUserId(uid);
        return orgRepository.update(updateOrg);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    @CacheEvict(value = "Org",key = "#orgId.toString()")
    public Org deleteOrg(Integer orgId) {
        LOGGER.info(String.format("User.Org Id=%d Delete ",
                orgId
        ));
        LOGGER.debug(String.format("Org-Id=%d", orgId));
        Org org = orgRepository.findById(orgId);
        if (org == null) {
            throw new OrgNotExistsException();
        }
        if (org.getId().intValue() == 1) {
            throw new OrgTopNodeNotDelException();
        }
        //更新子节点
        Org parentOrg = orgRepository.findById(org.getParentId());
        orgRepository.updateChildrenNumById(parentOrg.getId(), parentOrg.getChildrenNum() - 1);
        //todo 判断课程与用户是否存在,只有该机构下不存在用户和课程时,才可以删除! by lee5hx

        return orgRepository.delete(orgId);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "Org",key = "#orgId.toString()")
    public Org getOrg(Integer orgId) {
        return orgRepository.findById(orgId);
    }

    @Override
    @Transactional(readOnly = true)
    public OrgTree getOrgTree() {
        List<Org> list = orgRepository.findAll();


        LOGGER.info(String.format("User.Org OrgTreeResource.List.Size=%d ",
                list.size()
        ));
        Map<Integer, List<OrgTree>> map = list.stream()
                .map(org -> {
                    OrgTree orgTree = null;
                    try {
                        orgTree = new OrgTree();
                        orgTree.setOrgId(org.getId());
                        orgTree.setParentId(org.getParentId());
                        orgTree.setName(org.getName());
                        orgTree.setCode(org.getCode());
                        orgTree.setDepth(org.getDepth());
                        orgTree.setSeq(org.getSeq());
                        orgTree.setSyncId(org.getSyncId());
                        orgTree.setOrgCode(org.getOrgCode());
                        orgTree.setChildrenNum(org.getChildrenNum());
                        orgTree.setCreatedTime(org.getCreatedTime());
                        JelUser jelUser = userService.findById(org.getCreatedUserId());
                        orgTree.setCreatedUserName(jelUser.getUser().getUsername());
                        orgTree.setCreatedUserId(org.getCreatedUserId());
                        orgTree.setLastUpdTime(org.getLastUpdTime());
                        orgTree.setLastUpdUserId(org.getLastUpdUserId());
                        orgTree.setDescription(org.getDescription());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return orgTree;
                })
                .collect(groupingBy(
                        t -> t.getParentId().intValue()
                ));
        OrgTree orgTree;
        //获取父节点为0的机构,即顶级节点
        orgTree = map.get(0).get(0);
        recursiveOrgTree(orgTree, map);
        return orgTree;
    }

    private void recursiveOrgTree(OrgTree orgTree, Map<Integer, List<OrgTree>> map) {
        List<OrgTree> list = map.get(orgTree.getOrgId().intValue());
        if (list == null) {
            return;
        } else {
            list = list.parallelStream()
                    .sorted(Comparator.comparing(OrgTree::getSeq))
                    .collect(Collectors.toList());
        }
        for (OrgTree orgTree1 : list) {
            recursiveOrgTree(orgTree1, map);
        }
        orgTree.setChild(list);
    }

    @Override
    @Transactional(readOnly = true)
    public Map<Integer, Org> getOrgMap() {
        // Map<Integer,Org>  map = new HashMap<>();
        List<Org> list = orgRepository.findAll();
        Map<Integer, Org> orgMap = list.parallelStream()
                .collect(HashMap::new, (map, p) -> map.put(p.getId(), p), Map::putAll);
        return orgMap;
    }
    @Override
    public String getOrgNameById(String ids){
        Map<Integer,Org> map = getOrgMap();
        String [] arr = ids.split("\\|");
        StringBuilder appendStr = new StringBuilder();

        for (String id : arr){
          Org org = map.get(Integer.valueOf(id));
          if (org == null){
              return  null;
          }
          appendStr.append(org.getName()+"|");
        }
        return appendStr.toString();
    }


}

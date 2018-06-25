package com.jic.tnw.user.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.jic.tnw.db.mysql.enums.WebUrlResourceType;
import com.jic.tnw.db.mysql.tables.daos.RoleDao;
import com.jic.tnw.db.mysql.tables.pojos.Role;
import com.jic.tnw.db.mysql.tables.pojos.WebUrlResource;
import com.jic.tnw.db.repository.impl.JooqRoleRepository;
import com.jic.tnw.db.repository.impl.JooqWebUrlResourceRepository;

import com.jic.tnw.user.service.dto.WurTree;
import com.jic.tnw.user.service.dto.role.AddRoleDTO;
import com.jic.tnw.user.service.dto.role.EditRoleDTO;
import com.jic.tnw.common.exception.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

/**
 * Created by lee5hx on 2017/10/19.
 */
@Service
public class RoleServiceImpl implements RoleService {

    private static final Log LOGGER = LogFactory.getLog(RoleServiceImpl.class);

    private final ObjectMapper mapper;
    private final RoleDao roleDao;
    private final JooqRoleRepository jooqRoleRepository;
    private final JooqWebUrlResourceRepository jooqWebUrlResourceRepository;

    @Autowired
    RoleServiceImpl(ObjectMapper mapper,
                    RoleDao roleDao,
                    JooqRoleRepository jooqRoleRepository,
                    JooqWebUrlResourceRepository jooqWebUrlResourceRepository) {
        this.mapper = mapper;
        this.roleDao = roleDao;
        this.jooqRoleRepository = jooqRoleRepository;
        this.jooqWebUrlResourceRepository = jooqWebUrlResourceRepository;
    }

    @Override
    public List<Role> findAll() {
        List<Role> roleList = jooqRoleRepository.findAll();
        return jooqRoleRepository.findAll();
    }


    @Override
    @Transactional(readOnly = true)
    public Page<Role> findRolesWithPageable(Pageable pageable) {
//        return jooqRoleRepository.find(pageable);
        return null;
    }


    @Override
    @Transactional(readOnly = true)
    public Map<String, String[]> getUrlRoleMap() {
        Map<String, String[]> urlRoleMap = new HashMap<>();
        List<WebUrlResource> urlList = jooqWebUrlResourceRepository.findListByType(WebUrlResourceType.URL);
        LOGGER.info(String.format("url.list-size:%s", urlList.size()));
        Map<String, WebUrlResource> urlMap = urlList.parallelStream()
                .collect(HashMap::new, (map, p) -> map.put(p.getCode(), p), Map::putAll);
        LOGGER.info(String.format("url.map-size:%s", urlMap.size()));
        List<Role> roleList = jooqRoleRepository.findAll();
        WebUrlResource tempWebUrlResource;
        String tempRoles;
        String[] tempRoleCodes;
        for (Role r : roleList) {
            ArrayNode tt;
            try {
                        tt = (ArrayNode) mapper.readTree(r.getData());
            } catch (IOException e) {
                throw new RuntimeException("role date is null", e);
            }
            for (int i = 0; i < tt.size(); i++) {
                tempWebUrlResource = urlMap.get(tt.get(i).asText());
                if (urlRoleMap.containsKey(tempWebUrlResource.getUrl())) {
                    tempRoleCodes = urlRoleMap.get(tempWebUrlResource.getUrl());
                    tempRoleCodes = Arrays.copyOf(tempRoleCodes, tempRoleCodes.length + 1);
                    tempRoleCodes[tempRoleCodes.length - 1] = r.getCode();

                    urlRoleMap.put(tempWebUrlResource.getUrl(), tempRoleCodes);
                } else {
                    tempRoleCodes = new String[1];
                    tempRoleCodes[0] = r.getCode();
                    urlRoleMap.put(tempWebUrlResource.getUrl(), tempRoleCodes);
                }
            }
        }
        urlRoleMap.entrySet().stream().forEach(entry -> {
            LOGGER.info(String.format("%s = %s", entry.getKey(), Arrays.toString(entry.getValue())));
        });
        return urlRoleMap;
    }

    private Map<Integer, String> getUrlByIdRoleMap() {
        List<WebUrlResource> urlList = jooqWebUrlResourceRepository.findListByType(WebUrlResourceType.URL);
        Map<Integer, String> getCodeByIdMap = urlList.stream().collect(Collectors.toMap(WebUrlResource::getId, WebUrlResource::getCode));
        return getCodeByIdMap;
    }
    @Override
    public Map<String,Integer> getIdByCodeRoleMap(String json){
        List<WebUrlResource> urlList = jooqWebUrlResourceRepository.findListByType(WebUrlResourceType.URL);
        Map<String,Integer> getCodeByIdMap = urlList.stream().collect(Collectors.toMap(WebUrlResource::getCode,WebUrlResource::getId));
        return getCodeByIdMap;
    }
    @Override
    @Transactional(readOnly = true)
    public Map<Integer, Role> getOrgMap() {
        List<Role> list = findAll();
        Map<Integer, Role> orgMap = list.parallelStream()
                .collect(HashMap::new, (map, p) -> map.put(p.getId(), p), Map::putAll);
        return orgMap;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Role> findWithPageable(Pageable pageable, Map<String, Object> conditionMap) {
        return jooqRoleRepository.find(pageable, conditionMap);
    }

    @Override
    @Transactional(readOnly = true)
    public WurTree getWurTree() {
        List<WebUrlResource> list = jooqWebUrlResourceRepository.findAll();
        LOGGER.info(String.format(" WurTree.List.Size=%d ",
                list.size()
        ));
        Map<Integer, List<WurTree>> map = list.stream()
                .map(webUrlResource -> {
                    WurTree wurTree;
                    wurTree = new WurTree();
                    wurTree.setId(webUrlResource.getId());
                    wurTree.setChildrenNum(webUrlResource.getChildrenNum());
                    wurTree.setParentId(webUrlResource.getParentId());
                    wurTree.setCode(webUrlResource.getCode());
                    wurTree.setCreatedTime(webUrlResource.getCreatedTime());
                    wurTree.setCreatedUserId(webUrlResource.getCreatedUserId());
                    wurTree.setDescription(webUrlResource.getDescription());
                    wurTree.setDepth(webUrlResource.getDepth());
                    wurTree.setType(webUrlResource.getType());
                    wurTree.setSeq(webUrlResource.getSeq());
                    wurTree.setUpdatedTime(webUrlResource.getUpdatedTime());
                    wurTree.setUpdatedUserId(webUrlResource.getUpdatedUserId());
                    wurTree.setWurCode(webUrlResource.getWurCode());
                    wurTree.setUrl(webUrlResource.getUrl());
                    return wurTree;
                })
                .collect(groupingBy(
                        t -> t.getParentId().intValue()
                ));
        WurTree wurTree;
        //获取父节点为0的机构,即顶级节点
        wurTree = map.get(0).get(0);
        recursiveOrgTree(wurTree, map);
        return wurTree;
    }

    private void recursiveOrgTree(WurTree wurTree, Map<Integer, List<WurTree>> map) {
        List<WurTree> list = map.get(wurTree.getId().intValue());
        if (list == null) {
            return;
        } else {
            list = list.parallelStream()
                    .sorted(Comparator.comparing(WurTree::getSeq))
                    .collect(Collectors.toList());
        }
        for (WurTree wurTree1 : list) {
            recursiveOrgTree(wurTree1, map);
        }
        wurTree.setChild(list);
    }

    @Override
    @Transactional(readOnly = true)
    public Role findById(Integer id) {
        Role role = jooqRoleRepository.findById(id);
        return role;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Role deleteById(Integer id) {
        Role role = jooqRoleRepository.delete(id);
        return role;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Role addRole(AddRoleDTO addRole, Integer uid) {
        Role codeRole = jooqRoleRepository.findByCode(addRole.getCode());
        if (codeRole != null) {
            throw new RoleCodeExistException();
        }
        Role nameRole = jooqRoleRepository.findByName(addRole.getName());

        if (nameRole != null) {
            throw new RoleNameExistExceotion();
        }
        List<String>codeList = judgeWebUrlResourceCode(addRole.getIds());
        try {
           String json = mapper.writeValueAsString(codeList);
            LOGGER.info(String.format("add role code json is %s", json));
           Role role = jooqRoleRepository.addRole(addRole.getName(),addRole.getCode(),json,uid);
           codeRole = role;
           return role;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        LOGGER.info(String.format("add role code is %s", codeList));
        return codeRole;
    }
    @Override
    public Role updateRole(EditRoleDTO editRole, Integer uid, Integer roleId){
        Role idForRole = jooqRoleRepository.findById( roleId);
        if (idForRole == null){
            throw new RoleIDNotExistException();
        }
        Role nameRole = jooqRoleRepository.findByName(editRole.getName());
        if (nameRole == null || nameRole.getName().equals(idForRole.getName())){
            List<String> codeList = judgeWebUrlResourceCode(editRole.getIds());
            try {
                String json = mapper.writeValueAsString(codeList);
                LOGGER.info(String.format("add role code json is %s", json));
                Role role = jooqRoleRepository.updateRole(editRole.getName(),uid,json,roleId);
                nameRole = role;
                return role;
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }else {
            throw new RoleNameExistExceotion();
        }

        return nameRole;
    }
    private List<String> judgeWebUrlResourceCode(String[]ids){
        Map<Integer, String> getCodeByIdMap = getUrlByIdRoleMap();
        List<String> codeList = new ArrayList<>();
        for (String id : ids) {
            WebUrlResource resource = jooqWebUrlResourceRepository.findById(Integer.valueOf(id));
            if (resource==null){
                throw new WebUrlResourceIdNotFoundException();
            }
            if (resource.getType() == WebUrlResourceType.CATALOG_NAME){
                throw  new WebUrlResourceCodeNotMatchException();
            }
            String code = getCodeByIdMap.get(Integer.valueOf(id));
            codeList.add(code);
        }
        return codeList;
    }
    @Override
    public void verifyAddRoleCode(String code){
        Role codeRole = jooqRoleRepository.findByCode(code);
        if (codeRole != null){
            throw new RoleCodeExistException();
        }
    }
    @Override
    public void verifyAddRoleName(String name){
        Role nameRole = jooqRoleRepository.findByName(name);
        if (nameRole != null){
            throw new RoleNameExistExceotion();
        }
    }
    @Override
    public void verifyEditRoleName(String name,String roleId){
        Role role = findById(Integer.valueOf(roleId));
        if (role == null){
            throw new RoleIDNotExistException();
        }
        Role nameRole = jooqRoleRepository.findByName(name);
        if (nameRole ==null){

        }else {
            if (nameRole.getName().equals(name)){

            }else {
                throw new RoleNameExistExceotion();
            }
        }
    }

}

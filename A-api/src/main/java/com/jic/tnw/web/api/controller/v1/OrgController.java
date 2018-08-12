package com.jic.tnw.web.api.controller.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jic.tnw.db.mysql.tables.pojos.Org;
import com.jic.tnw.user.service.OrgService;
import com.jic.tnw.user.service.dto.AddOrgDTO;
import com.jic.tnw.user.service.dto.EditOrgDTO;
import com.jic.tnw.user.service.dto.OrgTree;
import com.jic.tnw.web.api.config.LocaleMessageSourceService;
import com.jic.tnw.web.api.vo.request.org.AddOrg;
import com.jic.tnw.web.api.vo.request.org.EditOrg;
import com.jic.tnw.web.api.vo.response.org.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by lee5hx on 2017/12/10.
 */
@ApiIgnore
@RestController
@RequestMapping("/v1")
@Api(description = "机构管理", tags = {"F-用户模块-2"})
public class OrgController {

    private static final Log LOGGER = LogFactory.getLog(OrgController.class);

    private final LocaleMessageSourceService localeMessageSourceService;

    private final ObjectMapper mapper;

    private final OrgService orgService;

    @Autowired
    public OrgController(
            LocaleMessageSourceService localeMessageSourceService,
            ObjectMapper mapper,
            OrgService orgService) {
        this.localeMessageSourceService = localeMessageSourceService;
        this.mapper = mapper;
        this.orgService = orgService;
    }

    @RequestMapping(value = "/org", method = RequestMethod.GET,produces="application/hal+json")
    @ApiOperation(value = "返回机构", notes = "返回机构(tree)",produces = "application/hal+json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT令牌", required = true, paramType = "header", dataType = "String"),
    })
    public ResponseEntity<?> getOrgTree() throws Exception {
        GetOrgTreeResourceAssembler orgTreeResourceAssembler = new GetOrgTreeResourceAssembler(OrgController.class,OrgTreeResource.class);
        List<OrgTree> list = new ArrayList<>();
        list.add(orgService.getOrgTree());
        List<OrgTreeResource> resources = orgTreeResourceAssembler.toResources(list);
        Resources<OrgTreeResource> wrapped = new Resources<>(
                resources,
                linkTo(methodOn(OrgController.class).addOrg(null,null)).withRel("org_add"),
                linkTo(methodOn(OrgController.class).sortOrg()).withRel("org_sort")
        );
        return new ResponseEntity<>(wrapped, HttpStatus.OK);
    }





    @RequestMapping(value = "/org", method = RequestMethod.POST)
    @ApiOperation(value = "新增机构", notes = "在上级机构下结构")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT令牌", required = true, paramType = "header", dataType = "String"),
            @ApiImplicitParam(name = "addOrg", value = "新增机构", required = true, dataType = "AddOrg")
    })
    public ResponseEntity<?> addOrg(@Validated @RequestBody AddOrg addOrg, @AuthenticationPrincipal UserDetails user) throws Exception {
        AddOrgResource addOrgResource = new AddOrgResource();

        AddOrgDTO addOrgDTO = new AddOrgDTO();
        addOrgDTO.setCode(addOrg.getCode());
        addOrgDTO.setDescription(addOrg.getDescription());
        addOrgDTO.setParentId(addOrg.getParentId());
        addOrgDTO.setName(addOrg.getName());

        Org org  = orgService.addOrg(addOrgDTO,Integer.parseInt(user.getUsername()));
        addOrgResource.setName(org.getBranchNm());
        addOrgResource.setCode(org.getBranchNo());
        addOrgResource.setDescription(org.getDescription());
        addOrgResource.setParentId(org.getParentId());
        return new ResponseEntity<>(addOrgResource, HttpStatus.CREATED);
    }


    @RequestMapping(value = "/org/sort", method = RequestMethod.POST)
    @ApiOperation(value = "排序机构", notes = "同级机构排序显示")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT令牌", required = true, paramType = "header", dataType = "String"),
    })
    public ResponseEntity<?> sortOrg() throws Exception {

        return ResponseEntity.ok("ok");
    }

    @RequestMapping(value = "/org/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "根据获取信息", notes = "根据机构ID获取机构信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT令牌", required = true, paramType = "header", dataType = "String"),
            @ApiImplicitParam(name = "id", value = "机构ID", required = true, paramType = "path", dataType = "int")
    })
    public ResponseEntity<?> getOrg(@PathVariable Integer id) throws Exception {
        GetOrgResource getOrgResource = new GetOrgResource();
        Org org  = orgService.getOrg(id);
        getOrgResource.setOrgId(org.getId());
        getOrgResource.setCode(org.getBranchNo());
        getOrgResource.setDescription(org.getDescription());
        getOrgResource.setName(org.getBranchNo());
        return new ResponseEntity<>(getOrgResource, HttpStatus.OK);
    }

    @RequestMapping(value = "/org/{id}", method = RequestMethod.PUT)
    @ApiOperation(value = "修改机构", notes = "根据机构ID修改机构信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT令牌", required = true, paramType = "header", dataType = "String"),
            @ApiImplicitParam(name = "id", value = "机构ID", required = true, paramType = "path", dataType = "int"),
            @ApiImplicitParam(name = "editOrg", value = "新增机构", required = true, dataType = "EditOrg")
    })
    public ResponseEntity<?> editOrg(@PathVariable Integer id, @Validated @RequestBody EditOrg editOrg, @AuthenticationPrincipal UserDetails user) throws Exception {
        EditOrgResource editOrgResource = new EditOrgResource();
        EditOrgDTO editOrgDTO = new EditOrgDTO();
        editOrgDTO.setCode(editOrg.getCode());
        editOrgDTO.setDescription(editOrg.getDescription());
        editOrgDTO.setName(editOrg.getName());

        Org org  = orgService.updateOrg(id,editOrgDTO,Integer.valueOf(user.getUsername()));
        editOrgResource.setOrgId(org.getId());
        editOrgResource.setCode(org.getBranchNo());
        editOrgResource.setDescription(org.getDescription());
        editOrgResource.setName(org.getBranchNm());
        return new ResponseEntity<>(editOrgResource, HttpStatus.OK);
    }

    @RequestMapping(value = "/org/{id}", method = RequestMethod.DELETE)
    @ApiOperation(value = "删除机构", notes = "根据机构ID删除机构")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT令牌", required = true, paramType = "header", dataType = "String"),
            @ApiImplicitParam(name = "id", value = "机构ID", required = true, paramType = "path", dataType = "int")
    })
    public ResponseEntity<?> deleteOrg(@PathVariable Integer id) throws Exception {
        DeleteOrgResource deleteOrgResource = new DeleteOrgResource();
        Org org  = orgService.deleteOrg(id);
        deleteOrgResource.setOrgId(org.getId());
        deleteOrgResource.setCode(org.getBranchNo());
        deleteOrgResource.setName(org.getBranchNm());
        deleteOrgResource.setDescription(org.getDescription());
        return new ResponseEntity<>(deleteOrgResource, HttpStatus.NO_CONTENT);
    }
}

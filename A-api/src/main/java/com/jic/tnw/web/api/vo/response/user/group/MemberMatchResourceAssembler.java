package com.jic.tnw.web.api.vo.response.user.group;

import com.jic.tnw.user.service.dto.MatchMember;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

/**
 * @author lee5hx
 * @date 2018/03/25
 */
public class MemberMatchResourceAssembler extends ResourceAssemblerSupport<MatchMember, MemberMatchResource> {


    public MemberMatchResourceAssembler(Class<?> controllerClass, Class<MemberMatchResource> resourceType) {
        super(controllerClass, resourceType);
    }

    @Override
    public MemberMatchResource toResource(MatchMember entity) {
        MemberMatchResource memberMatchResource = new MemberMatchResource();
        memberMatchResource.setUserGroupMemberMemberType(entity.getUserGroupMemberMemberType());
        memberMatchResource.setMemberName(entity.getMemberName());
        memberMatchResource.setMemberId(entity.getMemberId());
        memberMatchResource.setMemberTrueName(entity.getMemberTrueName());
        memberMatchResource.setMemberTrueName(entity.getMemberTrueName());
        memberMatchResource.setPeopleNumber(entity.getPeopleNumber());

        return memberMatchResource;
    }
}

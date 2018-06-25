/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Other licenses:
 * -----------------------------------------------------------------------------
 * Commercial licenses for this work are available. These replace the above
 * ASL 2.0 and offer limited warranties, support, maintenance, and commercial
 * database integrations.
 *
 * For more information, please visit: http://www.jooq.org/licenses
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */
package com.jic.tnw.user.service;

import com.jic.tnw.db.mysql.tables.pojos.Role;
import com.jic.tnw.user.service.dto.WurTree;
import com.jic.tnw.user.service.dto.role.AddRoleDTO;
import com.jic.tnw.user.service.dto.role.EditRoleDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * This Book Service (or DAO or Repository) is used by this example to interact with the library's T_BOOK table.
 *
 * @author Lukas Eder
 */


public interface RoleService {

    List<Role> findAll();

    Page<Role> findRolesWithPageable(Pageable pageable);

    Map<String, String[]> getUrlRoleMap();

    Map<Integer, Role> getOrgMap();

    Page<Role> findWithPageable(Pageable pageable, Map<String, Object> conditionMap);

    WurTree getWurTree();

    Role findById(Integer id);

    Role deleteById(Integer id);

    Role addRole(AddRoleDTO addRole, Integer uid);

    Role updateRole(EditRoleDTO editRole, Integer uid, Integer roleId);

    Map<String, Integer> getIdByCodeRoleMap(String json);

    void verifyAddRoleCode(String code);

    void verifyAddRoleName(String name);

    void verifyEditRoleName(String name,String roleId);

//	String getRoleGroupByIds(String[] ids);

}

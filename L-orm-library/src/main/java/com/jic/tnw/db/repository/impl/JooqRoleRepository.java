package com.jic.tnw.db.repository.impl;

import com.jic.tnw.db.mysql.tables.pojos.Role;
import com.jic.tnw.db.mysql.tables.records.RoleRecord;
import com.jic.tnw.db.repository.RoleRepository;
import com.jic.tnw.db.repository.utils.JOOQPageSortUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Table;
import org.jooq.TableField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by lee5hx on 2017/10/30.
 */
@Repository
public class JooqRoleRepository extends AbstractJooqRepository<Role,RoleRecord> implements RoleRepository {

    private static final Log LOGGER = LogFactory.getLog(JooqRoleRepository.class);


    private final DSLContext jooq;

    @Autowired
    public JooqRoleRepository(DSLContext jooq) {
        this.jooq = jooq;
    }

    @Override
    public Role add(Role entry) {
        RoleRecord roleRecord = jooq.insertInto(com.jic.tnw.db.mysql.tables.Role.ROLE)
                .set(createRecord(entry))
                .returning()
                .fetchOne();
        Role role = convertQueryResultToPojo(roleRecord);
        return role;
    }
    private RoleRecord createRecord(Role entry){
        RoleRecord roleRecord = new RoleRecord();
        roleRecord.setName(entry.getName());
        roleRecord.setCode(entry.getCode());
        roleRecord.setData(entry.getData());
        roleRecord.setCreatedUserId(entry.getCreatedUserId());
        roleRecord.setCreatedTime(entry.getCreatedTime());
        roleRecord.setLastUpdUserId(entry.getLastUpdUserId());
        roleRecord.setLastUpdTime(entry.getLastUpdTime());
        return roleRecord;
    }
    @Override
    public Role delete(Integer id) {
        Role role = findById(id);
        int deletedRecordCount = jooq.delete(com.jic.tnw.db.mysql.tables.Role.ROLE)
                .where(com.jic.tnw.db.mysql.tables.Role.ROLE.ID.eq(id))
                .execute();
        LOGGER.debug(String.format("delete role role=%s",role));
        LOGGER.debug(String.format("delete count= %sd",deletedRecordCount));
        return role;
    }

    @Override
    public List<Role> findAll() {
        LOGGER.info("Finding all role entries.");
        List<RoleRecord> queryResults = jooq
                .selectFrom(com.jic.tnw.db.mysql.tables.Role.ROLE).orderBy(com.jic.tnw.db.mysql.tables.Role.ROLE.CREATED_TIME.asc())
                .fetchInto(RoleRecord.class);
        List<Role> rolesEntries = convertQueryResultToPojos(queryResults);
        LOGGER.info(String.format("Found [%s] role entries", rolesEntries.size()));
        return rolesEntries;
    }

    @Override
    public Role findById(Integer id) {
        RoleRecord roleResult = jooq.selectFrom(com.jic.tnw.db.mysql.tables.Role.ROLE)
                .where(com.jic.tnw.db.mysql.tables.Role.ROLE.ID.eq(id))
                .fetchOne();
        return convertQueryResultToPojo(roleResult);
    }
    @Override
    public Role update(Role entry) {
        return null;
    }


    private TableField getTableField(String sortFieldName) {
        TableField sortField = null;
        try {
            Field tableField = com.jic.tnw.db.mysql.tables.Role.ROLE.getClass().getField(sortFieldName);
            sortField = (TableField) tableField.get(com.jic.tnw.db.mysql.tables.Role.ROLE);
        } catch (NoSuchFieldException | IllegalAccessException ex) {
            String errorMessage = String.format("Could not find table field: {}", sortFieldName);
            throw new InvalidDataAccessApiUsageException(errorMessage, ex);
        }

        return sortField;
    }
    @Override
    public Role convertQueryResultToPojo(RoleRecord queryResult) {
        if (queryResult == null) {
            return null;
        }
        Role role = new Role();
        role.setId(queryResult.getId());
        role.setCode(queryResult.getCode());
        role.setName(queryResult.getName());
        role.setData(queryResult.getData());
        role.setCreatedUserId(queryResult.getCreatedUserId());
        role.setCreatedTime(queryResult.getCreatedTime());
        role.setLastUpdTime(queryResult.getLastUpdTime());
        role.setLastUpdUserId(queryResult.getLastUpdUserId());
        return role;
    }
    @Override
    public List<Role> convertQueryResultToPojos(List<RoleRecord> queryResults) {
        List<Role> roleEntries = new ArrayList<>();

        for (RoleRecord queryResult : queryResults) {
            Role roleEntry = convertQueryResultToPojo(queryResult);
            roleEntries.add(roleEntry);
        }

        return roleEntries;
    }

    @Override
    public Page<Role> find(Pageable pageable,Map<String, Object> conditionMap) {


        LOGGER.info(String.format("Finding [%d] todo entries for page [%d] ",
                pageable.getPageSize(),
                pageable.getPageNumber()
        ));

        List<RoleRecord> queryResults = jooq.select(
                    com.jic.tnw.db.mysql.tables.Role.ROLE.ID,
                    com.jic.tnw.db.mysql.tables.Role.ROLE.NAME,
                    com.jic.tnw.db.mysql.tables.Role.ROLE.CODE,
                    com.jic.tnw.db.mysql.tables.Role.ROLE.CREATED_TIME,
                    com.jic.tnw.db.mysql.tables.Role.ROLE.CREATED_USER_ID
        ).from(com.jic.tnw.db.mysql.tables.Role.ROLE)
                    .where(createWhereConditions(conditionMap))
                    .andNot(com.jic.tnw.db.mysql.tables.Role.ROLE.CODE.eq("ROLE_ANONYMOUS"))
                    .orderBy(JOOQPageSortUtils.getSortFields(com.jic.tnw.db.mysql.tables.Role.ROLE, pageable.getSort()))
                    .limit(pageable.getPageSize()).offset(pageable.getOffset())
                    .fetchInto(RoleRecord.class);

        List<Role> todoEntries = convertQueryResultToPojos(queryResults);

        LOGGER.info(String.format("Found [%d] todo entries for page: [%d]",
                todoEntries.size(),
                pageable.getPageNumber())
        );

        long totalCount = findCountByLikeExpression("");
        LOGGER.info(String.format("Found [%d] user entries for page: [%d]",
                todoEntries.size(),
                pageable.getPageNumber())
        );
        LOGGER.info(String.format("{} todo entries matches with the like expression: %d",
                totalCount,
                "")
        );

        return new PageImpl<>(todoEntries, pageable, totalCount);
    }
    private Condition createWhereConditions(Map<String, Object> conditionMap) {
        Condition condition = com.jic.tnw.db.mysql.tables.Role.ROLE.ID.isNotNull();
        if (conditionMap.get("name") != null) {
            String name = String.valueOf(conditionMap.get("name"));
            condition = condition.and(com.jic.tnw.db.mysql.tables.Role.ROLE.NAME.like("%"+ name + "%"));
        }
        if (conditionMap.get("code") != null) {
            String code = String.valueOf(conditionMap.get("code"));
            condition = condition.and(com.jic.tnw.db.mysql.tables.Role.ROLE.CODE.like("%"+ code + "%"));
        }

        return condition;
    }

    private long findCountByLikeExpression(String likeExpression) {
        LOGGER.debug(String.format("Finding search result count by using like expression: [%s]", likeExpression));

        long resultCount = jooq.fetchCount(
                jooq.select()
                        .from(com.jic.tnw.db.mysql.tables.Role.ROLE)
                //.where(createWhereConditions(likeExpression))
        );

        LOGGER.debug(String.format("Found search result count: [%d]", resultCount));

        return resultCount;
    }
    @Override
    public Role findByName(String name){
        RoleRecord roleResult = jooq.selectFrom(com.jic.tnw.db.mysql.tables.Role.ROLE)
                .where(com.jic.tnw.db.mysql.tables.Role.ROLE.NAME.eq(name))
                .fetchOne();
        return convertQueryResultToPojo(roleResult);
    }
    @Override
    public Role findByCode(String code){
        RoleRecord roleResult = jooq.selectFrom(com.jic.tnw.db.mysql.tables.Role.ROLE)
                .where(com.jic.tnw.db.mysql.tables.Role.ROLE.CODE.eq(code))
                .fetchOne();
        return convertQueryResultToPojo(roleResult);
    }
    @Override
    public Role addRole(String name,String code,String json,Integer id){
        RoleRecord roleRecord = jooq.insertInto(com.jic.tnw.db.mysql.tables.Role.ROLE)
                .set(createAddRoleRecord(name,code,json,id))
                .returning()
                .fetchOne();
        Role role = convertQueryResultToPojo(roleRecord);
        return role;
    }
    private RoleRecord createAddRoleRecord(String name,String code,String json,Integer id){
        RoleRecord roleRecord = new RoleRecord();
        roleRecord.setName(name);
        roleRecord.setCode(code);
        roleRecord.setData(json);
        roleRecord.setCreatedUserId(id);
        roleRecord.setCreatedTime(LocalDateTime.now());
        roleRecord.setLastUpdUserId(id);
        roleRecord.setLastUpdTime(LocalDateTime.now());
        return roleRecord;
    }
    @Override
    public Role updateRole(String name,Integer uid,String json,Integer roleId){
        int updateCount = jooq.update(com.jic.tnw.db.mysql.tables.Role.ROLE)
                .set(com.jic.tnw.db.mysql.tables.Role.ROLE.NAME,name)
                .set(com.jic.tnw.db.mysql.tables.Role.ROLE.DATA,json)
                .set(com.jic.tnw.db.mysql.tables.Role.ROLE.LAST_UPD_TIME, LocalDateTime.now())
                .set(com.jic.tnw.db.mysql.tables.Role.ROLE.LAST_UPD_USER_ID,uid)
                .where(com.jic.tnw.db.mysql.tables.Role.ROLE.ID.equal(roleId))
                .execute();
        LOGGER.debug(String.format("PostGroup update_time =====%s",updateCount));
        return findById(roleId);
    }

    @Override
    protected Table<RoleRecord> getTable() {
        return com.jic.tnw.db.mysql.tables.Role.ROLE;
    }

    @Override
    protected Class<Role> getPojoClass() {
        return Role.class;
    }
}

package com.jic.tnw.db.repository.impl;

import com.jic.tnw.db.mysql.tables.pojos.User;
import com.jic.tnw.db.mysql.tables.records.UserRecord;
import com.jic.tnw.db.repository.UserRepository;
import com.jic.tnw.db.repository.utils.JOOQPageSortUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jooq.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by lee5hx on 2017/10/30.
 */
@Repository
public class JooqUserRepository extends AbstractJooqRepository<User, UserRecord> implements UserRepository {

    private static final Log LOGGER = LogFactory.getLog(JooqUserRepository.class);


    private final DSLContext jooq;

    @Autowired
    public JooqUserRepository(DSLContext jooq) {
        this.jooq = jooq;
    }

    @Override
    public User add(User entry) {
        UserRecord persisted = jooq.insertInto(com.jic.tnw.db.mysql.tables.User.USER)
                .set(createRecord(entry))
                .returning()
                .fetchOne();
        User returned = convertQueryResultToPojo(persisted);
        return returned;
    }


    private UserRecord createRecord(User entry) {
        UserRecord record = new UserRecord();
        record.setEmail(entry.getEmail());
        record.setPassword(entry.getPassword());
        record.setUsername(entry.getUsername());
        record.setTruename(entry.getTruename());
        record.setType(entry.getType());
        record.setCreatedTime(LocalDateTime.now());
        record.setRoles(entry.getRoles());
        record.setPostId(entry.getPostId());
        return record;
    }

    @Override
    public User delete(Integer id) {
        return null;
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public String generateUserName() {
        jooq.execute("REPLACE INTO `username_sequence` (`replace_key`) VALUES ('a')");
        ResultQuery<Record> rt = jooq.resultQuery("SELECT LAST_INSERT_ID() as ID_NAME");
        Object dd = rt.fetch("ID_NAME").get(0);
        return String.format("EL%s", dd.toString());
    }


    @Override
    public void updatePassword(String userName, String password) {
        int updatedRecordCount = jooq.update(com.jic.tnw.db.mysql.tables.User.USER)
                .set(com.jic.tnw.db.mysql.tables.User.USER.PASSWORD, password)
                .set(com.jic.tnw.db.mysql.tables.User.USER.LAST_PASSWORD_RESET_TIME, LocalDateTime.now())
                .where(com.jic.tnw.db.mysql.tables.User.USER.USERNAME.equal(userName))
                .execute();
        LOGGER.debug(String.format("Updated [%d] User entry.", updatedRecordCount));
    }

    @Override
    public void updateLoginSuccess(String principal, String ip) {
        jooq.update(com.jic.tnw.db.mysql.tables.User.USER)
                .set(com.jic.tnw.db.mysql.tables.User.USER.LOGIN_TIME, LocalDateTime.now())
                .set(com.jic.tnw.db.mysql.tables.User.USER.LOGIN_IP, ip)
                .where(com.jic.tnw.db.mysql.tables.User.USER.ID.eq(Integer.valueOf(principal)))
                .execute();
    }




    @Override
    public void updatePromoted(Integer id,Boolean promoted) {
        jooq.update(com.jic.tnw.db.mysql.tables.User.USER)
                .set(com.jic.tnw.db.mysql.tables.User.USER.PROMOTED, promoted)
                .set(com.jic.tnw.db.mysql.tables.User.USER.PROMOTED_TIME, LocalDateTime.now())
                .where(com.jic.tnw.db.mysql.tables.User.USER.ID.eq(id))
                .execute();
    }
    @Override
    public void updatePromotedSeq(Integer id,Integer seq) {
        jooq.update(com.jic.tnw.db.mysql.tables.User.USER)
                .set(com.jic.tnw.db.mysql.tables.User.USER.PROMOTED_SEQ, seq)
                .where(com.jic.tnw.db.mysql.tables.User.USER.ID.eq(id))
                .execute();
    }



    @Override
    public void updateLoginFailure(String principal) {
        User user = findByPrincipal(principal);
        if (user != null) {
            jooq.update(com.jic.tnw.db.mysql.tables.User.USER)
                    .set(com.jic.tnw.db.mysql.tables.User.USER.LAST_PASSWORD_FAIL_TIME, LocalDateTime.now())
                    .set(com.jic.tnw.db.mysql.tables.User.USER.CONSECUTIVE_PASSWORD_ERROR_TIMES, user.getConsecutivePasswordErrorTimes() + 1)
                    .where(com.jic.tnw.db.mysql.tables.User.USER.ID.eq(user.getId()))
                    .execute();
        }
    }

    @Override
    public void updateOrgById(Integer uid, String orgIds, String orgCodes) {
        LOGGER.debug(String.format("Updated Org User.id = [%d] orgIds=[%s] orgCodes=[%s].",
                uid,
                orgIds,
                orgCodes
        ));

        int updatedRecordCount = jooq.update(com.jic.tnw.db.mysql.tables.User.USER)
                .set(com.jic.tnw.db.mysql.tables.User.USER.UPDATED_TIME, LocalDateTime.now())
                .set(com.jic.tnw.db.mysql.tables.User.USER.ORG_IDS, orgIds)
                .set(com.jic.tnw.db.mysql.tables.User.USER.ORG_CODES, orgCodes)
                .where(com.jic.tnw.db.mysql.tables.User.USER.ID.eq(uid))
                .execute();
        LOGGER.debug(String.format("Updated Org Record Count [%d] User entry.", updatedRecordCount));
    }

    @Override
    public void updatePostById(Integer uid, Integer postid) {
        LOGGER.debug(String.format("Update PostID for USER  uid=%s postid = %s", uid, postid));
        int updatePostIDCount = jooq.update(com.jic.tnw.db.mysql.tables.User.USER)
                .set(com.jic.tnw.db.mysql.tables.User.USER.UPDATED_TIME, LocalDateTime.now())
                .set(com.jic.tnw.db.mysql.tables.User.USER.POST_ID, postid)
                .where(com.jic.tnw.db.mysql.tables.User.USER.ID.eq(uid))
                .execute();
        LOGGER.debug(String.format("Update USER Count = %s", updatePostIDCount));
    }

    @Override
    public User findUserByMobile(String mobile) {
        UserRecord queryResult = jooq.selectFrom(com.jic.tnw.db.mysql.tables.User.USER)
                .where(com.jic.tnw.db.mysql.tables.User.USER.MOBILE.eq(mobile))
                .fetchOne();
        return convertQueryResultToPojo(queryResult);
    }

    @Override
    public User findByPrincipal(String principal) {
        UserRecord queryResult = jooq.selectFrom(com.jic.tnw.db.mysql.tables.User.USER).where(com.jic.tnw.db.mysql.tables.User.USER.EMAIL.eq(principal))
                .or(com.jic.tnw.db.mysql.tables.User.USER.USERNAME.eq(principal))
                .or(com.jic.tnw.db.mysql.tables.User.USER.MOBILE.eq(principal))
                .fetchOne();
        return convertQueryResultToPojo(queryResult);
    }

    @Override
    public User findByName(String name) {
        UserRecord queryResult = jooq.selectFrom(com.jic.tnw.db.mysql.tables.User.USER).where(com.jic.tnw.db.mysql.tables.User.USER.USERNAME.eq(name))
                .fetchOne();
        return convertQueryResultToPojo(queryResult);
    }

    @Override
    public User findByEmail(String email) {
        UserRecord queryResult = jooq.selectFrom(com.jic.tnw.db.mysql.tables.User.USER).where(com.jic.tnw.db.mysql.tables.User.USER.EMAIL.eq(email))
                .fetchOne();
        return convertQueryResultToPojo(queryResult);
    }



    @Override
    public User findById(Integer id) {
        UserRecord queryResult = jooq.selectFrom(com.jic.tnw.db.mysql.tables.User.USER).where(com.jic.tnw.db.mysql.tables.User.USER.ID.eq(id))
                .fetchOne();
        return convertQueryResultToPojo(queryResult);
    }

    @Override
    public User update(User entry) {
        return null;
    }

    @Override
    public List<User> findByContainsTrueName(String trueName) {
        List<UserRecord> queryResults = jooq.select().from(com.jic.tnw.db.mysql.tables.User.USER)
                .where(com.jic.tnw.db.mysql.tables.User.USER.TRUENAME.like(trueName + "%"))
                .fetchInto(UserRecord.class);
        return convertQueryResultToPojos(queryResults);
    }

    @Override
    public List<User> findTeachersByContainsUserName(String userName) {
        List<UserRecord> queryResults = jooq.select().from(com.jic.tnw.db.mysql.tables.User.USER)
                .where(com.jic.tnw.db.mysql.tables.User.USER.ROLES.contains("ROLE_TEACHER"))
                .and(com.jic.tnw.db.mysql.tables.User.USER.USERNAME.contains(userName))
                .fetchInto(UserRecord.class);
        return convertQueryResultToPojos(queryResults);
    }

    @Override
    public Integer findByContainsLocked() {
        List<UserRecord> queryResults;
        queryResults = jooq.select().from(com.jic.tnw.db.mysql.tables.User.USER)
                .where(com.jic.tnw.db.mysql.tables.User.USER.LOCKED.eq(true))
                .fetchInto(UserRecord.class);
        return queryResults.size();
    }

    @Override
    public Page<User> find(Map<String, Object> conditionMap, Pageable pageable) {

        LOGGER.info(String.format("Finding [%d] user entries for page [%d] ",
                pageable.getPageSize(),
                pageable.getPageNumber()
        ));

        List<UserRecord> queryResults = jooq.select(
                com.jic.tnw.db.mysql.tables.User.USER.ID,
                com.jic.tnw.db.mysql.tables.User.USER.USERNAME,
                com.jic.tnw.db.mysql.tables.User.USER.TRUENAME,
                com.jic.tnw.db.mysql.tables.User.USER.ROLES,
                com.jic.tnw.db.mysql.tables.User.USER.ORG_IDS,
                com.jic.tnw.db.mysql.tables.User.USER.POST_ID,
                com.jic.tnw.db.mysql.tables.User.USER.PROMOTED,
                com.jic.tnw.db.mysql.tables.User.USER.PROMOTED_SEQ,
                com.jic.tnw.db.mysql.tables.User.USER.PROMOTED_TIME,
                com.jic.tnw.db.mysql.tables.User.USER.LOCKED,
                com.jic.tnw.db.mysql.tables.User.USER.LOGIN_IP,
                com.jic.tnw.db.mysql.tables.User.USER.LOGIN_TIME
        ).from(com.jic.tnw.db.mysql.tables.User.USER)
                .where(createWhereConditions(conditionMap))
                .orderBy(JOOQPageSortUtils.getSortFields(com.jic.tnw.db.mysql.tables.User.USER, pageable.getSort()))
                .limit(pageable.getPageSize()).offset(pageable.getOffset())
                .fetchInto(UserRecord.class);

        List<User> userEntries = convertQueryResultToPojos(queryResults);

        LOGGER.info(String.format("Found [%d] user entries for page: [%d]",
                userEntries.size(),
                pageable.getPageNumber())
        );

        long totalCount = findCountByStatus(conditionMap);
        UserPageImpl<User> page = new UserPageImpl<>(userEntries, pageable, totalCount);
        return page;
    }

    private long findCountByStatus(Map<String, Object> conditionMap) {
        long resultCount = jooq.fetchCount(
                jooq.select()
                        .from(com.jic.tnw.db.mysql.tables.User.USER)
                        .where(createWhereConditions(conditionMap))
        );
        LOGGER.debug(String.format("Found search result count: [%d]", resultCount));

        return resultCount;
    }
    @Override
    public long countByAllUser(){
        long resultCount = jooq.fetchCount(
                    jooq.select()
                            .from(com.jic.tnw.db.mysql.tables.User.USER)
        );
        return resultCount;
    }

    private Condition createWhereConditions(Map<String, Object> conditionMap) {
        Condition condition = com.jic.tnw.db.mysql.tables.User.USER.ID.isNotNull();
        if (conditionMap.get("org") != null) {
            String org = String.valueOf(conditionMap.get("org"));
            condition = condition.and(com.jic.tnw.db.mysql.tables.User.USER.ORG_CODES.like("%"+String.format("%s.",org)+"%"));
        }
        if (conditionMap.get("post") != null) {
            Integer post = Integer.valueOf(conditionMap.get("post").toString());
            condition = condition.and(com.jic.tnw.db.mysql.tables.User.USER.POST_ID.eq(post));
        }
        if (conditionMap.get("role") != null) {
            String role = String.valueOf(conditionMap.get("role"));
            condition = condition.and(com.jic.tnw.db.mysql.tables.User.USER.ROLES.contains(role));
        }
        if (conditionMap.get("user_name") != null) {
            String role = String.valueOf(conditionMap.get("user_name"));
            condition = condition.and(com.jic.tnw.db.mysql.tables.User.USER.USERNAME.contains(role));
        }
        if (conditionMap.get("true_name") != null) {
            String trueName = String.valueOf(conditionMap.get("true_name"));
            condition = condition.and(com.jic.tnw.db.mysql.tables.User.USER.TRUENAME.contains(trueName));
        }
        if (conditionMap.get("mobile") != null) {
            String mobile = String.valueOf(conditionMap.get("mobile"));
            condition = condition.and(com.jic.tnw.db.mysql.tables.User.USER.MOBILE.contains(mobile));
        }
        if (conditionMap.get("email") != null) {
            String email = String.valueOf(conditionMap.get("email"));
            condition = condition.and(com.jic.tnw.db.mysql.tables.User.USER.EMAIL.contains(email));
        }
        if (conditionMap.get("loginIp") != null) {
            String loginIp = String.valueOf(conditionMap.get("loginIp"));
            condition = condition.and(com.jic.tnw.db.mysql.tables.User.USER.LOGIN_IP.eq(loginIp));
        }
        if (conditionMap.get("promoted") != null) {
            String promoted = String.valueOf(conditionMap.get("promoted"));
            condition = condition.and(com.jic.tnw.db.mysql.tables.User.USER.PROMOTED.eq(Boolean.valueOf(promoted)));
        }
        return condition;
    }

    @Override
    public Long countByOrg(Integer orgId){
        long resultCount = jooq.fetchCount(
                jooq.select()
                        .from(com.jic.tnw.db.mysql.tables.User.USER)
                        .where(com.jic.tnw.db.mysql.tables.User.USER.ORG_IDS.like(orgId.toString()+"%"))
        );

        LOGGER.info(String.format("countByOrg.count = %d",resultCount));
        return resultCount;
    }
    @Override
    public Long countByPost(Integer postId){
        long resultCount = jooq.fetchCount(
                jooq.select()
                        .from(com.jic.tnw.db.mysql.tables.User.USER)
                        .where(com.jic.tnw.db.mysql.tables.User.USER.POST_ID.eq(postId))
        );
        LOGGER.info(String.format("countByPost.count = %d",resultCount));
        return resultCount;
    }

    @Override
    public List<User> convertQueryResultToPojos(List<UserRecord> queryResults) {
        List<User> entries = new ArrayList<>();
        for (UserRecord queryResult : queryResults) {
            User userEntry = convertQueryResultToPojo(queryResult);
            entries.add(userEntry);
        }
        return entries;
    }
    @Override
    public User updateRolesById(String roles,Integer id){
        int updatePostIDCount = jooq.update(com.jic.tnw.db.mysql.tables.User.USER)
                .set(com.jic.tnw.db.mysql.tables.User.USER.UPDATED_TIME, LocalDateTime.now())
                .set(com.jic.tnw.db.mysql.tables.User.USER.ROLES, roles)
                .where(com.jic.tnw.db.mysql.tables.User.USER.ID.eq(id))
                .execute();
        LOGGER.debug(String.format("Update USER Count = %s", updatePostIDCount));
        return findById(id);
    }

    @Override
    public void updateLockById(Integer id, Boolean lock) {
        int updateLockByIdCount = jooq.update(com.jic.tnw.db.mysql.tables.User.USER)
                .set(com.jic.tnw.db.mysql.tables.User.USER.LOCKED, lock)
                .where(com.jic.tnw.db.mysql.tables.User.USER.ID.eq(id))
                .execute();
        LOGGER.debug(String.format("Update USER Count = %s", updateLockByIdCount));
    }
    @Override
    public User updateUserName(String name,Integer id){
        int updateNameById = jooq.update(com.jic.tnw.db.mysql.tables.User.USER)
                .set(com.jic.tnw.db.mysql.tables.User.USER.USERNAME,name)
                .where(com.jic.tnw.db.mysql.tables.User.USER.ID.eq(id))
                .execute();

        LOGGER.debug(String.format("Update USER Count = %s", updateNameById));
        return findById(id);
    }
    @Override
    public User updateUserPwd(String pwd,Integer id){
        int updatePwdById = jooq.update(com.jic.tnw.db.mysql.tables.User.USER)
                .set(com.jic.tnw.db.mysql.tables.User.USER.PASSWORD,pwd)
                .where(com.jic.tnw.db.mysql.tables.User.USER.ID.eq(id))
                .execute();
        LOGGER.debug(String.format("Update USER Count = %s", updatePwdById));
        return findById(id);
    }
    @Override
    public User updateUserMobile(String mobile,String turename,Integer id){
        int updateMobileById = jooq.update(com.jic.tnw.db.mysql.tables.User.USER)
                .set(com.jic.tnw.db.mysql.tables.User.USER.MOBILE,mobile)
                .set(com.jic.tnw.db.mysql.tables.User.USER.TRUENAME,turename)
                .where(com.jic.tnw.db.mysql.tables.User.USER.ID.eq(id))
                .execute();
        LOGGER.debug(String.format("Update USER Count = %s", updateMobileById));
        return findById(id);
    }

    @Override
    protected Table<UserRecord> getTable() {
        return com.jic.tnw.db.mysql.tables.User.USER;
    }

    @Override
    protected Class<User> getPojoClass() {
        return User.class;
    }
}

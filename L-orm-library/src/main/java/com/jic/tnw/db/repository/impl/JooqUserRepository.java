package com.jic.tnw.db.repository.impl;

import com.jic.tnw.db.mysql.tables.pojos.User;
import com.jic.tnw.db.mysql.tables.records.UserRecord;
import com.jic.tnw.db.repository.UserRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jooq.DSLContext;
import org.jooq.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

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

    @Override
    public User oldUserPwd(String pwd, String id) {
        int updatePwdById = jooq.update(com.jic.tnw.db.mysql.tables.User.USER)
                .set(com.jic.tnw.db.mysql.tables.User.USER.LUOLDPWD, pwd)
                .where(com.jic.tnw.db.mysql.tables.User.USER.LUUSERID.eq(id))
                .execute();
        LOGGER.debug(String.format("Update USER Count = %s", updatePwdById));
        return findById(id);
    }

    @Override
    public User updateUserPwd(String pwd, String id) {
        int updatePwdById = jooq.update(com.jic.tnw.db.mysql.tables.User.USER)
                .set(com.jic.tnw.db.mysql.tables.User.USER.LUPWD, pwd)
                .where(com.jic.tnw.db.mysql.tables.User.USER.LUUSERID.eq(id))
                .execute();
        LOGGER.debug(String.format("Update USER Count = %s", updatePwdById));
        return findById(id);
    }

    private UserRecord createRecord(User entry) {
        UserRecord record = new UserRecord();
        record.setLuemail(entry.getLuemail());
        record.setLupwd(entry.getLupwd());
        record.setLuusername(entry.getLuusername());
//        record.setTruename(entry.getTruename());
        record.setLubrowsertype(entry.getLubrowsertype());
        record.setLuregtime(LocalDateTime.now());
        record.setLuorgid(entry.getLuorgid());
        record.setPositionId(entry.getPositionId());
        return record;
    }

    @Override
    public User delete(String id) {
        return null;
    }

    @Override
    public List<User> findAll() {
        return null;
    }


    @Override
    public void updateLoginSuccess(String principal, String ip) {
        jooq.update(com.jic.tnw.db.mysql.tables.User.USER)
                .set(com.jic.tnw.db.mysql.tables.User.USER.LULASTLOGTIME, LocalDateTime.now())
                .set(com.jic.tnw.db.mysql.tables.User.USER.LULASTLOGIP, ip)
                .where(com.jic.tnw.db.mysql.tables.User.USER.LUUSERID.eq(String.valueOf(principal)))
                .execute();
    }


    @Override
    public void updateLoginFailure(String principal) {
        User user = findByPrincipal(principal);
        if (user != null) {
            jooq.update(com.jic.tnw.db.mysql.tables.User.USER)
                    .set(com.jic.tnw.db.mysql.tables.User.USER.LAST_PASSWORD_FAIL_TIME, LocalDateTime.now())
//                    .set(com.jic.tnw.db.mysql.tables.User.USER.CONSECUTIVE_PASSWORD_ERROR_TIMES, user.getConsecutivePasswordErrorTimes() + 1)
                    .where(com.jic.tnw.db.mysql.tables.User.USER.LUUSERID.eq(user.getLuuserid()))
                    .execute();
        }
    }


    @Override
    public User findUserByMobile(String mobile) {
        UserRecord queryResult = jooq.selectFrom(com.jic.tnw.db.mysql.tables.User.USER)
                .where(com.jic.tnw.db.mysql.tables.User.USER.LUMOBILE.eq(mobile))
                .fetchOne();
        return convertQueryResultToPojo(queryResult);
    }

    @Override
    public User findByPrincipal(String principal) {
        UserRecord queryResult = jooq.selectFrom(com.jic.tnw.db.mysql.tables.User.USER)
                .where(com.jic.tnw.db.mysql.tables.User.USER.LUUSERID.eq(principal))
                .or(com.jic.tnw.db.mysql.tables.User.USER.LUUSERNAME.eq(principal))
                .or(com.jic.tnw.db.mysql.tables.User.USER.LUMOBILE.eq(principal))
                .fetchOne();
        return convertQueryResultToPojo(queryResult);
    }


    @Override
    public User userSetMobile(String phoneNo, String id) {
        int userSetMobile = jooq.update(com.jic.tnw.db.mysql.tables.User.USER)
                .set(com.jic.tnw.db.mysql.tables.User.USER.LUMOBILE, phoneNo)
                .where(com.jic.tnw.db.mysql.tables.User.USER.LUUSERID.eq(id))
                .execute();
        LOGGER.debug(String.format("Update USER Count = %s", userSetMobile));
        return findById(id);
    }

    @Override
    public User findById(String id) {
        UserRecord queryResult = jooq.selectFrom(com.jic.tnw.db.mysql.tables.User.USER).where(com.jic.tnw.db.mysql.tables.User.USER.LUUSERID.eq(id))
                .fetchOne();
        return convertQueryResultToPojo(queryResult);
    }

    @Override
    public User uploadPortraitFile(String id, MultipartFile file) {
//        int userSetMobile = jooq.update(com.jic.tnw.db.mysql.tables.User.USER)
//                .set(com.jic.tnw.db.mysql.tables.User.USER.LUHEADPIC,file)
//                .where(com.jic.tnw.db.mysql.tables.User.USER.LUUSERID.eq(id))
//                .execute();
//        LOGGER.debug(String.format("Update USER Count = %s", userSetMobile));
        return null;
    }


    @Override
    public User portraitFile(String id, String url) {
        int portraitFile = jooq.update(com.jic.tnw.db.mysql.tables.User.USER)
                .set(com.jic.tnw.db.mysql.tables.User.USER.LUHEADPIC, url)
                .where(com.jic.tnw.db.mysql.tables.User.USER.LUUSERID.eq(id))
                .execute();

        LOGGER.debug(String.format("Update USER Count = %s", portraitFile));
        return findById(id);
    }

    /**
     * touxiang
     *
     * @param entry
     * @return
     */


//    @Override
//    public User update(User entry) {
//        return null;
//    }
    @Override
    public User update(User entry) {
        UserRecord record = jooq.newRecord(com.jic.tnw.db.mysql.tables.User.USER, entry);
        resetChangedOnNotNull(record);
        Integer updateCount = jooq.update(com.jic.tnw.db.mysql.tables.User.USER)
                .set(record)
                .where(com.jic.tnw.db.mysql.tables.User.USER.LUUSERID.eq(entry.getLuuserid()))
                .execute();
        LOGGER.info(String.format("user update count = %d", updateCount));
        return findById(entry.getLuuserid());
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
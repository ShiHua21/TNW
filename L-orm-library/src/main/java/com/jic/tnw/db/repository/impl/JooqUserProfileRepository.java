package com.jic.tnw.db.repository.impl;

import com.jic.tnw.db.mysql.tables.pojos.UserProfile;
import com.jic.tnw.db.mysql.tables.records.UserProfileRecord;
import com.jic.tnw.db.repository.UserProfileRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jooq.DSLContext;
import org.jooq.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JooqUserProfileRepository extends AbstractJooqRepository<UserProfile, UserProfileRecord> implements UserProfileRepository {

    private static final Log LOGGER = LogFactory.getLog(JooqUserProfileRepository.class);
    private final DSLContext jooq;

    @Autowired
    public JooqUserProfileRepository(DSLContext jooq) {
        this.jooq = jooq;
    }

    @Override
    public UserProfile add(UserProfile entry) {
        UserProfileRecord persisted = jooq.insertInto(com.jic.tnw.db.mysql.tables.UserProfile.USER_PROFILE)
                .set(createRecord(entry))
                .returning()
                .fetchOne();
        UserProfile returned = convertQueryResultToPojo(persisted);
        return returned;
    }

    private UserProfileRecord createRecord(UserProfile entry) {
        UserProfileRecord record = new UserProfileRecord();
        record.setId(entry.getId());
        return record;
    }

    @Override
    public UserProfile delete(String id) {
        int iid = Integer.parseInt(id);

        int deleteCount = jooq.delete(com.jic.tnw.db.mysql.tables.UserProfile.USER_PROFILE)
                .where(com.jic.tnw.db.mysql.tables.UserProfile.USER_PROFILE.ID.eq(iid))
                .execute();
        LOGGER.info(String.format("user profile delete count %d", deleteCount));
        return findById(id);
    }

    @Override
    public List<UserProfile> findAll() {
        List<UserProfileRecord> userProfileRecords = jooq.selectFrom(com.jic.tnw.db.mysql.tables.UserProfile.USER_PROFILE)
                .fetchInto(UserProfileRecord.class);
        List<UserProfile> userProfiles = convertQueryResultToPojos(userProfileRecords);
        return userProfiles;
    }

    @Override
    public UserProfile findById(String id) {
        int iid = Integer.parseInt(id);

        UserProfileRecord userProfileRecord = jooq.selectFrom(com.jic.tnw.db.mysql.tables.UserProfile.USER_PROFILE)
                .where(com.jic.tnw.db.mysql.tables.UserProfile.USER_PROFILE.ID.eq(iid))
                .fetchOne();
        UserProfile userProfile = convertQueryResultToPojo(userProfileRecord);
        return userProfile;
    }

    @Override
    public UserProfile update(UserProfile entry) {
        int updatedRecordCount = jooq.update(com.jic.tnw.db.mysql.tables.UserProfile.USER_PROFILE)
                .set(com.jic.tnw.db.mysql.tables.UserProfile.USER_PROFILE.ABOUT, entry.getAbout())
                .set(com.jic.tnw.db.mysql.tables.UserProfile.USER_PROFILE.IDCARD, entry.getIdcard())
                .set(com.jic.tnw.db.mysql.tables.UserProfile.USER_PROFILE.GENDER, entry.getGender())
                .set(com.jic.tnw.db.mysql.tables.UserProfile.USER_PROFILE.IAM, entry.getIam())
                .set(com.jic.tnw.db.mysql.tables.UserProfile.USER_PROFILE.QQ, entry.getQq())
                .set(com.jic.tnw.db.mysql.tables.UserProfile.USER_PROFILE.WEIBO, entry.getWeibo())
                .set(com.jic.tnw.db.mysql.tables.UserProfile.USER_PROFILE.WEIXIN, entry.getWeixin())
                .set(com.jic.tnw.db.mysql.tables.UserProfile.USER_PROFILE.SIGNATURE, entry.getSignature())
                .set(com.jic.tnw.db.mysql.tables.UserProfile.USER_PROFILE.SITE, entry.getSite())
                .where(com.jic.tnw.db.mysql.tables.UserProfile.USER_PROFILE.ID.eq(entry.getId()))
                .execute();
        LOGGER.info(String.format("user profile update count %s", updatedRecordCount));
        return findById(entry.getId().toString());
    }

    @Override
    protected Table<UserProfileRecord> getTable() {
        return com.jic.tnw.db.mysql.tables.UserProfile.USER_PROFILE;
    }


    @Override
    protected Class<UserProfile> getPojoClass() {
        return UserProfile.class;
    }
}

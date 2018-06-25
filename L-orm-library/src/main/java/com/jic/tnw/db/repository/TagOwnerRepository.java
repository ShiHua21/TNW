package com.jic.tnw.db.repository;

import com.jic.tnw.db.mysql.tables.pojos.TagOwner;

import java.util.List;

/**
 * @author tp
 * @date 2018/4/17
 */
public interface TagOwnerRepository extends JooqRepository<TagOwner> {
    List<TagOwner> findByOwnIdAndOwnType(Integer ownId, String OwnType);
}

package com.jic.tnw.db.repository;

import java.util.List;

/**
 * @author lee5hx
 */
public interface JooqRepository<T> {
    /**
     * add
     * @param entry
     * @return
     */
    T add(T entry);

    /**
     * delete
     * @param id
     * @return
     */
    T delete(Integer id);

    /**
     * findAll
     * @return
     */
    List<T> findAll();

    /**
     * findById
     * @param id
     * @return
     */
    T findById(Integer id);

    /**
     * update
     * @param entry
     * @return
     */
    T update(T entry);

}
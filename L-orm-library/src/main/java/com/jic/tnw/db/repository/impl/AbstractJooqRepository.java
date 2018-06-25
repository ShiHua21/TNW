package com.jic.tnw.db.repository.impl;

import org.jooq.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @param <T>
 * @param <Z>
 * @author lee5hx
 */
public abstract class AbstractJooqRepository<T, Z extends Record> {


    protected /* non-final */ Field<?>[] pk() {
        UniqueKey<?> key = getTable().getPrimaryKey();
        return key == null ? null : key.getFieldsArray();
    }


    /**
     * [#2700] [#3582] If a POJO attribute is NULL, but the column is NOT NULL
     * then we should let the database apply DEFAULT values
     */
    protected void resetChangedOnNotNull(Record record) {

        Field<?>[] pk = pk();
        int size = record.size();
        //主键过滤
        if (pk != null) {
            for (Field<?> field : pk) {
                record.changed(field, false);
            }
        }
        //字段为空过滤
        for (int i = 0; i < size; i++) {
            if (record.get(i) == null) {

                //if (!record.field(i).getDataType().nullable()){
                record.changed(i, false);
                //}
            }
        }
    }


    protected T convertQueryResultToPojo(Z queryResult) {
        if (queryResult == null) {
            return null;
        }
        //Record r = (Record) queryResult;
        //T t = null;
        //Class<T> tt = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        return queryResult.into(getPojoClass());
    }

    protected List<T> convertQueryResultToPojos(List<Z> queryResults) {
        List<T> entries = new ArrayList<>();
        for (Z queryResult : queryResults) {
            T entry = convertQueryResultToPojo(queryResult);
            entries.add(entry);
        }
        return entries;
    }

    /**
     * 获取表
     *
     * @return
     */
    protected abstract Table<Z> getTable();

    /**
     * 获取Pojo-Class
     *
     * @return
     */
    protected abstract Class<T> getPojoClass();

    protected Z createRecord(DSLContext jooq, T entry) {
        return (Z) jooq.newRecord(getTable(), entry);
    }

    protected T add(DSLContext jooq, T entry) {
        Record record = (Record) createRecord(jooq, entry);
        Z persisted = (Z) jooq.insertInto(getTable())
                .set(record)
                .returning()
                .fetchOne();
        return convertQueryResultToPojo(persisted);
    }
}

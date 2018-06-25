package com.jic.tnw.db.repository.utils;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jooq.SortField;
import org.jooq.Table;
import org.jooq.TableField;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * Created by lee5hx on 2017/10/30.
 */

public class JOOQPageSortUtils {

    private static final Log LOGGER = LogFactory.getLog(JOOQPageSortUtils.class);


    public static SortField<?> convertTableFieldToSortField(TableField tableField, Sort.Direction sortDirection) {
        if (sortDirection == Sort.Direction.ASC) {
            return tableField.asc();
        } else {
            return tableField.desc();
        }
    }

    //
    public static Collection<SortField<?>> getSortFields(Table<?> table, Sort sortSpecification) {
        LOGGER.debug(String.format("Getting sort fields from sort specification: [%s]", sortSpecification));
        Collection<SortField<?>> querySortFields = new ArrayList<>();

        if (sortSpecification == null) {
            LOGGER.debug("No sort specification found. Returning empty collection -> no sorting is done.");
            return querySortFields;
        }
        Iterator<Sort.Order> specifiedFields = sortSpecification.iterator();

        while (specifiedFields.hasNext()) {
            Sort.Order specifiedField = specifiedFields.next();

            String sortFieldName = specifiedField.getProperty();
            Sort.Direction sortDirection = specifiedField.getDirection();
            //LOGGER.debug(String.format("Getting sort field with name: {} and direction: {}", sortFieldName, sortDirection));

            TableField tableField = (TableField) table.field(sortFieldName);
            SortField<?> querySortField = convertTableFieldToSortField(tableField, sortDirection);
            querySortFields.add(querySortField);
        }

        return querySortFields;
    }


}

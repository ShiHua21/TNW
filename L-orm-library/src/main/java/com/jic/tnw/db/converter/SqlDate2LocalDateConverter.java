package com.jic.tnw.db.converter;

import org.jooq.Converter;

import java.sql.Date;
import java.time.LocalDate;

public class SqlDate2LocalDateConverter implements Converter<Date, LocalDate> {

    @Override
    public LocalDate from(Date databaseObject) {
        return (databaseObject != null)
                ? databaseObject.toLocalDate()
                : null;
    }

    @Override
    public Date to(LocalDate userObject) {
        return Date.valueOf(userObject);
    }

    @Override
    public Class<Date> fromType() {
        return Date.class;
    }

    @Override
    public Class<LocalDate> toType() {
        return LocalDate.class;
    }
}
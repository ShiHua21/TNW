package com.jic.tnw.db.converter;

import org.jooq.Converter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.TimeZone;

public class LocalDateTimeConverter1 implements Converter<Integer, LocalDateTime> {

//    public static void main(String[] agrs) {
//
//        Integer databaseObject = 1436160088;
//        System.out.println(LocalDateTime.ofInstant(Instant.ofEpochSecond(databaseObject), TimeZone
//                .getDefault().toZoneId()));
//    }

    @Override
    public LocalDateTime from(Integer databaseObject) {
        return (databaseObject!=null)
                ?LocalDateTime.ofInstant(Instant.ofEpochSecond(databaseObject), TimeZone.getDefault().toZoneId())
                :null;
    }

    @Override
    public Integer to(LocalDateTime userObject) {
        if(userObject == null){
            return 0;
        }
        return (int) userObject.atZone(ZoneId.systemDefault()).toEpochSecond();
    }

    @Override
    public Class<Integer> fromType() {
        return Integer.class;
    }

    @Override
    public Class<LocalDateTime> toType() {
        return LocalDateTime.class;
    }
}
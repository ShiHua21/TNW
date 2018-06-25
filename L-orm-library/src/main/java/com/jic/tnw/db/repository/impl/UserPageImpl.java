package com.jic.tnw.db.repository.impl;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

public class UserPageImpl<T> extends PageImpl<T> {

    private long lockUserCount;
    private Pageable pageable;


    public UserPageImpl(List<T> content, Pageable pageable, long total) {
        super(content, pageable, total);
        this.pageable = pageable;
    }

    public UserPageImpl(List<T> content) {
        super(content);
    }

    @Override
    public <S> Page<S> map(Converter<? super T, ? extends S> converter) {
        List<S> result = new ArrayList<>(super.getContent().size());
        for (T element : this) {
            result.add(converter.convert(element));
        }
       return new UserPageImpl<>(result,this.pageable,super.getTotalElements());
    }

    public long getLockUserCount() {
        return lockUserCount;
    }

    public void setLockUserCount(long lockUserCount) {
        this.lockUserCount = lockUserCount;
    }
}

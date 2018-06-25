package com.jic.tnw.web.api.vo.request;


import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.validation.annotation.Validated;

import java.util.Arrays;

/**
 * @author lee5hx
 */
@Validated
public class SortItemsVO {

    @NotEmpty(message = "{course.chapter.sort.ids.not.empty}")
    private String[] ids;

    public String[] getIds() {
        return ids;
    }

    public void setIds(String[] ids) {
        this.ids = ids;
    }

    @Override
    public String toString() {
        return "SortItemsVO{" +
                "ids=" + Arrays.toString(ids) +
                '}';
    }
}

package com.jic.elearning.thrid.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by lee5hx on 2017/11/29.
 */


public class TianmaMyOrdersPageResult {

    @JsonProperty("total")
    private Long total;

    private List<TianmaMyOrders> rows;


    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<TianmaMyOrders> getRows() {
        return rows;
    }

    public void setRows(List<TianmaMyOrders> rows) {
        this.rows = rows;
    }
}

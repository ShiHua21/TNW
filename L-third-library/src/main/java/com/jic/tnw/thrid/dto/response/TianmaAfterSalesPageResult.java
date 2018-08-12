package com.jic.tnw.thrid.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by lee5hx on 2017/11/29.
 */


public class TianmaAfterSalesPageResult {

    @JsonProperty("total")
    private Long total;

    private List<TianmaAfterSales> rows;


    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<TianmaAfterSales> getRows() {
        return rows;
    }

    public void setRows(List<TianmaAfterSales> rows) {
        this.rows = rows;
    }
}

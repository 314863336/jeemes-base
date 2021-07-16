package com.huitai.common.utils;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * description: Page <br>
 * date: 2020/4/9 15:36 <br>
 * author: XJM <br>
 * version: 1.0 <br>
 */
public class Page<T> extends com.baomidou.mybatisplus.extension.plugins.pagination.Page<T> {
    private T params;
    private String sortField;
    private String sortOrder;
    // 额外的参数
    private Map<String, Object> extraParams;


    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        if (!StringUtil.isEmpty(sortField)) {
            List<OrderItem> orderItems = super.getOrders();
            if (orderItems != null && orderItems.size() > 0) {
                orderItems.get(0).setColumn(sortField);
            }else{
                OrderItem orderItem = new OrderItem();
                orderItem.setColumn(sortField);
                orderItems = new ArrayList<>();
                orderItems.add(orderItem);
                super.setOrders(orderItems);
            }
        }

        this.sortField = sortField;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        if (!StringUtil.isEmpty(sortOrder)) {
            List<OrderItem> orderItems = super.getOrders();
            if (orderItems != null && orderItems.size() > 0) {
                orderItems.get(0).setAsc(sortField.indexOf("asc") >= 0 ? true : false);
            }else{
                OrderItem orderItem = new OrderItem();
                orderItems.get(0).setAsc(sortField.indexOf("asc") >= 0 ? true : false);
                orderItems = new ArrayList<>();
                orderItems.add(orderItem);
                super.setOrders(orderItems);
            }
        }
        this.sortOrder = sortOrder;
    }

    public T getParams() {
        return params;
    }

    public void setParams(T params) {
        this.params = params;
    }

    public Map<String, Object> getExtraParams() {
        return extraParams;
    }

    public void setExtraParams(Map<String, Object> extraParams) {
        this.extraParams = extraParams;
    }
}

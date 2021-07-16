package com.huitai.bpm.manage.entity;

import com.huitai.common.utils.Page;

/**
 * @description: 工作流分页拓展类 <br>
 * @author PLF <br>
 * @date 2020-12-01 11:15 <br>
 * @version 1.0 <br>
 */
public class FlwPage<T,L> extends Page<T> {

    private L list;

    public L getList() {
        return list;
    }

    public void setList(L list) {
        this.list = list;
    }
}

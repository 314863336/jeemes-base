package com.huitai.core.system.service;

import com.huitai.core.base.IBaseTreeService;
import com.huitai.core.system.entity.HtSysOffice;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author TYJ
 * @since 2020-04-15
 */
public interface HtSysOfficeService extends IBaseTreeService<HtSysOffice> {

    // 公司类型
    String CORP_TYPE = "0";

    // 部门类型
    String OFFICE_TYPE = "1";

    List<HtSysOffice> findOfficeList(HtSysOffice htSysOffice);

    List<HtSysOffice> treeData(HtSysOffice htSysOffice);

    HtSysOffice detail(String id);

    List<Serializable> listRedis();

}

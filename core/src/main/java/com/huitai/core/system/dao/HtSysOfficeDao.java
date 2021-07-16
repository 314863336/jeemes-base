package com.huitai.core.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.huitai.core.system.entity.HtSysOffice;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author TYJ
 * @since 2020-04-15
 */
public interface HtSysOfficeDao extends BaseMapper<HtSysOffice> {

    HtSysOffice getOfficeById(@Param("id")String id);

    List<HtSysOffice> findOfficeList(HtSysOffice htSysOffice);

}

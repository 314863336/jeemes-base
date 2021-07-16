package com.huitai.core.system.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;
import com.huitai.core.global.SystemConstant;
import com.huitai.common.utils.Md5Util;
import com.huitai.core.system.entity.HtSysConfig;
import com.huitai.core.system.entity.HtSysOffice;
import com.huitai.core.system.entity.HtSysUser;
import com.huitai.core.system.service.HtSysConfigService;
import com.huitai.core.system.service.HtSysOfficeService;
import com.huitai.core.system.service.HtSysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * description: 导入用户监听器
 * date: 2020/4/24 15:16
 * author: TYJ
 * version: 1.0
 */
public class HtSysUserListener extends AnalysisEventListener<HtSysUser> {

    private static final Logger LOGGER = LoggerFactory.getLogger(HtSysUserListener.class);
    /**
     * 每隔5条存储数据库，实际使用中可以3000条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 1000;
    List<HtSysUser> list = new ArrayList<>();
    /**
     * 假设这个是一个DAO，当然有业务逻辑这个也可以是一个service。当然如果不用存储这个对象没用。
     */
    private HtSysUserService htSysUserService;

    private HtSysConfigService htSysConfigService;

    private HtSysOfficeService htSysOfficeService;

    /**
     * 如果使用了spring,请使用这个构造方法。每次创建Listener的时候需要把spring管理的类传进来
     *
     * @param htSysUserService
     * @param htSysConfigService
     */
    public HtSysUserListener(HtSysUserService htSysUserService,
                             HtSysConfigService htSysConfigService,
                             HtSysOfficeService htSysOfficeService) {
        this.htSysUserService = htSysUserService;
        this.htSysConfigService = htSysConfigService;
        this.htSysOfficeService = htSysOfficeService;
    }
    /**
     * 这个每一条数据解析都会来调用
     *
     * @param data
     *            one row value. Is is same as {@link AnalysisContext#readRowHolder()}
     * @param context
     */
    @Override
    public void invoke(HtSysUser data, AnalysisContext context) {
        LOGGER.info("解析到一条数据:{}", JSON.toJSONString(data));
        list.add(data);
        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (list.size() >= BATCH_COUNT) {
            saveData();
            // 存储完成清理 list
            list.clear();
        }
    }
    /**
     * 所有数据解析完成了 都会来调用
     *
     * @param context
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        // 这里也要保存数据，确保最后遗留的数据也存储到数据库
        saveData();
        LOGGER.info("所有数据解析完成！");
    }
    /**
     * 加上存储数据库
     */
    private void saveData() {
        LOGGER.info("{}条数据，开始存储数据库！", list.size());
        /*从缓存中获取参数配置*/
        List<Serializable> configs = htSysConfigService.listRedis();
        String defaultPassword = null;
        HtSysConfig htSysConfig = null;
        for(Serializable s : configs){
            htSysConfig = (HtSysConfig)s;
            if(HtSysUserService.SYS_USER_INIT_PASSWORD.equals(htSysConfig.getConfigKey())){
                defaultPassword = Md5Util.stringToMD5(htSysConfig.getConfigValue());
            }
        }
        /*从缓存中获取部门公司信息*/
        configs = htSysOfficeService.listRedis();
        HtSysOffice htSysOffice = null;
        for(HtSysUser htSysUser : list){
            htSysUser.setPassword(defaultPassword);
            htSysUser.setUserType(0);
            htSysUser.setStatus(SystemConstant.ENABLE);
            htSysUser.setSex(htSysUser.getSexName());
            for(Serializable s : configs){
                htSysOffice = (HtSysOffice)s;
                if(HtSysOfficeService.CORP_TYPE.equals(htSysOffice.getOfficeType())
                        && htSysOffice.getOfficeCode().equals(htSysUser.getCompanyName())){
                    htSysUser.setCompanyId(htSysOffice.getId());
                }
                if(HtSysOfficeService.OFFICE_TYPE.equals(htSysOffice.getOfficeType())
                        && htSysOffice.getOfficeCode().equals(htSysUser.getOfficeName())){
                    htSysUser.setDeptId(htSysOffice.getId());
                }
            }
        }
        htSysUserService.saveBatch(list);
        LOGGER.info("存储数据库成功！");
    }
}

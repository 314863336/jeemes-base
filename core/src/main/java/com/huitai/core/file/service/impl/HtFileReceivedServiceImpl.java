package com.huitai.core.file.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.huitai.common.utils.Page;
import com.huitai.core.base.BaseServiceImpl;
import com.huitai.core.file.dao.HtFileReceivedDao;
import com.huitai.core.file.entity.HtFileInfo;
import com.huitai.core.file.entity.HtFileReceived;
import com.huitai.core.file.service.HtFileReceivedService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * <p>
 * 文档共享接收表 服务实现类
 * </p>
 *
 * @author XJM
 * @since 2020-05-29
 */
@Service
public class HtFileReceivedServiceImpl extends BaseServiceImpl<HtFileReceivedDao, HtFileReceived> implements HtFileReceivedService {


    /**
     * description: 获取分享给我文件列表 <br>
     * version: 1.0 <br>
     * date: 2020/5/28 15:45 <br>
     * author: XJM <br>
     */
    @DS("slave")
    @Transactional(readOnly = true)
    @Override
    public Page<HtFileReceived> findToMeList(Page<HtFileReceived> page){
        if(page.getParams() == null){
            page.setParams(new HtFileReceived());
        }
        if(page.getParams().getHtFileInfo() == null){
            page.getParams().setHtFileInfo(new HtFileInfo());
        }
        return baseMapper.findToMeList(page);
    }

}

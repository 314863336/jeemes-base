package com.huitai.core.message.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huitai.common.utils.Page;
import com.huitai.core.message.dao.HtMessageReceiveDao;
import com.huitai.core.message.entity.HtMessageReceive;
import com.huitai.core.message.entity.HtMessageSend;
import com.huitai.core.message.service.HtMessageReceiveService;
import com.huitai.core.system.entity.HtSysUser;
import com.huitai.core.utils.UserUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 接收消息表 服务实现类
 * </p>
 *
 * @author XJM
 * @since 2020-05-07
 */
@Service
public class HtMessageReceiveServiceImpl extends ServiceImpl<HtMessageReceiveDao, HtMessageReceive> implements HtMessageReceiveService {

    Logger logger = LoggerFactory.getLogger(HtMessageReceiveServiceImpl.class);

    /**
     * description: 根据发送消息创建接收消息 <br>
     * version: 1.0 <br>
     * date: 2020/5/7 10:24 <br>
     * author: XJM <br>
     */
    @Transactional(readOnly = false)
    @Override
    public void createReceiveMessage(HtMessageSend htMessageSend){
        HtMessageReceive htMessageReceive = new HtMessageReceive();
        htMessageReceive.setSendId(htMessageSend.getId());
        // 新增时都是未推送，页面定时查询到之后，再改为未读
        htMessageReceive.setReadStatus(HtMessageReceiveService.READ_STATUS_0);
        htMessageReceive.setMsgContent(htMessageSend.getMsgContent());
        htMessageReceive.setMsgTitle(htMessageSend.getMsgTitle());
        htMessageReceive.setReceiveUser(htMessageSend.getReceiveUser());
        htMessageReceive.setRemarks(htMessageSend.getRemarks());
        htMessageReceive.setSendUser(htMessageSend.getSendUser());
        htMessageReceive.setReceiveDate(new Date());
        super.save(htMessageReceive);
    }

    /**
     * description: 查询接收消息列表 <br>
     * version: 1.0 <br>
     * date: 2020/5/8 16:27 <br>
     * author: XJM <br>
     */
    @DS("slave")
    @Transactional(readOnly = true)
    @Override
    public Page<HtMessageReceive> findHtMessageReceiveList(Page<HtMessageReceive> page){
        if(page.getParams() == null){
            page.setParams(new HtMessageReceive());
        }
        return  baseMapper.findHtMessageReceiveList(page);
    }

    /**
     * description: 获取新消息 <br>
     * version: 1.0 <br>
     * date: 2020/5/9 17:06 <br>
     * author: XJM <br>
     */
    @Transactional(readOnly = false)
    @Override
    public List<HtMessageReceive> getNewMessage(){
        HtSysUser htSysUser = UserUtil.getCurUser();
        HtMessageReceive params = new HtMessageReceive();
        params.setReceiveUser(htSysUser.getId());
        params.setReadStatus(HtMessageReceiveService.READ_STATUS_0);
        List<HtMessageReceive> list = this.findNewHtMessageReceiveList(params);
        if(list != null && list.size() > 0){
            for (HtMessageReceive htMessageReceive : list) {
                // 修改状态为未读
                htMessageReceive.setReadStatus(HtMessageReceiveService.READ_STATUS_2);
                super.updateById(htMessageReceive);
            }
        }

        return list;
    }

    /**
     * description: 获取未推送消息列表 <br>
     * version: 1.0 <br>
     * date: 2020/5/9 17:22 <br>
     * author: XJM <br>
     */
    @DS("slave")
    @Transactional(readOnly = true)
    @Override
    public List<HtMessageReceive> findNewHtMessageReceiveList(HtMessageReceive htMessageReceive){
        return baseMapper.findNewHtMessageReceiveList(htMessageReceive);
    }

    /**
     * description: 修改已读状态 <br>
     * version: 1.0 <br>
     * date: 2020/5/11 9:59 <br>
     * author: XJM <br>
     */
    @Transactional(readOnly = false)
    @Override
    public void changeReadStatus(String[] ids){
        UpdateWrapper<HtMessageReceive> updateWrapper = new UpdateWrapper<>();
        // 否则全部修改为已读
        if(ids.length > 0){
            updateWrapper.in("id", ids);
        }
        updateWrapper.set("read_status", HtMessageReceiveService.READ_STATUS_1);
        super.update(updateWrapper);
    }
}

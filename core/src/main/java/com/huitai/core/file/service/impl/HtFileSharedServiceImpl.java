package com.huitai.core.file.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.huitai.common.utils.Page;
import com.huitai.core.base.BaseServiceImpl;
import com.huitai.core.file.dao.HtFileSharedDao;
import com.huitai.core.file.entity.HtFileInfo;
import com.huitai.core.file.entity.HtFileReceived;
import com.huitai.core.file.entity.HtFileShared;
import com.huitai.core.file.service.HtFileReceivedService;
import com.huitai.core.file.service.HtFileSharedService;
import com.huitai.core.system.entity.HtSysUser;
import com.huitai.core.utils.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * <p>
 * 文档共享表 服务实现类
 * </p>
 *
 * @author XJM
 * @since 2020-05-28
 */
@Service
public class HtFileSharedServiceImpl extends BaseServiceImpl<HtFileSharedDao, HtFileShared> implements HtFileSharedService {

    @Autowired
    private HtFileReceivedService htFileReceivedService;

    /**
     * description: 获取我分享的文件列表 <br>
     * version: 1.0 <br>
     * date: 2020/5/29 16:47 <br>
     * author: XJM <br>
     */
    @DS("slave")
    @Transactional(readOnly = true)
    @Override
    public Page<HtFileShared> findFormMeList(Page<HtFileShared> page){
        if(page.getParams() == null){
            page.setParams(new HtFileShared());
        }
        if(page.getParams().getHtFileInfo() == null){
            page.getParams().setHtFileInfo(new HtFileInfo());
        }
        return baseMapper.findFormMeList(page);
    }

    /**
     * description: 分享文件 <br>
     * version: 1.0 <br>
     * date: 2020/5/29 16:28 <br>
     * author: XJM <br>
     */
    @Transactional(readOnly = false)
    @Override
    public void shareFile(List<HtFileShared> htFileShareds){
        HtSysUser htSysUser = UserUtil.getCurUser();
        HtFileShared htFileShared = null;
        HtFileReceived htFileReceived = null;
        QueryWrapper<HtFileReceived> queryWrapper = null;
        String[] userIds = null;
        for (int i = 0; i < htFileShareds.size(); i++) {
            htFileShared = htFileShareds.get(i);
            htFileShared.setFromUserId(htSysUser.getId());
            super.save(htFileShared);
            userIds = htFileShared.getToUserIds().split(",");
            for (int j = 0; j < userIds.length; j++) {
                queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("file_info_id", htFileShared.getFileInfoId());
                queryWrapper.eq("from_user_id", htFileShared.getFromUserId());
                queryWrapper.eq("to_user_id", userIds[j]);
                htFileReceived = new HtFileReceived();
                htFileReceived.setShareId(htFileShared.getId());
                htFileReceived.setRemarks(htFileShared.getRemarks());
                htFileReceived.setToUserId(userIds[j]);
                htFileReceived.setFileInfoId(htFileShared.getFileInfoId());
                htFileReceived.setFromUserId(htFileShared.getFromUserId());
                htFileReceivedService.save(htFileReceived);

            }
        }
    }

    /**
     * description: 取消分享 <br>
     * version: 1.0 <br>
     * date: 2020/6/2 9:19 <br>
     * author: XJM <br>
     */
    @Transactional(readOnly = false)
    @Override
    public void deleteShared(String id){
        QueryWrapper<HtFileReceived> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("share_id", id);
        htFileReceivedService.remove(queryWrapper);

        super.removeById(id);
    }

}

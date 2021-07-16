package com.huitai.core.job.service;

import com.huitai.core.job.entity.HtSysJob;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 作业调度表 服务类
 * </p>
 *
 * @author TYJ
 * @since 2020-05-25
 */
public interface HtSysJobService extends IService<HtSysJob> {

    /**
     * description: 创建定时任务 <br>
     * version: 1.0 <br>
     * date: 2020/5/26 9:57 <br>
     * author: TYJ <br>
     */ 
    void saveTask(HtSysJob htSysJob) throws Exception;

    /**
     * description: 删除任务 <br>
     * version: 1.0 <br>
     * date: 2020/5/26 10:33 <br>
     * author: TYJ <br>
     */ 
    void deleteJob(String id) throws Exception;

    /**
     * description: 暂停任务 <br>
     * version: 1.0 <br>
     * date: 2020/5/26 10:45 <br>
     * author: TYJ <br>
     */ 
    void pause(String id) throws Exception;

    /**
     * description: 恢复任务 <br>
     * version: 1.0 <br>
     * date: 2020/5/26 10:57 <br>
     * author: TYJ <br>
     */ 
    void resume(String id) throws Exception;

    /**
     * description: 修改任务 <br>
     * version: 1.0 <br>
     * date: 2020/5/26 11:16 <br>
     * author: TYJ <br>
     */ 
    void updateTask(HtSysJob htSysJob) throws Exception;

    /**
     * description: 立即触发一次任务 <br>
     * version: 1.0 <br>
     * date: 2020/5/26 11:35 <br>
     * author: TYJ <br>
     */ 
    void trigger(String id) throws Exception;
}

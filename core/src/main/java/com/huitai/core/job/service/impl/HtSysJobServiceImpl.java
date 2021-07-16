package com.huitai.core.job.service.impl;

import com.huitai.core.base.BaseException;
import com.huitai.core.base.BaseServiceImpl;
import com.huitai.core.global.SystemConstant;
import com.huitai.core.job.dao.HtSysJobDao;
import com.huitai.core.job.entity.HtSysJob;
import com.huitai.core.job.service.HtSysJobService;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 作业调度表 服务实现类
 * </p>
 *
 * @author TYJ
 * @since 2020-05-25
 */
@Service
public class HtSysJobServiceImpl extends BaseServiceImpl<HtSysJobDao, HtSysJob> implements HtSysJobService {

    //Quartz定时任务核心的功能实现类
    private Scheduler scheduler;


    private final String trigger_prefix = "trigger";

    public HtSysJobServiceImpl(@Autowired SchedulerFactoryBean schedulerFactoryBean) {
        scheduler = schedulerFactoryBean.getScheduler();
    }

    /**
     * description: 新增保存任务 <br>
     * version: 1.0 <br>
     * date: 2020/5/26 10:58 <br>
     * author: TYJ <br>
     */ 
    @Override
    @Transactional
    public void saveTask(HtSysJob htSysJob) throws Exception{
        /*
         * 1.将任务记录插入数据库 2.将任务交由Scheduler安排触发
         */
        super.save(htSysJob);
        if(!CronExpression.isValidExpression(htSysJob.getCronExpression())){
            throw new BaseException("周期表达式格式不正确！");
        }
        Class cls;
        try {
             cls = Class.forName(htSysJob.getInvokeTarget());
        }catch (Exception e){
            throw new BaseException("执行类名路径不正确！");
        }
        cls.newInstance();
        // 构建job信息
        JobDetail job = JobBuilder.newJob(cls).withIdentity(htSysJob.getJobName(), htSysJob.getJobGroup())
                .withDescription(htSysJob.getDescription()).build();
        // 触发时间点
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(htSysJob.getCronExpression());
        //传递参数
        Map<String, Object> map = new HashMap<>();
        map.put("params", htSysJob.getParams());
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(trigger_prefix + htSysJob.getJobName(), htSysJob.getJobGroup())
//                .startNow()
                .withDescription(htSysJob.getDescription())
                .withSchedule(cronScheduleBuilder)
                .usingJobData(new JobDataMap(map))
                .build();
        // 交由Scheduler安排触发
        scheduler.scheduleJob(job, trigger);
    }

    @Override
    public void trigger(String id) throws Exception {
        HtSysJob htSysJob = getById(id);
        JobKey key = new JobKey(htSysJob.getJobName(), htSysJob.getJobGroup());
        //传参
        Map<String, Object> map = new HashMap<>();
        map.put("params", htSysJob.getParams());
        scheduler.triggerJob(key, new JobDataMap(map));
    }

    /**
     * description: 暂停任务 <br>
     * version: 1.0 <br>
     * date: 2020/5/26 10:58 <br>
     * author: TYJ <br>
     */ 
    @Override
    public void pause(String id) throws Exception {
        HtSysJob htSysJob = getById(id);
        htSysJob.setStatus(SystemConstant.DISABLE);
        super.updateById(htSysJob);
        //暂停任务
        scheduler.pauseJob(JobKey.jobKey(htSysJob.getJobName(), htSysJob.getJobGroup()));

    }

    /**
     * description: 恢复任务 <br>
     * version: 1.0 <br>
     * date: 2020/5/26 10:58 <br>
     * author: TYJ <br>
     */ 
    @Override
    public void resume(String id) throws Exception {
        HtSysJob htSysJob = getById(id);
        htSysJob.setStatus(SystemConstant.ENABLE);
        super.updateById(htSysJob);
        //恢复任务
        scheduler.resumeJob(JobKey.jobKey(htSysJob.getJobName(), htSysJob.getJobGroup()));
    }

    @Override
    @Transactional
    public void deleteJob(String id) throws Exception {
        HtSysJob htSysJob = getById(id);
        removeById(id);
        TriggerKey triggerKey = TriggerKey.triggerKey(htSysJob.getJobName(), htSysJob.getJobGroup());
        // 停止触发器
        scheduler.pauseTrigger(triggerKey);
        // 移除触发器
        scheduler.unscheduleJob(triggerKey);
        // 删除任务
        scheduler.deleteJob(JobKey.jobKey(htSysJob.getJobName(), htSysJob.getJobGroup()));
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public void updateTask(HtSysJob htSysJob) throws Exception {
        HtSysJob old = getById(htSysJob.getId());
        super.updateById(htSysJob);
        //1.如果cron表达式的格式不正确,则返回修改失败
        if (!CronExpression.isValidExpression(htSysJob.getCronExpression())){
            throw new BaseException("cron表达式格式错误！");
        }
        TriggerKey triggerKey = TriggerKey.triggerKey(trigger_prefix + htSysJob.getJobName(), htSysJob.getJobGroup());
        //2.如果cron或者参数发生变化了,则按新cron触发 进行重新启动定时任务
        if(!old.getCronExpression().equals(htSysJob.getCronExpression())
            || !((old.getParams() == null?"":old.getParams()).equals(htSysJob.getParams() == null?"":htSysJob.getParams()))){
            Map<String, Object> map = new HashMap<>();
            map.put("params", htSysJob.getParams());
            CronTrigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity(triggerKey)
                    .withSchedule(CronScheduleBuilder.cronSchedule(htSysJob.getCronExpression()))
                    .usingJobData(new JobDataMap(map))
                    .build();
            scheduler.rescheduleJob(triggerKey, trigger);
        }
    }


}

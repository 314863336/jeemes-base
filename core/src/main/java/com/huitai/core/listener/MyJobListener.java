package com.huitai.core.listener;

import com.huitai.common.utils.GenerateIdUtil;
import com.huitai.common.utils.SpringContextUtil;
import com.huitai.core.job.dao.HtSysJobLogDao;
import com.huitai.core.job.entity.HtSysJobLog;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * description: 任务监听器
 * date: 2020/5/29 13:58
 * author: TYJ
 * version: 1.0
 */
public class MyJobListener implements JobListener {

    Logger logger = LoggerFactory.getLogger(this.getClass());


    @Override
    public String getName() {
        return getClass().getSimpleName();
    }

    @Override
    public void jobToBeExecuted(JobExecutionContext context) {
    }

    @Override
    public void jobExecutionVetoed(JobExecutionContext context) {
    }

    @Override
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
        try {
            HtSysJobLogDao htSysJobLogDao = SpringContextUtil.getBean(HtSysJobLogDao.class);
            HtSysJobLog htSysJobLog = new HtSysJobLog();
            htSysJobLog.setId(GenerateIdUtil.getId());
            htSysJobLog.setJobName(context.getJobDetail().getKey().getName());
            htSysJobLog.setJobGroup(context.getJobDetail().getKey().getGroup());
            htSysJobLog.setCreateDate(new Date());
            if (jobException == null) {
                htSysJobLog.setJobMessage("运行成功！");
            } else {
                htSysJobLog.setJobMessage("运行失败！");
            }
            htSysJobLogDao.insert(htSysJobLog);
        }catch (Exception e){
            e.printStackTrace();
            logger.info("记录日志失败！");
        }

    }
}

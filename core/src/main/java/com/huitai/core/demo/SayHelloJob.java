package com.huitai.core.demo;

import com.alibaba.fastjson.JSONObject;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * description: 定时任务
 * date: 2020/5/25 19:32
 * author: TYJ
 * version: 1.0
 */
@DisallowConcurrentExecution
@Component
public class SayHelloJob implements Job {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try{
            Object obj = jobExecutionContext.getMergedJobDataMap().get("params");
            JSONObject jSONObject = JSONObject.parseObject(obj.toString());
            logger.info("参数：" + obj);
            logger.info(jSONObject.get("name").toString());
            //写你自己的逻辑
            logger.info("SayHelloJob.execute , hello world  ! ");
        }catch(Exception e){
            throw new JobExecutionException();
        }
    }

}

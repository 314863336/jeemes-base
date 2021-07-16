package com.huitai.bpm.listener;

import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;

/**
 * @description: 工作流任务监听 <br>
 * @author PLF <br>
 * @date 2020-12-02 11:48 <br>
 * @version 1.0 <br>
 */
public class FlwTaskListener implements TaskListener {
    @Override
    public void notify(DelegateTask delegateTask) {
        String eventName = delegateTask.getEventName();
        System.out.println(eventName);
    }
}

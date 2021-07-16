package com.huitai.bpm.listener;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;

/**
 * @description: 工作流执行监听 <br>
 * @author PLF <br>
 * @date 2020-12-02 11:16 <br>
 * @version 1.0 <br>
 */
public class FlwExecutionListener implements ExecutionListener {
    @Override
    public void notify(DelegateExecution delegateExecution) {
        String eventName = delegateExecution.getEventName();
        System.out.println(eventName);
    }
}

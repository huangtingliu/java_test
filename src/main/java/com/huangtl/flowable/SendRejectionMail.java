package com.huangtl.flowable;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;

/**
 * @Description TODO
 * @Author huangtl
 * @Date 2020/6/12 9:09
 **/
public class SendRejectionMail implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) {
        System.out.println("employee:"+execution.getVariable("employee")+" approved is "+execution.getVariable("approved"));
    }
}

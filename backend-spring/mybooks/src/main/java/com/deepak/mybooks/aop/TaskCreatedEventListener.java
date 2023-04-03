package com.deepak.mybooks.aop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.deepak.mybooks.entity.Task;
import com.deepak.mybooks.service.ExpertService;
import com.deepak.mybooks.service.TaskService;

@Component
public class TaskCreatedEventListener {

    @Autowired
    private ExpertService expertService;

    @Autowired
    private TaskService taskService;

    @Async 
    @EventListener
    public void onTaskCreated(TaskCreatedEvent event) {
        Task task = event.getTask();
        if(task.getOrderNum()==1) {
        	expertService.assignTaskToAvailableExpert(task);
        	taskService.update(task);
        }
        
    }
}

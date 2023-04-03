package com.deepak.mybooks.aop;

import org.springframework.context.ApplicationEvent;

import com.deepak.mybooks.entity.Task;

public class TaskCreatedEvent extends ApplicationEvent {
    
    private Task task;

    public TaskCreatedEvent(Object source, Task task) {
        super(source);
        this.task = task;
    }

    public Task getTask() {
        return task;
    }
}

package com.deepak.mybooks.aop;

import java.util.List;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.deepak.mybooks.entity.Task;

@Aspect
@Component
public class TaskCreatedEventAspect {

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @AfterReturning(
            pointcut = "execution(* com.deepak.mybooks.controller.TaskController.createTasks(..))",
            returning = "result"
    )
    public void afterCreateTask(JoinPoint joinPoint, Object result) {
        if (result instanceof ResponseEntity) {
            ResponseEntity<List<Task>> responseEntity = (ResponseEntity<List<Task>>) result;
            List<Task> tasks = responseEntity.getBody();
            for (Task task : tasks) {
                eventPublisher.publishEvent(new TaskCreatedEvent(this, task));
            }
        }
    }
}
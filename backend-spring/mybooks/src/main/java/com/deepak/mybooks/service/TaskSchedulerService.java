package com.deepak.mybooks.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@EnableScheduling
public class TaskSchedulerService {
	
	@Autowired
	private TaskService taskService;
	
	@Scheduled(cron = "0 * * * * *")
	public void assignExperts() {
		// assign expert to already assigned customers
		taskService.updateExperts();
	}
	
}

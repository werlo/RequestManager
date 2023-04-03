package com.deepak.mybooks.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deepak.mybooks.constants.TaskStatus;
import com.deepak.mybooks.entity.Customer;
import com.deepak.mybooks.entity.Task;
import com.deepak.mybooks.repo.CustomerRepository;
import com.deepak.mybooks.repo.TaskRepository;
import com.deepak.mybooks.service.ExpertService;
import com.deepak.mybooks.service.TaskService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("tasks")
public class TaskController {
	
	@Autowired
	private CustomerRepository customerRepo;
	
	@Autowired
	private TaskRepository taskRepo;
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private ExpertService expertService;
	
	@PostMapping("/customers/{customerId}")
	public ResponseEntity createTasks(@NotEmpty @NotNull @RequestBody List<Task> tasks, @NotEmpty @NotNull @PathVariable String customerId) {
	    Customer customer = customerRepo.findByName(customerId).get();
	    List<Task> arr = new ArrayList<>();
	    if (customer == null) {
	        return ResponseEntity.notFound().build();
	    }
	    for (Task task : tasks) {
	        task.setCustomer(customer);
	        task.setStatus(TaskStatus.QUEUED);
	        arr.add(taskService.create(task));
	    }
	    return ResponseEntity.ok().body(arr);
	}
	
	@GetMapping("customers/{customerId}")
	public ResponseEntity getTasks(@NotEmpty @NotNull @PathVariable String customerId) {
	    Customer customer = customerRepo.findByName(customerId).get();
	    List<Task> arr = new ArrayList<>();
	    if (customer == null) {
	        return ResponseEntity.notFound().build();
	    }
	    
	    arr = taskService.findByCustomer(customerId);
	    
	    return ResponseEntity.ok().body(arr);
	}
	
	@GetMapping("experts/{expertId}")
	public ResponseEntity getTasksForExpert(@NotEmpty @NotNull @PathVariable String expertId) {
	    List<Task> arr = new ArrayList<>();
	   
	    arr = taskService.findByExpert(expertId);
	    
	    return ResponseEntity.ok().body(arr);
	}
	
	@GetMapping("")
	public ResponseEntity getAllTasks() {
	    
	    List<Task> arr = new ArrayList<>();
	   
	    arr = taskService.findAll();
	    return ResponseEntity.ok().body(arr);
	}
	
	@GetMapping("resolve/{expertId}/{taskId}")
	public ResponseEntity resolveRequest(@NotEmpty @NotNull @PathVariable String expertId,@NotEmpty @NotNull @PathVariable Long taskId) {
		
		taskService.resolveTask(expertId, taskId);
		
		return ResponseEntity.ok().build();
		
	}
	
	
	@GetMapping("pick/{expertId}/{taskId}")
	public ResponseEntity pickRequest(@NotEmpty @NotNull @PathVariable String expertId,@NotEmpty @NotNull @PathVariable Long taskId) {
		
		taskService.pickTask(expertId, taskId);
		
		return ResponseEntity.ok().build();
		
	}
	
	
	
	
	
	

}

package com.deepak.mybooks.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deepak.mybooks.constants.TaskStatus;
import com.deepak.mybooks.entity.Expert;
import com.deepak.mybooks.entity.Task;
import com.deepak.mybooks.repo.ExpertRepository;
import com.deepak.mybooks.repo.TaskRepository;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    
    @Autowired
    private ExpertRepository expertRepository;
    
    
    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> findAll() {
        return taskRepository.findAll();
    }
    
    public List<Task> findByCustomer(String customerId) {
        return taskRepository.findByCustomerName(customerId);
    }

    public Task findById(Long id) {
        return taskRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Task not found"));
    }

    public Task create(Task task) {
        return taskRepository.save(task);
    }

    public Task update(Task task) {
        if (task.getId() == null) {
            throw new IllegalArgumentException("Task ID cannot be null");
        }
        Task existingTask = findById(task.getId());
        existingTask.setName(task.getName());
        existingTask.setDescription(task.getDescription());
        existingTask.setDeadline(task.getDeadline());
        existingTask.setEstimatedEffort(task.getEstimatedEffort());
        existingTask.setOrderNum(task.getOrderNum());
        existingTask.setStatus(task.getStatus());
        existingTask.setCustomer(task.getCustomer());
        existingTask.setExpert(task.getExpert());
        return taskRepository.save(existingTask);
    }

    public void delete(Long id) {
        Task task = findById(id);
        taskRepository.delete(task);
    }

    @Transactional
	public void updateExperts() {
		
		taskRepository.updateExpertIds();
		
	}
    
    @Transactional
    public void resolveTask(String expertId,long taskId) {
    	Task t = taskRepository.findById(taskId).get();
    	
    	t.setStatus(TaskStatus.COMPLETED);
    	
    	Expert e = expertRepository.findById(expertId).get();
    	
    	e.setBandWidth(e.getBandWidth()+t.getEstimatedEffort());
    	
    	taskRepository.save(t);
    	
    	expertRepository.save(e);
    	
    	
    }
    
    @Transactional
    public void pickTask(String expertId,long taskId) {
    	
    	Task t = taskRepository.findById(taskId).get();
    	
    	t.setStatus(TaskStatus.IN_PROGRESS);
    	
    	Expert e = expertRepository.findById(expertId).get();
    	
    	e.setBandWidth(e.getBandWidth()-t.getEstimatedEffort());
    	
    	taskRepository.save(t);
    	
    	expertRepository.save(e);
    	
    }

	public List<Task> findByExpert(@NotEmpty @NotNull String expertId) {
		
		return taskRepository.findByExpertName(expertId);
	}

}

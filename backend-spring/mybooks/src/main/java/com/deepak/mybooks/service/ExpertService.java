package com.deepak.mybooks.service;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.deepak.mybooks.constants.TaskStatus;
import com.deepak.mybooks.entity.Expert;
import com.deepak.mybooks.entity.Task;
import com.deepak.mybooks.repo.ExpertRepository;

@Service
public class ExpertService {

    private final ExpertRepository expertRepository;

    @Autowired
    public ExpertService(ExpertRepository expertRepository) {
        this.expertRepository = expertRepository;
    }

    public List<Expert> findAll() {
        return expertRepository.findAll();
    }

    public Expert findByName(String name) {
        return expertRepository.findById(name).orElseThrow(() -> new EntityNotFoundException("Expert not found"));
    }

    public Expert create(Expert expert) {
        return expertRepository.save(expert);
    }

    public Expert update(Expert expert) {
        Expert existingExpert = findByName(expert.getName());
        existingExpert.setBandWidth(expert.getBandWidth());
        existingExpert.setTasks(expert.getTasks());
        return expertRepository.save(existingExpert);
    }

    public void delete(String name) {
        Expert expert = findByName(name);
        expertRepository.delete(expert);
    }
    
    public void assignTaskToAvailableExpert(Task task) {
        // Get all experts whose max daily capacity is greater than or equal to the task's estimated effort
        List<Expert> candidateExperts = expertRepository.findByBandWidthGreaterThanEqualOrderByTasksAsc(task.getEstimatedEffort());

        // Check if there are any candidate experts
        if (!candidateExperts.isEmpty()) {
            // Iterate through the candidate experts and assign the task to the first available expert
            for (Expert expert : candidateExperts) {
                if (expert.getTasks().isEmpty()) {
                    expert.getTasks().add(task);
                    task.setExpert(expert);
                    task.setStatus(TaskStatus.IN_PROGRESS);
                    updateExpertCapacity(expert, task);
                    return ;
                } else {
                    Task lastTask = expert.getTasks().get(expert.getTasks().size() - 1);
                    LocalDateTime lastTaskDeadline = lastTask.getDeadline().plusHours(lastTask.getEstimatedEffort());
                    if (lastTaskDeadline.isBefore(task.getDeadline())) {
                        expert.getTasks().add(task);
                        task.setExpert(expert);
                        task.setStatus(TaskStatus.IN_PROGRESS);
                        updateExpertCapacity(expert, task);
                        return ;
                    }
                }
            }
        }
    }
    
    public void updateExpertCapacity(Expert expert, Task task) {
        int taskEffort = task.getEstimatedEffort();
        int newCapacity = expert.getBandWidth() - taskEffort;

        expert.setBandWidth(newCapacity);
        expertRepository.save(expert);
    }

}

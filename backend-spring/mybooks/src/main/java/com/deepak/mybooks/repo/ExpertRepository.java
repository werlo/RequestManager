package com.deepak.mybooks.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deepak.mybooks.entity.Expert;

public interface ExpertRepository extends JpaRepository<Expert, String> {

	List<Expert> findByBandWidthGreaterThanEqualOrderByTasksAsc(Integer estimatedEffort);
	

}

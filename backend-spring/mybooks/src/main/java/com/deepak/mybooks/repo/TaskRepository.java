package com.deepak.mybooks.repo;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.deepak.mybooks.entity.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {

	@Modifying
	@Query(value="UPDATE TASK t1 \n"
			+ "SET t1.expert_id = (\n"
			+ "  SELECT t2.expert_id \n"
			+ "  FROM TASK t2 \n"
			+ "  WHERE t2.customer_id = t1.customer_id \n"
			+ "  AND t2.order_num = '1' \n"
			+ "  AND t2.expert_id IS NOT NULL\n"
			+ ")\n"
			+",t1.status = 'PENDING' "
			+ "WHERE t1.expert_id IS NULL \n"
			+ "AND EXISTS (\n"
			+ "  SELECT * \n"
			+ "  FROM TASK t2 \n"
			+ "  WHERE t2.customer_id = t1.customer_id \n"
			+ "  AND t2.order_num = '1' \n"
			+ "  AND t2.expert_id IS NOT NULL\n"
			+ ")\n"
			+ "", nativeQuery=true)
	void updateExpertIds();

	List<Task> findByCustomerName(String customerId);

	List<Task> findByExpertName(@NotEmpty @NotNull String expertId);

	
}

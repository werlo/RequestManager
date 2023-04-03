package com.deepak.mybooks.repo;

import java.util.Optional;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deepak.mybooks.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, String> {

	Optional<Customer> findByName(@NotEmpty @NotNull String customerId);

}

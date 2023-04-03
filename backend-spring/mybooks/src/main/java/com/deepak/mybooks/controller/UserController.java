package com.deepak.mybooks.controller;

import java.util.List;
import java.util.Optional;

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

import com.deepak.mybooks.entity.Customer;
import com.deepak.mybooks.entity.Expert;
import com.deepak.mybooks.repo.CustomerRepository;
import com.deepak.mybooks.repo.ExpertRepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("users")
public class UserController {
	
	@Autowired
	private CustomerRepository customerRepo;
	
	@Autowired
	private ExpertRepository expertRepo;
	
	
	@GetMapping("customers")
	public ResponseEntity<List<Customer>> getAllCustomers(){
		
		return ResponseEntity.ok(customerRepo.findAll());
	}
	
	@GetMapping("experts")
	public ResponseEntity<List<Expert>> getAllExperts(){
		
		return ResponseEntity.ok(expertRepo.findAll());
	}
	
	@PostMapping("customer")
	public ResponseEntity<Customer> createCustomer(@NotNull @NotEmpty @RequestBody String customerId){
		
		Customer c = new Customer();
		c.setName(customerId);
		
		return ResponseEntity.of(Optional.ofNullable(customerRepo.save(c)));
	}
	
	@PostMapping("expert")
	public ResponseEntity<Optional<Expert>> createExpert(@NotNull @NotEmpty @RequestBody String expertId){
		Expert e = new Expert();
		e.setName(expertId);
		return ResponseEntity.ok(Optional.ofNullable(expertRepo.save(e)));
	}
	
	@GetMapping("customer/{customerId}")
	public ResponseEntity<Customer> getCustomer(@NotNull @NotEmpty @PathVariable String customerId){
		Optional<Customer> c = customerRepo.findById(customerId);
		if(c.isEmpty())
			return ResponseEntity.notFound().build();
		return ResponseEntity.ok().body(c.get());
	}
	
	@GetMapping("expert/{expertId}")
	public ResponseEntity<Expert> getExpert(@NotNull @NotEmpty @PathVariable String expertId){
		Optional<Expert> c = expertRepo.findById(expertId);
		if(c.isEmpty())
			return ResponseEntity.notFound().build();
		return ResponseEntity.ok().body(c.get());
	}

}

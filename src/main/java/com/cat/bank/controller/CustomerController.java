package com.cat.bank.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cat.bank.dto.CustomerDto;
import com.cat.bank.service.CustomerService;

@RestController
@RequestMapping("/customers")
public class CustomerController {

	private CustomerService custService;

	public CustomerController(CustomerService customerService) {
		this.custService = customerService;
	}

	@GetMapping(path = "/{customerId}")
	public ResponseEntity<CustomerDto> findCustomerById(@PathVariable("customerId") Long clstomerId) {
		CustomerDto custDto = custService.getCustomerById(clstomerId);
		return ResponseEntity.ok(custDto);
	}

	@GetMapping
	public ResponseEntity<List<CustomerDto>> gatAllCustomers() {
		List<CustomerDto> customers = custService.getAllCusotmers();
		return ResponseEntity.ok(customers);
	}

	@PostMapping
	public ResponseEntity<CustomerDto> createCustomer(@RequestBody CustomerDto customerDto) {
		CustomerDto createdCustomer = custService.createCustomer(customerDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdCustomer);
	}

	@PutMapping(path = "/edit/{customerId}")
	public ResponseEntity<CustomerDto> editCustomer(@PathVariable("customerId") Long customerId,
			@RequestBody CustomerDto customerDto) {
		CustomerDto updatedCustomer = custService.updateCustomer(customerId, customerDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(updatedCustomer);
	}

}

package com.cat.bank.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.cat.bank.dto.CustomerDto;
import com.cat.bank.model.Customer;
import com.cat.bank.repository.CustomerRepository;

@Service
public class CustomerServiceImpl implements CustomerService {

	private CustomerRepository custRepo;

	// Constructor
	public CustomerServiceImpl(CustomerRepository customerRepo) {
		this.custRepo = customerRepo;
	}

//Custom methods
	private CustomerDto convertToDto(Customer customer) {
		return new CustomerDto(customer.getCustomerId(), customer.getCustomerName(), customer.getEmail(),
				customer.getPhone(), customer.getAddress());
	}

	private Customer convertToEntity(CustomerDto customerDto) {
		Customer customerEntity = new Customer(customerDto.customerName(), customerDto.email(), customerDto.phone(),
				customerDto.address());
		return customerEntity;
	}

	// ------------------------------------------------------------------------------------------------
	@Override
	public CustomerDto createCustomer(CustomerDto customerDto) {
//		Customer customer = convertToEntity(customerDto);
//		Customer createdCustomer = custRepo.save(customer);
//		return convertToDto(createdCustomer);
		Customer customer = new Customer();

		if (customerDto.customerName() != null) {
			customer.setCustomerName(customerDto.customerName());
		} else {
			throw new IllegalArgumentException("Customer name is necessary");
		}

		if (customerDto.address() != null) {
			customer.setAddress(customerDto.address());
		} else {
			throw new IllegalArgumentException("Address cannot be empty");
		}

		if (customerDto.email() != null) {
			customer.setEmail(customerDto.email());
		} else {
			customer.setEmail(null);
		}

		if (customerDto.phone() != null) {
			customer.setPhone(customerDto.phone());
		} else {
			customer.setPhone(null);
		}

		Customer createdCustomer = custRepo.save(customer);
		return convertToDto(createdCustomer);

	}

	// ------------------------------------------------------------------------------------------------
	@Override
	public CustomerDto getCustomerById(Long customerId) {
		Customer customer = custRepo.findById(customerId)
				.orElseThrow(() -> new RuntimeException("Customer not found!"));
		return convertToDto(customer);
	}

	// ------------------------------------------------------------------------------------------------
	@Override
	public CustomerDto updateCustomer(Long customerId, CustomerDto customerDto) {
		Customer existingCustomer = custRepo.findById(customerId)
				.orElseThrow(() -> new RuntimeException("Customer with ID: " + customerId + "does not exist"));

		if (customerDto.customerName() != null) {
			existingCustomer.setCustomerName(customerDto.customerName());
		}
		if (customerDto.email() != null) {
			existingCustomer.setEmail(customerDto.email());
		}

		if (customerDto.phone() != null) {
			existingCustomer.setPhone(customerDto.phone());
		}

		if (customerDto.address() != null) {
			existingCustomer.setAddress(customerDto.address());
		}

		Customer updatedCustomer = custRepo.save(existingCustomer);

		return convertToDto(updatedCustomer);
	}

	// ------------------------------------------------------------------------------------------------
	@Override
	public List<CustomerDto> getAllCusotmers() {
		List<Customer> customers = custRepo.findAll();

		return customers.stream().map(customer -> new CustomerDto(customer.getCustomerId(), customer.getCustomerName(),
				customer.getEmail(), customer.getPhone(), customer.getAddress())).collect(Collectors.toList());
	}

}

package com.cat.bank.service;

import java.util.List;

import com.cat.bank.dto.CustomerDto;

public interface CustomerService {

	public CustomerDto getCustomerById(Long customerId);

	public List<CustomerDto> getAllCusotmers();

	public CustomerDto createCustomer(CustomerDto customer);

	public CustomerDto updateCustomer(Long customerId, CustomerDto customer);

}

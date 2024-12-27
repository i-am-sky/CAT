package com.cat.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cat.bank.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

}

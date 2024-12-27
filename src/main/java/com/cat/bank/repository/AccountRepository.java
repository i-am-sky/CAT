package com.cat.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cat.bank.model.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

}

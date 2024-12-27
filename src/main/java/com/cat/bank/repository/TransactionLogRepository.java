package com.cat.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cat.bank.model.TransactionLog;

public interface TransactionLogRepository extends JpaRepository<TransactionLog, Long> {

}

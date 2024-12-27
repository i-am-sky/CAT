package com.cat.bank.dto;

import java.math.BigDecimal;

import com.cat.bank.model.AccountType;

public record AccountDto(Long accountId, BigDecimal balance, AccountType accountType, Long customerId) {

}

package com.cat.bank.service;

import java.util.List;

import com.cat.bank.dto.AccountDto;
import com.cat.bank.dto.TransactionRequest;

public interface AccountService {

	public AccountDto getAccountById(Long accountId);

	public AccountDto createAccount(AccountDto account);

	public AccountDto updateAccount(Long accountId, AccountDto account);

	public Boolean moneyTransaction(TransactionRequest transactionRequest);

	public List<AccountDto> getAccounts();

}
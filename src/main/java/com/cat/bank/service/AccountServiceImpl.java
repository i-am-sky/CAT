package com.cat.bank.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.cat.bank.dto.AccountDto;
import com.cat.bank.dto.TransactionRequest;
import com.cat.bank.model.Account;
import com.cat.bank.model.Customer;
import com.cat.bank.model.TransactionLog;
import com.cat.bank.repository.AccountRepository;
import com.cat.bank.repository.CustomerRepository;
import com.cat.bank.repository.TransactionLogRepository;

@Service
public class AccountServiceImpl implements AccountService {

	AccountRepository accRepo;
	CustomerRepository customerRepo;
	TransactionLogRepository transactRepo;

	// Constructor
	public AccountServiceImpl(AccountRepository accountRepository, CustomerRepository customerRepo,
			TransactionLogRepository transactionRepo) {
		this.accRepo = accountRepository;
		this.customerRepo = customerRepo;
		this.transactRepo = transactionRepo;
	}

	// ------------------------------------------------------------------------------------------------
	// Custom methods
	public AccountDto convertToDto(Account account) {
		return new AccountDto(account.getAccountId(), account.getBalance(), account.getAccountType(),
				account.getCustomer().getCustomerId());
	}
//
//	public Account convertToEntity(AccountDto accountDto) {
////		Customer customer = 
//		Account account = new Account(accountDto.balance(), accountDto.accountType(), null);
//
//		return account;
//	}

	// ------------------------------------------------------------------------------------------------
	@Override
	public AccountDto getAccountById(Long accountId) {
		Account account = accRepo.findById(accountId)
				.orElseThrow(() -> new RuntimeException("Account with ID " + accountId + " does not exist"));
		return convertToDto(account);
	}

	// ------------------------------------------------------------------------------------------------
	@Override
	public List<AccountDto> getAccounts() {
		List<Account> accounts = accRepo.findAll();
		List<AccountDto> accountsListDto = accounts.stream().map(account -> new AccountDto(account.getAccountId(),
				account.getBalance(), account.getAccountType(), account.getCustomer().getCustomerId()))
				.collect(Collectors.toList());

		return accountsListDto;
	}

	// ------------------------------------------------------------------------------------------------
	@Override
	public AccountDto createAccount(AccountDto accountDto) {

		Account account = new Account();

		Customer customer = customerRepo.findById(accountDto.customerId())
				.orElseThrow(() -> new RuntimeException("Customer ID cannot be found"));

		if (accountDto.balance() != null) {
			account.setBalance(accountDto.balance());
		} else {
			account.setBalance(new BigDecimal("0.0"));
		}

		if (accountDto.accountType() != null) {
			account.setAccountType(accountDto.accountType());
		} else {
			throw new IllegalArgumentException("Account type cannot be empty");
		}

		if (accountDto.customerId() != null) {
			account.setCustomer(customer);
		}

		Account createdAccount = accRepo.save(account);
		return convertToDto(createdAccount);
	}

	// ------------------------------------------------------------------------------------------------
	@Override
	public AccountDto updateAccount(Long accountId, AccountDto accountDto) {
		Account existingAccount = accRepo.findById(accountId)
				.orElseThrow(() -> new RuntimeException("Account with ID: " + accountId + " was not found"));
		if (accountDto.balance() != null) {
			existingAccount.setBalance(accountDto.balance());
		}

		if (accountDto.accountType() != null) {
			existingAccount.setAccountType(accountDto.accountType());
		} else {
			throw new IllegalArgumentException("Account Type cannot be changed.");
		}

		Account updatedAccount = accRepo.save(existingAccount);
		return convertToDto(updatedAccount);
	}

	// ------------------------------------------------------------------------------------------------
	public Boolean moneyTransaction(TransactionRequest transactionRequest) {

		BigDecimal amount = BigDecimal.valueOf(transactionRequest.getMoney());
		Account sender = accRepo.findById(transactionRequest.getSenderId())
				.orElseThrow(() -> new RuntimeException("Sender's Id not found"));
		Account recipient = accRepo.findById(transactionRequest.getRecipientId())
				.orElseThrow(() -> new RuntimeException("recipient's Id not found"));

		BigDecimal senderBalance = sender.getBalance();

		if (amount.compareTo(BigDecimal.ZERO) > 0) {
			if (senderBalance.compareTo(amount) >= 0) {

				BigDecimal updatedSenderBalance = senderBalance.subtract(amount);
				BigDecimal updatedRecipientBalance = recipient.getBalance().add(amount);

				try {

					sender.setBalance(updatedSenderBalance);
					recipient.setBalance(updatedRecipientBalance);

					// update transaction log
					TransactionLog transactLog = new TransactionLog();
					transactLog.setAmount(new BigDecimal(transactionRequest.getMoney()));
					transactLog.setSenderAccId(sender.getAccountId());
					transactLog.setRecipientAccId(recipient.getAccountId());
					transactLog.setTransactionDate(LocalDateTime.now());

					accRepo.save(sender);
					accRepo.save(recipient);
					transactRepo.save(transactLog);

					return true;

				} catch (Exception e) {
					System.out.println("Trasaction Failed: " + e.getMessage());
					return false;
				}
			} else {
				System.out.println("Sender does't have anough money");
			}

		} else {
			System.out.println("Amount should be GT 0");
		}
		return false;
	}
}

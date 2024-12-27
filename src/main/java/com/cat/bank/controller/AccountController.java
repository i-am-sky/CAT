package com.cat.bank.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cat.bank.dto.AccountDto;
import com.cat.bank.dto.TransactionRequest;
import com.cat.bank.service.AccountService;

@RestController
@RequestMapping("/accounts")
public class AccountController {

	private AccountService accService;

	public AccountController(AccountService accountService) {
		this.accService = accountService;
	}

	@GetMapping(path = "/{accountId}")
	public ResponseEntity<AccountDto> findAccountById(@PathVariable("accountId") Long accountId) {
		AccountDto account = accService.getAccountById(accountId);
		return ResponseEntity.ok(account);
	}

	@GetMapping
	public ResponseEntity<List<AccountDto>> getAccounts() {
		List<AccountDto> allAccounts = accService.getAccounts();
		return ResponseEntity.ok(allAccounts);
	}

	@PostMapping
	public ResponseEntity<AccountDto> createAccount(@RequestBody AccountDto acountDto) {
		AccountDto createdAccount = accService.createAccount(acountDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdAccount);
	}

	@PostMapping(path = "/transact")
	public ResponseEntity<String> transactAmount(@RequestBody TransactionRequest transactRequest) {
		boolean TransactionSuccess = false;
		if (transactRequest.getSenderId() != null) {
			if (transactRequest.getRecipientId() != null) {
				if (transactRequest.getMoney() != null) {
					TransactionSuccess = accService.moneyTransaction(transactRequest);
				} else {
//					System.out.println("Money cannot be ZERO");
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Money cannot be ZERO");
				}
			} else {
//				System.out.println("Invalid Recipient ID");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Recipient ID");
			}
		} else {
//			System.out.println("Invalid Sender ID");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Sender ID");
		}

		if (TransactionSuccess) {
			return ResponseEntity.status(HttpStatus.OK).body("Transaction Success");
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Transaction could not be completed");
		}
	}

}

package com.cat.bank.model;

import java.math.BigDecimal;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Data
@Getter
@Setter
@Table(name = "Accounts")
public class Account {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long accountId;
	private BigDecimal balance;
	private AccountType accountType;

	@OneToOne
	@JoinColumn(name = "customer_id", nullable = false)
	private Customer customer;

	public Account() {

	}

	public Account(BigDecimal balance, AccountType accountType, Customer customer) {
		this.balance = balance;
		this.accountType = accountType;
		this.customer = customer;
	}

	public Long getAccountId() {
		return accountId;
	}

//	public void setAccountId(Long accountId) {
//		this.accountId = accountId;
//	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public AccountType getAccountType() {
		return accountType;
	}

	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	@Override
	public int hashCode() {
		return Objects.hash(accountId, accountType, balance, customer);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		return Objects.equals(accountId, other.accountId) && accountType == other.accountType
				&& Objects.equals(balance, other.balance) && Objects.equals(customer, other.customer);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Account [accountId=");
		builder.append(accountId);
		builder.append(", balance=");
		builder.append(balance);
		builder.append(", accountType=");
		builder.append(accountType);
		builder.append(", customer=");
		builder.append(customer);
		builder.append("]");
		return builder.toString();
	}

}

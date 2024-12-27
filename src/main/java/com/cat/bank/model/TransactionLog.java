package com.cat.bank.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class TransactionLog {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long transactionId;
	private Long senderAccId;
	private Long recipientAccId;
	private BigDecimal amount;
	private LocalDateTime transactionDate;

	public TransactionLog() {

	}

	public Long getTransactionId() {
		return transactionId;
	}

//	public void setTransactionId(Long transactionId) {
//		this.transactionId = transactionId;
//	}

	public Long getSenderAccId() {
		return senderAccId;
	}

	public void setSenderAccId(Long senderAccId) {
		this.senderAccId = senderAccId;
	}

	public Long getRecipientAccId() {
		return recipientAccId;
	}

	public void setRecipientAccId(Long recipientAccId) {
		this.recipientAccId = recipientAccId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public LocalDateTime getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(LocalDateTime transactionDate) {
		this.transactionDate = transactionDate;
	}

	@Override
	public int hashCode() {
		return Objects.hash(amount, recipientAccId, senderAccId, transactionDate, transactionId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TransactionLog other = (TransactionLog) obj;
		return Objects.equals(amount, other.amount) && Objects.equals(recipientAccId, other.recipientAccId)
				&& Objects.equals(senderAccId, other.senderAccId)
				&& Objects.equals(transactionDate, other.transactionDate)
				&& Objects.equals(transactionId, other.transactionId);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TransactionLog [transactionId=");
		builder.append(transactionId);
		builder.append(", senderAccId=");
		builder.append(senderAccId);
		builder.append(", recipientAccId=");
		builder.append(recipientAccId);
		builder.append(", amount=");
		builder.append(amount);
		builder.append(", transactionDate=");
		builder.append(transactionDate);
		builder.append("]");
		return builder.toString();
	}

}

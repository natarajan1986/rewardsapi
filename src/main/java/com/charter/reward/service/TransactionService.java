package com.charter.reward.service;

import java.util.List;

import com.charter.reward.db.dto.Transaction;

public interface TransactionService {

	public Transaction createTransaction(Transaction transaction);

	public Transaction updateTransaction(Transaction transaction);

	public Transaction getTransactionById(Long id);
	
	public String deleteTransactionById(Long id);

	public List<Transaction> getTransactionsByCustomerId(Long customerId);

	public List<Transaction> getAllTransactions();

}

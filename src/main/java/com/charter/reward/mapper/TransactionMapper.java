package com.charter.reward.mapper;

import org.springframework.stereotype.Component;

import com.charter.reward.db.dto.Customer;
import com.charter.reward.db.dto.Transaction;
import com.charter.reward.db.entity.CustomerEntity;
import com.charter.reward.db.entity.CustomerTransaction;

@Component
public class TransactionMapper {

	/**
	 * This method is used to convert the Transaction Details to CustomerEntity and
	 * vice versa
	 * 
	 * @param cTransaction
	 * @return
	 */
	public Transaction mapTransactionEntityToTransaction(CustomerTransaction cTransaction) {
		Transaction tx = new Transaction();
		tx.setId(cTransaction.getId());
		tx.setCustomerId(cTransaction.getCustomer().getId());
		tx.setAmount(cTransaction.getAmount());
		tx.setComments(cTransaction.getComments());
		tx.setTransactionDate(cTransaction.getTransactionDate());
		tx.setRewardPoints(cTransaction.getRewardPoints());
		return tx;
	}

	public CustomerEntity mapTransactionToTransactionEntity(Customer customer) {
		CustomerEntity customerEntity = new CustomerEntity();
		customerEntity.setUsername(customer.getUsername());
		customerEntity.setPassword(customer.getPassword());
		customerEntity.setEmail(customer.getEmail());
		customerEntity.setPhoneNumber(customer.getPhoneNumber());
		return customerEntity;
	}

//	Transaction tx = new Transaction();
//	tx.setId(cTransaction.getId());
//	tx.setCustomerId(cTransaction.getCustomer().getId());
//	tx.setAmount(cTransaction.getAmount());
//	tx.setComments(cTransaction.getComments());
//	tx.setTransactionDate(cTransaction.getTransactionDate());
//	tx.setRewardPoints(cTransaction.getRewardPoints());
}

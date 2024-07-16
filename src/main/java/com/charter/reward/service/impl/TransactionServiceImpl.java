package com.charter.reward.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.charter.reward.constants.ApplicationConstants;
import com.charter.reward.db.dto.Transaction;
import com.charter.reward.db.entity.CustomerEntity;
import com.charter.reward.db.entity.CustomerTransaction;
import com.charter.reward.db.repository.CustomerRepository;
import com.charter.reward.db.repository.TransactionRepository;
import com.charter.reward.exception.BadRequestException;
import com.charter.reward.exception.ResourceNotFoundException;
import com.charter.reward.mapper.TransactionMapper;
import com.charter.reward.service.TransactionService;
import com.charter.reward.util.RewardUtil;

@Service
public class TransactionServiceImpl implements TransactionService {

	public TransactionServiceImpl(TransactionMapper transactionMapper, CustomerRepository customerRepository,
			TransactionRepository transactionRepository,RewardUtil rewardUtil) {
		this.transactionMapper = transactionMapper;
		this.customerRepository = customerRepository;
		this.transactionRepository = transactionRepository;
		this.rewardUtil = rewardUtil;
	}

	 private static final Logger logger = LogManager.getLogger(TransactionServiceImpl.class);
	
	private TransactionMapper transactionMapper;
	
	private RewardUtil rewardUtil;

	@Autowired
	public CustomerRepository customerRepository;

	@Autowired
	public TransactionRepository transactionRepository;

	private void validateTransaction(Transaction transaction) {
		if (transaction.getCustomerId() == null) {
			throw new BadRequestException("Customer Id is required");
		} else if (transaction.getAmount() == null) {
			throw new BadRequestException("Amount can not be empty.");
		}

	}

	/**
	 * Method to make transaction entry for a existing customer
	 */
	@Transactional("rewardTransactionsManager")
	public Transaction createTransaction(Transaction transaction) {
		logger.info("Create transaction for customer "+transaction);
		validateTransaction(transaction);
		CustomerEntity customerEntity = new CustomerEntity();
		customerEntity = customerRepository.findById(transaction.getCustomerId()).orElseThrow();
		CustomerTransaction customerTransaction = new CustomerTransaction();
		customerTransaction.setAmount(transaction.getAmount());
		customerTransaction.setComments(transaction.getComments());
		customerTransaction.setTransactionDate(RewardUtil.getCurrentLocalDateTime());
		customerTransaction.setRewardPoints(rewardUtil.calculateRewardPoints(transaction.getAmount()));
		customerTransaction.setCustomer(customerEntity);
		customerTransaction = transactionRepository.save(customerTransaction);
		return transactionMapper.mapTransactionEntityToTransaction(customerTransaction);
	}

	/**
	 * Update transaction for a existing customer
	 */
	@Transactional("rewardTransactionsManager")
	public Transaction updateTransaction(Transaction transaction) {
		validateTransaction(transaction);
		CustomerTransaction customerTransaction = new CustomerTransaction();
		CustomerEntity customerEntity = new CustomerEntity();
		customerTransaction = transactionRepository.findById(transaction.getId()).orElseThrow();
		customerEntity = customerRepository.findById(transaction.getCustomerId()).orElseThrow();
		customerTransaction.setAmount(transaction.getAmount());
		customerTransaction.setComments(transaction.getComments());
		customerTransaction.setTransactionDate(RewardUtil.getCurrentLocalDateTime());
		customerTransaction.setRewardPoints(rewardUtil.calculateRewardPoints(transaction.getAmount()));
		customerTransaction.setCustomer(customerEntity);
		customerTransaction = transactionRepository.save(customerTransaction);
		return transactionMapper.mapTransactionEntityToTransaction(customerTransaction);
	}

	/**
	 * Fetch transaction based on transaction id
	 */
	@Transactional(label = "rewardTransactionsManager", readOnly = true)
	public Transaction getTransactionById(Long id) {
		Optional<CustomerTransaction> tx = Optional.of(new CustomerTransaction());
		tx = transactionRepository.findById(id);
		if (tx.isEmpty())
			throw new ResourceNotFoundException("Resource not found with id: " + id);
		return transactionMapper.mapTransactionEntityToTransaction(tx.get());
	}

	/**
	 * Fetch all transaction based on customer id
	 */
	@Transactional(label = "rewardTransactionsManager", readOnly = true)
	public List<Transaction> getTransactionsByCustomerId(Long customerId) {
		List<CustomerTransaction> customerTransactions = transactionRepository.findTransactionsByCustomerId(customerId);
		if (CollectionUtils.isEmpty(customerTransactions))
			throw new ResourceNotFoundException("Resource not found ");
		List<Transaction> transactionList = customerTransactions.stream()
				.map(transactionMapper::mapTransactionEntityToTransaction).collect(Collectors.toList());
		logger.info("transaction list based on customer id "+transactionList);
		return transactionList;
	}

	/**
	 * Fetch all transactions
	 */
	@Transactional(label = "rewardTransactionsManager", readOnly = true)
	public List<Transaction> getAllTransactions() {
		List<CustomerTransaction> customerTransactions = transactionRepository.findAll();
		if (CollectionUtils.isEmpty(customerTransactions))
			throw new ResourceNotFoundException("Resource not found ");
		List<Transaction> transactionList = customerTransactions.stream()
				.map(transactionMapper::mapTransactionEntityToTransaction).collect(Collectors.toList());
		logger.info("transaction list based on customer id "+transactionList);
		return transactionList;
	}

	/**
	 * Delete transaction by transaction id
	 */
	@Transactional("rewardTransactionsManager")
	public String deleteTransactionById(Long id) {
		transactionRepository.deleteById(id);
		return ApplicationConstants.TRANSACTION_DELETED_SUCCESS;
	}

}

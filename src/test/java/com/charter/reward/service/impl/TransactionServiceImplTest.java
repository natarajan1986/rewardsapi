package com.charter.reward.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.charter.reward.db.dto.Transaction;
import com.charter.reward.db.entity.CustomerEntity;
import com.charter.reward.db.entity.CustomerTransaction;
import com.charter.reward.db.repository.CustomerRepository;
import com.charter.reward.db.repository.TransactionRepository;
import com.charter.reward.exception.ResourceNotFoundException;
import com.charter.reward.mapper.TransactionMapper;

@ExtendWith(SpringExtension.class)
public class TransactionServiceImplTest {

	@Mock
	private TransactionMapper transactionMapper;

	@Mock
	public CustomerRepository customerRepository;

	@Mock
	public TransactionRepository transactionRepository;

	@InjectMocks
	TransactionServiceImpl transactionServiceImpl;

	@Test
	void createTransaction_thenReturnObject() {

		var transaction = getTransaction();
		var customerEntity = getCustomerEntity();

		when(customerRepository.findById(1L)).thenReturn(customerEntity);
		when(transactionRepository.save(getCustomerTransaction())).thenReturn(getCustomerTransaction());
		when(transactionMapper.mapTransactionEntityToTransaction(getCustomerTransaction()))
				.thenReturn(getTransaction());
		var obj = transactionServiceImpl.createTransaction(transaction);
		assertThat(obj).isNull();

	}

	@Test
	void updateTransaction_thenReturnObject() {

		var transaction = getTransaction();
		var customerEntity = getCustomerEntity();

		when(customerRepository.findById(1L)).thenReturn(customerEntity);
		when(transactionRepository.findById(1L)).thenReturn(getCustomerTransactionOptional());
		when(transactionRepository.save(getCustomerTransaction())).thenReturn(getCustomerTransaction());
		when(transactionMapper.mapTransactionEntityToTransaction(getCustomerTransaction()))
				.thenReturn(getTransaction());
		var obj = transactionServiceImpl.updateTransaction(transaction);
		assertThat(obj).isNull();

	}

	@Test
	void getTransactionById__thenThrowException() throws ResourceNotFoundException {
		Optional<CustomerTransaction> tx = Optional.empty();
		when(transactionRepository.findById(1L)).thenReturn(tx);
		assertThrows(ResourceNotFoundException.class, () -> {
			transactionServiceImpl.getTransactionById(1L);
		});

	}

	@Test
	void getTransactionsByCustomerId__thenThrowException() throws ResourceNotFoundException {
		Optional<CustomerTransaction> tx = Optional.empty();
		when(transactionRepository.findTransactionsByCustomerId(1L)).thenReturn(null);
		assertThrows(ResourceNotFoundException.class, () -> {
			transactionServiceImpl.getTransactionsByCustomerId(1L);
		});

	}

	@Test
	void getAllTransactions__thenThrowException() throws ResourceNotFoundException {
		when(transactionRepository.findAll()).thenReturn(null);
		assertThrows(ResourceNotFoundException.class, () -> {
			transactionServiceImpl.getAllTransactions();
		});

	}

	/**
	 * 
	 * @return
	 */
	private Optional<CustomerEntity> getCustomerEntity() {
		Optional<CustomerEntity> customerEntity = Optional.of(new CustomerEntity());
		customerEntity.get().setId(1L);
		customerEntity.get().setUsername("raja");
		customerEntity.get().setPassword("raja");
		customerEntity.get().setEmail("raja@gmail.com");
		customerEntity.get().setPhoneNumber("8056047407");
		return customerEntity;
	}

	/**
	 * 
	 * @return
	 */
	private CustomerTransaction getCustomerTransaction() {
		CustomerTransaction tx = new CustomerTransaction();
		tx.setId(1L);
		tx.setAmount(BigDecimal.valueOf(60.00));
		tx.setRewardPoints(10);
		tx.setTransactionDate(LocalDateTime.now());
		tx.setCustomerId(1L);
		return tx;
	}

	/**
	 * 
	 * @return
	 */
	private Optional<CustomerTransaction> getCustomerTransactionOptional() {
		Optional<CustomerTransaction> tx = Optional.of(new CustomerTransaction());
		tx.get().setId(1L);
		tx.get().setAmount(BigDecimal.valueOf(60.00));
		tx.get().setRewardPoints(10);
		tx.get().setTransactionDate(LocalDateTime.now());
		tx.get().setCustomerId(1L);
		return tx;
	}

	/**
	 * 
	 * @return
	 */
	private Transaction getTransaction() {
		Transaction tx = new Transaction();
		tx.setId(1L);
		tx.setAmount(BigDecimal.valueOf(60.00));
		tx.setRewardPoints(10);
		tx.setTransactionDate(LocalDateTime.now());
		tx.setCustomerId(1L);
		return tx;
	}
}

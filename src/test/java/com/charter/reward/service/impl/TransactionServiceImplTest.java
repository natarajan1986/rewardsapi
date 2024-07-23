package com.charter.reward.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
import com.charter.reward.exception.BadRequestException;
import com.charter.reward.exception.ResourceNotFoundException;
import com.charter.reward.mapper.TransactionMapper;
import com.charter.reward.util.RewardUtil;

@ExtendWith(SpringExtension.class)
public class TransactionServiceImplTest {

	@Mock
	public RewardUtil rewardUtil;

	@Mock
	public TransactionMapper transactionMapper;

	@Mock
	public CustomerRepository customerRepository;

	@Mock
	public TransactionRepository transactionRepository;

	@InjectMocks
	TransactionServiceImpl testTransactionServiceImpl;

	@Test
	void createTransaction_thenReturnObject() {

		var transaction = getTransaction();
		var customerEntity = getCustomerEntity();

		when(customerRepository.findById(1L)).thenReturn(customerEntity);
		when(transactionRepository.save(getCustomerTransaction())).thenReturn(getCustomerTransaction());
		when(transactionMapper.mapTransactionEntityToTransaction(getCustomerTransaction()))
				.thenReturn(getTransaction());
		var result = testTransactionServiceImpl.createTransaction(transaction);
		assertThat(result).isNull();
//		verify(transactionRepository);
	}

	@Test
	void createTransaction_validationError_thenReturnObject() {

		var transaction = getTransaction();
		var customerEntity = getCustomerEntity();

		transaction.setCustomerId(null);
		when(customerRepository.findById(1L)).thenReturn(customerEntity);
		when(transactionRepository.save(getCustomerTransaction())).thenReturn(getCustomerTransaction());
		when(transactionMapper.mapTransactionEntityToTransaction(getCustomerTransaction()))
				.thenReturn(getTransaction());
		assertThrows(BadRequestException.class, () -> {
			testTransactionServiceImpl.createTransaction(transaction);
		});

	}

	@Test
	void createTransaction_validationError1_thenReturnObject() {

		var transaction = getTransaction();
		var customerEntity = getCustomerEntity();

		transaction.setAmount(null);
		when(customerRepository.findById(1L)).thenReturn(customerEntity);
		when(transactionRepository.save(getCustomerTransaction())).thenReturn(getCustomerTransaction());
		when(transactionMapper.mapTransactionEntityToTransaction(getCustomerTransaction()))
				.thenReturn(getTransaction());
		assertThrows(BadRequestException.class, () -> {
			testTransactionServiceImpl.createTransaction(transaction);
		});

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
		var obj = testTransactionServiceImpl.updateTransaction(transaction);
		assertThat(obj).isNull();
//		verify(transactionRepository);
	}

	@Test
	void getTransactionById__thenThrowException() throws ResourceNotFoundException {
		Optional<CustomerTransaction> tx = Optional.empty();
		when(transactionRepository.findById(1L)).thenReturn(tx);
		assertThrows(ResourceNotFoundException.class, () -> {
			testTransactionServiceImpl.getTransactionById(1L);
		});

	}

	@Test
	void getTransactionsByCustomerId__thenReturn() {
		when(transactionRepository.findTransactionsByCustomerId(1L)).thenReturn(getCustomerTransactions());
		testTransactionServiceImpl.getTransactionsByCustomerId(1L);
	}

	@Test
	void getTransactionsByCustomerId__thenThrowException() throws ResourceNotFoundException {
		when(transactionRepository.findTransactionsByCustomerId(1L)).thenReturn(null);
		assertThrows(ResourceNotFoundException.class, () -> {
			testTransactionServiceImpl.getTransactionsByCustomerId(1L);
		});

	}

	@Test
	void getAllTransactions__thenThrowException() throws ResourceNotFoundException {
		when(transactionRepository.findAll()).thenReturn(null);
		assertThrows(ResourceNotFoundException.class, () -> {
			testTransactionServiceImpl.getAllTransactions();
		});

	}

	@Test
	void getAllTransactions__thenReturns(){
		when(transactionRepository.findAll()).thenReturn(getCustomerTransactions());
		testTransactionServiceImpl.getAllTransactions();
	}

	@Test
	void deleteTransactions__thenReturns() {
		testTransactionServiceImpl.deleteTransactionById(1L);
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
	private List<CustomerTransaction> getCustomerTransactions() {
		List<CustomerTransaction> customerTransactions = new ArrayList<>();
		CustomerTransaction tx = new CustomerTransaction();
		tx.setId(1L);
		tx.setAmount(BigDecimal.valueOf(60.00));
		tx.setRewardPoints(10);
		tx.setTransactionDate(LocalDateTime.now());
		tx.setCustomerId(1L);
		customerTransactions.add(tx);
		return customerTransactions;
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

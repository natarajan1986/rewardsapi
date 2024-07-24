package com.charter.reward.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
	TransactionServiceImpl underTest;

	@Test
	void testCreateTransactionShouldReturnObject() {

		var transaction = getTransaction();
		var customerEntity = getCustomerEntity();

		when(customerRepository.findById(1L)).thenReturn(customerEntity);
		when(transactionRepository.save(getCustomerTransaction())).thenReturn(getCustomerTransaction());
		when(transactionMapper.mapTransactionEntityToTransaction(getCustomerTransaction()))
				.thenReturn(getTransaction());
		when(underTest.createTransaction(getTransaction())).thenReturn(getTransaction());

		var result = underTest.createTransaction(transaction);

		assertThat(result).isNotNull();
		assertEquals(result.getComments(), "fees");
	}

	@Test
	void testCreateTransactionValidationErrorShouldReturnObject() {
		var transaction = getTransaction();
		var customerEntity = getCustomerEntity();
		transaction.setCustomerId(null);

		when(customerRepository.findById(1L)).thenReturn(customerEntity);
		when(transactionRepository.save(getCustomerTransaction())).thenReturn(getCustomerTransaction());
		when(transactionMapper.mapTransactionEntityToTransaction(getCustomerTransaction()))
				.thenReturn(getTransaction());

		assertThrows(BadRequestException.class, () -> {
			underTest.createTransaction(transaction);
		});

	}

	@Test
	void testCreateTransactionValidationError1ShouldReturnObject() {
		var transaction = getTransaction();
		var customerEntity = getCustomerEntity();
		transaction.setAmount(null);

		when(customerRepository.findById(1L)).thenReturn(customerEntity);
		when(transactionRepository.save(getCustomerTransaction())).thenReturn(getCustomerTransaction());
		when(transactionMapper.mapTransactionEntityToTransaction(getCustomerTransaction()))
				.thenReturn(getTransaction());

		assertThrows(BadRequestException.class, () -> {
			underTest.createTransaction(transaction);
		});

	}

	@Test
	void testUpdateTransactionShouldReturnObject() {
		var transaction = getTransaction();
		var customerEntity = getCustomerEntity();

		when(customerRepository.findById(1L)).thenReturn(customerEntity);
		when(transactionRepository.findById(1L)).thenReturn(getCustomerTransactionOptional());
		when(transactionRepository.save(getCustomerTransaction())).thenReturn(getCustomerTransaction());
		when(transactionMapper.mapTransactionEntityToTransaction(getCustomerTransaction()))
				.thenReturn(getTransaction());
		when(underTest.updateTransaction(getTransaction())).thenReturn(getTransaction());

		var result = underTest.updateTransaction(transaction);

		assertThat(result).isNotNull();
		assertEquals(result.getComments(), "fees");
	}

	@Test
	void testGetTransactionByIdShouldThrowException() throws ResourceNotFoundException {
		Optional<CustomerTransaction> tx = Optional.empty();
		when(transactionRepository.findById(1L)).thenReturn(tx);

		assertThrows(ResourceNotFoundException.class, () -> {
			underTest.getTransactionById(1L);
		});
	}

	@Test
	void testGetTransactionsByCustomerIdShouldReturn() {
		var customerTransactions = getCustomerTransactions();
		var transaction = getTransactions();
		when(transactionRepository.findTransactionsByCustomerId(1L)).thenReturn(customerTransactions);
		when(transactionMapper.mapTransactionEntityToTransaction(customerTransactions.get(0)))
				.thenReturn(transaction.get(0));
		var result = underTest.getTransactionsByCustomerId(1L);

		assertEquals(result.size(), 1);
		assertEquals(result, transaction);
	}

	@Test
	void testGetTransactionsByCustomerIdShouldThrowException() throws ResourceNotFoundException {
		when(transactionRepository.findTransactionsByCustomerId(1L)).thenReturn(null);
		
		assertThrows(ResourceNotFoundException.class, () -> {
			underTest.getTransactionsByCustomerId(1L);
		});

	}

	@Test
	void testGetAllTransactionsShouldThrowException() throws ResourceNotFoundException {
		when(transactionRepository.findAll()).thenReturn(null);
		
		assertThrows(ResourceNotFoundException.class, () -> {
			underTest.getAllTransactions();
		});

	}

	@Test
	void testGetAllTransactionsShouldReturns() {
		var customerTransactions = getCustomerTransactions();
		var transaction = getTransactions();

		when(transactionRepository.findAll()).thenReturn(customerTransactions);
		when(transactionMapper.mapTransactionEntityToTransaction(customerTransactions.get(0)))
				.thenReturn(transaction.get(0));

		var result = underTest.getAllTransactions();

		assertEquals(result.size(), 1);
		assertEquals(result, transaction);
	}

	@Test
	void testDeleteTransactionsShouldDelete() {
		underTest.deleteTransactionById(1L);
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
	private List<Transaction> getTransactions() {
		List<Transaction> customerTransactions = new ArrayList<>();
		Transaction tx = new Transaction();
		tx.setId(1L);
		tx.setAmount(BigDecimal.valueOf(60.00));
		tx.setRewardPoints(10);
		tx.setTransactionDate(LocalDateTime.now());
		tx.setCustomerId(1L);
		tx.setComments("fees");
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
		tx.setComments("fees");
		tx.setCustomer(getCustomerEntity().get());
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
		tx.setComments("fees");
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

}

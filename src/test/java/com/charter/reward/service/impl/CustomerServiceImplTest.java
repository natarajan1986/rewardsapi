package com.charter.reward.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.charter.reward.db.dto.Customer;
import com.charter.reward.db.entity.CustomerEntity;
import com.charter.reward.db.repository.CustomerRepository;
import com.charter.reward.exception.BadRequestException;
import com.charter.reward.mapper.CustomerMapper;

@ExtendWith(SpringExtension.class)
public class CustomerServiceImplTest {

	@InjectMocks
	CustomerServiceImpl underTest;

	@Mock
	CustomerMapper customerMapper;

	@Mock
	public CustomerRepository customerRepository;

	@Test
	void testSaveCustomerWhenSavingCustomerShouldReturnObject() {
		var customer = getCustomer();
		var customerEntity = getCustomerEntity();

		when(customerMapper.mapCustomerToCustomerEntity(customer)).thenReturn(customerEntity);
		when(underTest.createCustomerAccount(customer)).thenReturn(customer);

		var result = underTest.createCustomerAccount(getCustomer());

		assertThat(result).isNotNull();
		assertEquals(result, customer);
	}

	@Test
	void testSaveCustomerWhenSavingCustomerShouldThrowException() throws BadRequestException {
		var customer = getCustomer();
		var customerEntity = getCustomerEntity();
		
		customer.setUsername(null);
		
		when(customerMapper.mapCustomerToCustomerEntity(customer)).thenReturn(customerEntity);
		assertThrows(BadRequestException.class, () -> {
			underTest.createCustomerAccount(customer);
		});
	}

	@Test
	void testSaveCustomerWhenSavingCustomerPasswordNullShouldThrowException() throws BadRequestException {
		var customer = getCustomer();
		var customerEntity = getCustomerEntity();
		customer.setPassword(null);
		
		when(customerMapper.mapCustomerToCustomerEntity(customer)).thenReturn(customerEntity);
		
		assertThrows(BadRequestException.class, () -> {
			underTest.createCustomerAccount(customer);
		});
	}

	/**
	 * 
	 * @return Customer
	 */
	private Customer getCustomer() {
		Customer customer = new Customer();
		customer.setId(1L);
		customer.setUsername("raja");
		customer.setPassword("raja");
		customer.setEmail("raja@gmail.com");
		customer.setPhoneNumber("8056047407");
		return customer;
	}

	/**
	 * 
	 * @return CustomerEntity
	 */
	private CustomerEntity getCustomerEntity() {
		CustomerEntity customerEntity = new CustomerEntity();
		customerEntity.setId(1L);
		customerEntity.setUsername("raja");
		customerEntity.setPassword("raja");
		customerEntity.setEmail("raja@gmail.com");
		customerEntity.setPhoneNumber("8056047407");
		return customerEntity;
	}

}

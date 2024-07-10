package com.charter.reward.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.charter.reward.db.dto.Customer;
import com.charter.reward.db.entity.CustomerEntity;
import com.charter.reward.db.repository.CustomerRepository;
import com.charter.reward.exception.BadRequestException;
import com.charter.reward.mapper.CustomerMapper;
import com.charter.reward.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {

	public CustomerServiceImpl(CustomerMapper customerMapper) {
		this.customerMapper = customerMapper;
	}

	private CustomerMapper customerMapper;

	@Autowired
	public CustomerRepository customerRepository;

	/**
	 * 
	 */
	@Transactional("rewardTransactionsManager")
	public Customer createCustomerAccount(Customer customer) {
		validateCustomer(customer);
		CustomerEntity cus = customerMapper.mapCustomerToCustomerEntity(customer);
		CustomerEntity sCustomerEntity = customerRepository.save(cus);
		return customerMapper.mapCustomerEntityToCustomer(sCustomerEntity);
	}

	/**
	 * 
	 * @param customer
	 */
	private void validateCustomer(Customer customer) {
		if (customer.getUsername() == null || customer.getUsername().isEmpty()) {
			throw new BadRequestException("User Name must not be empty.");
		} else if (customer.getPassword() == null || customer.getPassword().isEmpty()) {
			throw new BadRequestException("Password must not be empty.");
		}

	}

}

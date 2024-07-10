package com.charter.reward.mapper;

import org.springframework.stereotype.Component;

import com.charter.reward.db.dto.Customer;
import com.charter.reward.db.entity.CustomerEntity;
import com.charter.reward.util.RewardUtil;

@Component
public class CustomerMapper {

	/**
	 * This method is used to convert the Customer Details event input to
	 * CustomerEntity
	 * 
	 * @param customerEntity
	 * @return
	 */
	public Customer mapCustomerEntityToCustomer(CustomerEntity customerEntity) {
		Customer customer = new Customer();
		customer.setUsername(customerEntity.getUsername());
		customer.setPassword(customerEntity.getPassword());
		customer.setEmail(customerEntity.getEmail());
		customer.setPhoneNumber(customerEntity.getPhoneNumber());
		return customer;
	}

	public CustomerEntity mapCustomerToCustomerEntity(Customer customer) {
		CustomerEntity customerEntity = new CustomerEntity();
		customerEntity.setUsername(customer.getUsername());
		customerEntity.setPassword(customer.getPassword());
		customerEntity.setEmail(customer.getEmail());
		customerEntity.setPhoneNumber(customer.getPhoneNumber());
		customerEntity.setAccountNumber(RewardUtil.generateRandomAccountNumber(10));
		return customerEntity;
	}
}

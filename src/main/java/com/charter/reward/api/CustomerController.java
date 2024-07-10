package com.charter.reward.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.charter.reward.db.dto.Customer;
import com.charter.reward.service.CustomerService;

@RestController
@RequestMapping("/v1/customers")
public class CustomerController {

	 private static final Logger logger = LogManager.getLogger(CustomerController.class);
	
	@Autowired
	public CustomerService customerService;

	@PostMapping("/register")
	public ResponseEntity<Customer> createCustomerAccount(@RequestBody Customer customer) {
		logger.info("Fetches All Reward Points Summary"+customer);
		return new ResponseEntity<>(customerService.createCustomerAccount(customer), HttpStatus.OK);
	}

}

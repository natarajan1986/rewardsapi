package com.charter.reward.api;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.charter.reward.db.dto.Transaction;
import com.charter.reward.service.TransactionService;

import jakarta.validation.Valid;

/**
 * 
 */
@RequestMapping("/v1/transactions")
@RestController
public class CustomerTransactionController {
	
	 private static final Logger logger = LogManager.getLogger(CustomerTransactionController.class);

	@Autowired
	public TransactionService transactionService;

	/**
	 * Method to make transaction entry for a existing customer
	 * 
	 * @param transaction
	 * @return
	 */
	@PostMapping
	public ResponseEntity<Transaction> createTransaction(@Valid @RequestBody Transaction transaction) {
		logger.info("Create transaction entry for a existing customer");
		return new ResponseEntity<>(transactionService.createTransaction(transaction), HttpStatus.OK);

	}

	/**
	 * Method to update transaction entry for a existing customer
	 * 
	 * @param transaction
	 * @return
	 */
	@PutMapping
	public ResponseEntity<Transaction> updateTransaction(@Valid @RequestBody Transaction transaction) {
		logger.info("update transaction entry for a existing customer");
		return new ResponseEntity<>(transactionService.updateTransaction(transaction), HttpStatus.OK);

	}

	/**
	 * Method to fetch transaction based on transaction id
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/{id}")
	public ResponseEntity<Transaction> getTransactionById(@PathVariable Long id) {
		return new ResponseEntity<>(transactionService.getTransactionById(id), HttpStatus.OK);
	}

	/**
	 * Method to fetch transactions based on customer id
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("transaction-list/{customerId}")
	public ResponseEntity<List<Transaction>> getTransactionsByCustomerId(@PathVariable Long customerId) {
		return new ResponseEntity<List<Transaction>>(transactionService.getTransactionsByCustomerId(customerId),
				HttpStatus.OK);
	}

	@GetMapping()
	public ResponseEntity<List<Transaction>> getAllTransactions() {
		return new ResponseEntity<List<Transaction>>(transactionService.getAllTransactions(),
				HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteTransactionById(@PathVariable Long id) {
		return new ResponseEntity<>(transactionService.deleteTransactionById(id), HttpStatus.OK);
	}
	
}

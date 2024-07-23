package com.charter.reward.api;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.charter.reward.db.dto.CustomerRewardsDTO;
import com.charter.reward.db.dto.RewardsSummaryDTO;
import com.charter.reward.service.RewardService;

@RequestMapping("/v1/reward-points")
@RestController
public class RewardsController {

	private static final Logger logger = LogManager.getLogger(RewardsController.class);

	@Autowired
	public RewardService rewardService;

	/**
	 * fetches reward point accumulated on a monthly basis for all customers
	 * 
	 * @return
	 */
	@GetMapping
	public ResponseEntity<List<CustomerRewardsDTO>> getAllRewardPointsSummary() {
		logger.info("Fetches All Reward Points Summary");
		return new ResponseEntity<List<CustomerRewardsDTO>>(rewardService.getAllRewardPoints(), HttpStatus.OK);
	}

	/**
	 * fetches reward points accumulated on a monthly basis for given customer
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/{id}")
	public ResponseEntity<List<CustomerRewardsDTO>> getAllRewardPointsByCustomerId(@PathVariable Long id) {
		logger.info("Fetches All Reward Points Summary based on customer id" +id);
		return new ResponseEntity<List<CustomerRewardsDTO>>(rewardService.getAllRewardPointsByCustomer(id),
				HttpStatus.OK);
	}

	/**
	 * Update Customer rewards table with customer monthy reward points summary
	 * 
	 * @param transaction
	 * @return
	 */
	@PutMapping("/customer-rewards-summary")
	public ResponseEntity<List<RewardsSummaryDTO>> fetchCustomerRewardsSummary() {
		logger.info("Generate monthly reward point summary for customers");
		return new ResponseEntity<List<RewardsSummaryDTO>>(rewardService.updateCustomerRewardsSummary(), HttpStatus.OK);

	}

	
	
	
	
}

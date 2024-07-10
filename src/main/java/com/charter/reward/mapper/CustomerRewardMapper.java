package com.charter.reward.mapper;

import org.springframework.stereotype.Component;

import com.charter.reward.db.dto.CustomerRewardsDTO;
import com.charter.reward.db.entity.CustomerRewards;

@Component
public class CustomerRewardMapper {

	/**
	 * This method is used to convert the reward Details to rewardEntity
	 * 
	 * @param customerRewards
	 * @return
	 */
	public CustomerRewardsDTO mapCustomerRewardsToCustomerRewardsEntity(CustomerRewards customerRewards) {
		CustomerRewardsDTO customerRewardsDTO = new CustomerRewardsDTO();
		customerRewardsDTO.setId(customerRewards.getId());
		customerRewardsDTO.setCustomerId(customerRewards.getCustomerId());
		if (customerRewards.getCustomer() != null)
			customerRewardsDTO.setUserName(customerRewards.getCustomer().getUsername());
		customerRewardsDTO.setMonth(customerRewards.getMonth());
		customerRewardsDTO.setRewardPoints(customerRewards.getRewardPoints());
		customerRewardsDTO.setYear(customerRewards.getYear());
		return customerRewardsDTO;
	}

}

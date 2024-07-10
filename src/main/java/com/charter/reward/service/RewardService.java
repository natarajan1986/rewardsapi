package com.charter.reward.service;

import java.util.List;

import com.charter.reward.db.dto.CustomerRewardsDTO;
import com.charter.reward.db.dto.RewardsSummaryDTO;

public interface RewardService {
	public List<CustomerRewardsDTO> getAllRewardPoints();

	public List<CustomerRewardsDTO> getAllRewardPointsByCustomer(Long id);
	
	public List<RewardsSummaryDTO> updateCustomerRewardsSummary();
}

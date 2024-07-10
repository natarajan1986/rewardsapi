package com.charter.reward.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.charter.reward.db.dto.CustomerRewardsDTO;
import com.charter.reward.db.dto.RewardsSummaryDTO;
import com.charter.reward.db.entity.CustomerRewards;
import com.charter.reward.db.repository.CustomerRepository;
import com.charter.reward.db.repository.CustomerRewardRepository;
import com.charter.reward.db.repository.TransactionRepository;
import com.charter.reward.exception.ResourceNotFoundException;
import com.charter.reward.mapper.CustomerRewardMapper;

@ExtendWith(SpringExtension.class)
public class RewardServiceImplTest {

	@Mock
	private CustomerRewardMapper rewardMapper;

	@InjectMocks
	RewardServiceImpl rewardServiceImpl;

	@Mock
	public TransactionRepository transactionRepository;

	@Mock
	public CustomerRewardRepository rewardRepository;

	@Mock
	public CustomerRepository customerRepository;

	@Test
	void getAllRewardPoints_thenReturnObject() {

		var customerRewards = getAllCustomerRewards();
		when(rewardRepository.findAll()).thenReturn(customerRewards);
		var obj = rewardServiceImpl.getAllRewardPoints();
		assertThat(obj).isNotNull();

	}

	@Test
	void getAllRewardPointsByCustomer_thenReturnObject() {
		var customerRewards = getAllCustomerRewards();
		when(rewardRepository.findRewardPointsByCustomerId(1L)).thenReturn(customerRewards);
		var obj = rewardServiceImpl.getAllRewardPointsByCustomer(1L);
		assertThat(obj).isNotNull();

	}

	@Test
	void updateCustomerRewardsSummary_whenUpdateCustomerRewards_thenThrowException() throws ResourceNotFoundException {
		when(transactionRepository.findCustomerRewardSummary()).thenReturn(null);
		assertThrows(ResourceNotFoundException.class, () -> {
			rewardServiceImpl.updateCustomerRewardsSummary();
		});
	}

	/**
	 * 
	 * @return List<CustomerRewards>
	 */
	private List<CustomerRewards> getAllCustomerRewards() {
		List<CustomerRewards> rewards = new ArrayList<>();
		CustomerRewards customerRewards = new CustomerRewards();
		customerRewards.setId(1L);
		customerRewards.setMonth("July");
		customerRewards.setRewardPoints(10);
		customerRewards.setYear("2024");
		customerRewards.setCustomerId(1L);
		rewards.add(customerRewards);
		return rewards;
	}

	/**
	 * 
	 * @return List<CustomerRewardsDTO>
	 */
	private List<CustomerRewardsDTO> getAllRewardPointsDTOs() {
		List<CustomerRewardsDTO> customerRewardsDTOs = new ArrayList<>();
		CustomerRewardsDTO customerRewardsDTO = new CustomerRewardsDTO();
		customerRewardsDTO.setId(1L);
		customerRewardsDTO.setMonth("July");
		customerRewardsDTO.setRewardPoints(10);
		customerRewardsDTO.setYear("2024");
		customerRewardsDTO.setCustomerId(1L);
		customerRewardsDTOs.add(customerRewardsDTO);
		return customerRewardsDTOs;
	}

	/**
	 * 
	 * @return List<RewardsSummaryDTO>
	 */
	private List<RewardsSummaryDTO> getAllRewardSummaryPointsDTOs() {
		List<RewardsSummaryDTO> rewardsSummaryDTOs = new ArrayList<>();
		RewardsSummaryDTO rewardsSummaryDTO = new RewardsSummaryDTO();

		rewardsSummaryDTO.setMonth("July");
		rewardsSummaryDTO.setRewardPoints((long) 10);
		rewardsSummaryDTO.setYear(BigDecimal.valueOf(2024));
		rewardsSummaryDTO.setCustomerId(1L);
		rewardsSummaryDTOs.add(rewardsSummaryDTO);
		return rewardsSummaryDTOs;
	}
}

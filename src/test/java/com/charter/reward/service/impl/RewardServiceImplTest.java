package com.charter.reward.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.charter.reward.db.dto.CustomerRewardsDTO;
import com.charter.reward.db.dto.RewardsSummaryDTO;
import com.charter.reward.db.entity.CustomerEntity;
import com.charter.reward.db.entity.CustomerRewards;
import com.charter.reward.db.repository.CustomerRepository;
import com.charter.reward.db.repository.CustomerRewardRepository;
import com.charter.reward.db.repository.TransactionRepository;
import com.charter.reward.exception.ResourceNotFoundException;
import com.charter.reward.mapper.CustomerRewardMapper;

import jakarta.persistence.Tuple;

@ExtendWith(SpringExtension.class)
public class RewardServiceImplTest {

	@Mock
	private CustomerRewardMapper rewardMapper;

	@InjectMocks
	public RewardServiceImpl underTest;

	@Mock
	public TransactionRepository transactionRepository;

	@Mock
	public CustomerRewardRepository rewardRepository;

	@Mock
	public CustomerRepository customerRepository;

	@Test
	void testGetAllRewardPointsShouldReturnObject() {
		var customerRewards = getAllCustomerRewards();
		var customerRewardPointsDTOs = getAllRewardPointsDTOs();

		when(rewardRepository.findAll()).thenReturn(customerRewards);
		when(rewardMapper.mapCustomerRewardsToCustomerRewardsEntity(customerRewards.get(0)))
				.thenReturn(customerRewardPointsDTOs.get(0));

		var result = underTest.getAllRewardPoints();

		assertThat(result).isNotNull();
		assertEquals(result.size(), 1);
		assertEquals(result, customerRewardPointsDTOs);
		verify(rewardRepository, atLeastOnce()).findAll();

	}

	@Test
	void testGetAllRewardPointsShouldThrowException() {
		when(rewardRepository.findAll()).thenReturn(null);
		
		assertThrows(ResourceNotFoundException.class, () -> {
			underTest.getAllRewardPoints();
		});

	}

	@Test
	void testGetAllRewardPointsByCustomer_thenReturnException() {
		when(rewardRepository.findRewardPointsByCustomerId(1L)).thenReturn(null);
		
		assertThrows(ResourceNotFoundException.class, () -> {
			underTest.getAllRewardPointsByCustomer(1L);
		});

	}

	@Test
	void testGetAllRewardPointsByCustomer_thenReturnObject() {
		var customerRewards = getAllCustomerRewards();
		when(rewardRepository.findRewardPointsByCustomerId(1L)).thenReturn(customerRewards);

		var result = underTest.getAllRewardPointsByCustomer(1L);

		assertThat(result).isNotNull();
		assertEquals(result.size(), 1);
		verify(rewardRepository);
	}

	@Test
	void testUpdateCustomerRewardsSummary_whenUpdateCustomerRewards_thenThrowException() throws ResourceNotFoundException {
		when(transactionRepository.findCustomerRewardSummary()).thenReturn(null);
		assertThrows(ResourceNotFoundException.class, () -> {
			underTest.updateCustomerRewardsSummary();
		});
	}

	@Test
	void testSaveCustomerRewardsSummary_whenSaveCustomerRewards_thenThrowException() throws ResourceNotFoundException {
		when(customerRepository.findById(1L)).thenReturn(Optional.of( getCustomerEntity()));
		when(transactionRepository.findCustomerRewardSummary()).thenReturn(null);
		assertThrows(ResourceNotFoundException.class, () -> {
			underTest.updateCustomerRewardsSummary();
		});
	}

	@Test
	void testSaveCustomerRewardsSummary_whenSaveCustomerRewards_thenReturns() throws ResourceNotFoundException {
		when(customerRepository.findById(1L)).thenReturn(Optional.of( getCustomerEntity()));
		when(transactionRepository.findCustomerRewardSummary()).thenReturn(getCustomerEntityTuples());
		assertThrows(ResourceNotFoundException.class, () -> {
			underTest.updateCustomerRewardsSummary();
		});
	}

	/**
	 * 
	 * @return
	 */
	private List<Tuple> getCustomerEntityTuples() {
		Tuple mockedTuple = Mockito.mock(Tuple.class);

		List<Tuple> accountObj = new ArrayList<>();

		accountObj.add(mockedTuple);
		return accountObj;

	}

	/**
	 * 
	 * @return
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
	@SuppressWarnings("unused")
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

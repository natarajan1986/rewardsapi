package com.charter.reward.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.charter.reward.db.dto.CustomerRewardsDTO;
import com.charter.reward.db.dto.RewardsSummaryDTO;
import com.charter.reward.db.entity.CustomerEntity;
import com.charter.reward.db.entity.CustomerRewards;
import com.charter.reward.db.repository.CustomerRepository;
import com.charter.reward.db.repository.CustomerRewardRepository;
import com.charter.reward.db.repository.TransactionRepository;
import com.charter.reward.exception.ResourceNotFoundException;
import com.charter.reward.mapper.CustomerRewardMapper;
import com.charter.reward.service.RewardService;

import jakarta.persistence.Tuple;

@Service
public class RewardServiceImpl implements RewardService {

	public RewardServiceImpl(CustomerRewardMapper rewardMapper, TransactionRepository transactionRepository,
			CustomerRewardRepository rewardRepository, CustomerRepository customerRepository) {
		this.rewardMapper = rewardMapper;
		this.transactionRepository = transactionRepository;
		this.rewardRepository = rewardRepository;
		this.customerRepository = customerRepository;
	}

	private static final Logger logger = LogManager.getLogger(RewardServiceImpl.class);
	
	private CustomerRewardMapper rewardMapper;

	@Autowired
	public TransactionRepository transactionRepository;

	@Autowired
	public CustomerRewardRepository rewardRepository;

	@Autowired
	public CustomerRepository customerRepository;

	@Transactional(label = "rewardTransactionsManager", readOnly = true)
	public List<CustomerRewardsDTO> getAllRewardPoints() {
		List<CustomerRewards> customerRewards = rewardRepository.findAll();
		if (CollectionUtils.isEmpty(customerRewards))
			throw new ResourceNotFoundException("Resource not found ");
		List<CustomerRewardsDTO> customerRewardsDTOs = customerRewards.stream()
				.map(rewardMapper::mapCustomerRewardsToCustomerRewardsEntity).collect(Collectors.toList());

		return customerRewardsDTOs;

	}

	@Transactional(label = "rewardTransactionsManager", readOnly = true)
	public List<CustomerRewardsDTO> getAllRewardPointsByCustomer(Long id) {
		List<CustomerRewards> customerRewards = rewardRepository.findRewardPointsByCustomerId(id);
		if (CollectionUtils.isEmpty(customerRewards))
			throw new ResourceNotFoundException("Resource not found ");
		List<CustomerRewardsDTO> customerRewardsDTOs = customerRewards.stream()
				.map(rewardMapper::mapCustomerRewardsToCustomerRewardsEntity).collect(Collectors.toList());
		return customerRewardsDTOs;
	}

	/**
	 * Fetches customer reward points summary and update customer rewards table
	 */
	@Transactional("rewardTransactionsManager")
	public List<RewardsSummaryDTO> updateCustomerRewardsSummary() {
		List<Tuple> transactionList = transactionRepository.findCustomerRewardSummary();
		if (CollectionUtils.isEmpty(transactionList))
			throw new ResourceNotFoundException("Resource not found ");
		List<RewardsSummaryDTO> rewardSummaryDTOs = convertTupleToDTO(transactionList);
		rewardSummaryDTOs.forEach(r -> {
			CustomerRewards customerRewards = rewardRepository.findCustomerRewardPonts(r.getCustomerId(),
					r.getMonth().trim(), r.getYear().toString());
			if (customerRewards != null) {
				customerRewards.setRewardPoints(Math.toIntExact(r.getRewardPoints()));
				rewardRepository.save(customerRewards);
			} else {
				saveCustomerRewards(r);
			}

		});
		logger.info("Fetches All Reward Points Summary"+rewardSummaryDTOs);
		return rewardSummaryDTOs;
	}

	/**
	 * 
	 */
	private void saveCustomerRewards(RewardsSummaryDTO dto) {
		CustomerEntity customerEntity = new CustomerEntity();
		customerEntity = customerRepository.findById(dto.getCustomerId()).orElseThrow();
		CustomerRewards rewardsEntity = new CustomerRewards();

		rewardsEntity.setMonth(dto.getMonth().trim());
		rewardsEntity.setYear(dto.getYear().toString());
		rewardsEntity.setRewardPoints(Math.toIntExact(dto.getRewardPoints()));
		rewardsEntity.setCustomer(customerEntity);
		rewardRepository.save(rewardsEntity);
	}

	/**
	 * Converts Tuple list to list of DTOs
	 * 
	 * @param transactionList
	 * @return
	 */
	private List<RewardsSummaryDTO> convertTupleToDTO(List<Tuple> transactionList) {

		List<RewardsSummaryDTO> summaryDTOs = transactionList.stream()
				.map(t -> new RewardsSummaryDTO(t.get(0, Long.class), t.get(1, String.class),
						t.get(2, BigDecimal.class), t.get(3, Long.class)))
				.collect(Collectors.toList());

		return summaryDTOs;
	}

}

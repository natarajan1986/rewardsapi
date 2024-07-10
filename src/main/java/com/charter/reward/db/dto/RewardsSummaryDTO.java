package com.charter.reward.db.dto;

import java.math.BigDecimal;

public class RewardsSummaryDTO {
	
	
	public RewardsSummaryDTO(Long customerId, String month, BigDecimal year, Long rewardPoints) {
		super();
		this.customerId = customerId;
		this.month = month;
		this.year = year;
		this.rewardPoints = rewardPoints;
	}

	public RewardsSummaryDTO() {
		
	}

	private Long customerId;
	
	private String month;

	private BigDecimal year;

	private Long rewardPoints;

	/**
	 * @return the customerId
	 */
	public Long getCustomerId() {
		return customerId;
	}

	/**
	 * @param customerId the customerId to set
	 */
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	/**
	 * @return the month
	 */
	public String getMonth() {
		return month;
	}

	/**
	 * @param month the month to set
	 */
	public void setMonth(String month) {
		this.month = month;
	}

	/**
	 * @return the year
	 */
	public BigDecimal getYear() {
		return year;
	}

	/**
	 * @param year the year to set
	 */
	public void setYear(BigDecimal year) {
		this.year = year;
	}

	/**
	 * @return the rewardPoints
	 */
	public Long getRewardPoints() {
		return rewardPoints;
	}

	/**
	 * @param rewardPoints the rewardPoints to set
	 */
	public void setRewardPoints(Long rewardPoints) {
		this.rewardPoints = rewardPoints;
	}



}

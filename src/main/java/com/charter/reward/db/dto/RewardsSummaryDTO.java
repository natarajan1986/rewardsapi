package com.charter.reward.db.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RewardsSummaryDTO {

	private Long customerId;

	private String month;

	private BigDecimal year;

	private Long rewardPoints;

}

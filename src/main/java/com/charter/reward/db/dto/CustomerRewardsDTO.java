package com.charter.reward.db.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRewardsDTO {

	private Long id;
	
	private String userName;

	private String month;

	private String year;

	private Integer rewardPoints;

	public Long customerId;

}

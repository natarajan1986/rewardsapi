package com.charter.reward.db.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
	private Long id;

	private String comments;

	@NotNull(message = "The amount is required.")
	private BigDecimal amount;

	private LocalDateTime transactionDate;

	@NotNull(message = "The Customer Id is required.")
	private Long customerId;

	private Integer rewardPoints;

}

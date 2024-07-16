package com.charter.reward.util;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Random;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Class for performing all utility functions
 */
@Component
public class RewardUtil {

	@Value("${reward.lowerlimit}")
	private Integer lowerLimit;

	@Value("${reward.upperlimit}")
	private Integer upperLimit;


	/**
	 * Get LocalDateTime
	 * 
	 * @return
	 */
	public static LocalDateTime getCurrentLocalDateTime() {
		LocalDateTime lt = LocalDateTime.now();
		return lt;
	}

	/**
	 * Calculate rewards points based on transaction amount
	 * 
	 * @param txAmount
	 * @return
	 */
	public Integer calculateRewardPoints(BigDecimal txAmount) {
		int transactionAmount = txAmount.intValue();

		Integer points = 0;
		if (transactionAmount > lowerLimit && transactionAmount <= upperLimit) {
			points = (transactionAmount - lowerLimit);
		} else if (transactionAmount > upperLimit) {
			points = 2 * (transactionAmount - upperLimit) + lowerLimit;
		}
		return points;
	}

	public String generateRandomAccountNumber(int length) {
		StringBuilder accountNumber = new StringBuilder();
	    String allowedChars = "0123456789";
		Random random = new Random();

		for (int i = 0; i < length; i++) {
			int randomIndex = random.nextInt(allowedChars.length());
			char randomChar = allowedChars.charAt(randomIndex);
			accountNumber.append(randomChar);
		}

		return accountNumber.toString();
	}

}

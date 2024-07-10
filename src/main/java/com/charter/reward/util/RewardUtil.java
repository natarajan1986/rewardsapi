package com.charter.reward.util;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Random;

/**
 * Class for performing all utility functions
 */
public class RewardUtil {

	/**
	 * Get LocalDateTime
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
	public static Integer calculateRewardPoints(BigDecimal txAmount) {
		int transactionAmount = txAmount.intValue();

		Integer points = 0;
		if (transactionAmount > 50 && transactionAmount <= 100) {
			points = (transactionAmount - 50);
		} else if (transactionAmount > 100) {
			points = 2 * (transactionAmount - 100) + 50;
		}
		return points;
	}

	
	public static String generateRandomAccountNumber(int length) {
        String allowedChars = "0123456789";
        StringBuilder accountNumber = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(allowedChars.length());
            char randomChar = allowedChars.charAt(randomIndex);
            accountNumber.append(randomChar);
        }

        return accountNumber.toString();
    }
	
}

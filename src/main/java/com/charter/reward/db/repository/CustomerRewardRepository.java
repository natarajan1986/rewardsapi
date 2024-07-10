package com.charter.reward.db.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.charter.reward.db.entity.CustomerRewards;

@Repository
public interface CustomerRewardRepository extends JpaRepository<CustomerRewards, Long> {

	/**
	 * 
	 * @param customerId
	 * @return
	 */
	@Query(value = "select * from {h-schema}customer_rewards where customer_id =?1 ", nativeQuery = true)
	public List<CustomerRewards> findRewardPointsByCustomerId(Long customerId);
	
	/**
	 * 
	 * @param customerId
	 * @param month
	 * @param year
	 * @return
	 */
	@Query(value = "select * from {h-schema}customer_rewards where customer_id =?1 and month =?2 and year =?3  ", nativeQuery = true)
	public CustomerRewards findCustomerRewardPonts(Long customerId,String month,String year);
}

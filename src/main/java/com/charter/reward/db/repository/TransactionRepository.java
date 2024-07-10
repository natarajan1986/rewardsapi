package com.charter.reward.db.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.charter.reward.db.entity.CustomerTransaction;

import jakarta.persistence.Tuple;

@Repository
public interface TransactionRepository extends JpaRepository<CustomerTransaction, Long> {

	/**
	 * 
	 * @param customerId
	 * @return
	 */
	@Query(value = "select * from {h-schema}customer_transaction where customer_id =?1 ", nativeQuery = true)
	public List<CustomerTransaction> findTransactionsByCustomerId(Long customerId);

	/**
	 * 
	 * @return
	 */
	@Query(value = "select ct.customer_id as customerId,to_char(ct.transaction_date,'Month') as month,extract(year from ct.transaction_date) as year,sum(ct.reward_points) as rewardPoints FROM {h-schema}customer_transaction ct\r\n"
			+ "	 group by customerId,month,year ", nativeQuery = true)
	public List<Tuple> findCustomerRewardSummary();

}

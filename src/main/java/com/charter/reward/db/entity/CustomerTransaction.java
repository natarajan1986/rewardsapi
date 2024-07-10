package com.charter.reward.db.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "customer_transaction")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerTransaction implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "comments")
	private String comments;

	@Column(name = "amount")
	private BigDecimal  amount;

	@Column(name = "transaction_date")
	private LocalDateTime transactionDate;
	
	@Column(name = "reward_points")
	private Integer  rewardPoints;
	
	@Column(name="customer_id", updatable=false, insertable=false)
	public Long customerId;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name=("customer_id"),nullable=false)
	private CustomerEntity customer;
	
}

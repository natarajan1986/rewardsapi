package com.charter.reward.db.entity;

import java.io.Serializable;

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
@Table(name = "customer_rewards")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRewards implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "month")
	private String month;

	@Column(name = "year")
	private String year;

	@Column(name = "reward_points")
	private Integer rewardPoints;

	@Column(name = "customer_id", updatable = false, insertable = false)
	public Long customerId;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = ("customer_id"), nullable = false)
	private CustomerEntity customer;

}

package com.charter.reward.db.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.charter.reward.db.entity.CustomerEntity;


@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {
	  Optional<CustomerEntity> findByUsername(String username);

	  Boolean existsByUsername(String username);

	  Boolean existsByEmail(String email);
}

package com.charter.reward.configuration;

import java.util.HashMap;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableJpaRepositories(basePackages = {"com.charter.reward.db.repository"}, entityManagerFactoryRef = "rewardEntityManager", transactionManagerRef = "rewardTransactionsManager")
public class PostgresqlRewardConfig {
	@Value("${reward.datasource.driverClassName}")
	private String driverclass;

	@Value("${reward.datasource.jdbcUrl}")
	private String url;

	@Value("${reward.datasource.username}")
	private String username;

	@Value("${reward.datasource.password}")
	private String password;
	
	public PostgresqlRewardConfig() {
		super();
	}

	@Bean(name = "rewardDataSource")
	public DataSource towDataSource() {
		
		final DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(driverclass);
		dataSource.setUrl(url);
		dataSource.setUsername(username);
		dataSource.setPassword(password);
		
		return dataSource;

	}
	@Bean(name = "rewardEntityManager")
	@Primary
	public LocalContainerEntityManagerFactoryBean rewardEntityManager(
			@Qualifier("rewardDataSource") DataSource rewardDataSource) {

		final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(rewardDataSource);

		em.setPackagesToScan(new String[] {"com.charter.reward.db.entity"});

		final HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		em.setJpaVendorAdapter(vendorAdapter);
		final HashMap<String, Object> properties = new HashMap<>();

		properties.put("hibernate.default_schema", "reward");
		properties.put("hibernate.hbm2ddl.auto", "update");
		properties.put("spring.jpa.show-sql", true);
		properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");

		em.setJpaPropertyMap(properties);

		return em;
	}
	
	@Bean(name = "rewardTransactionsManager")
	public PlatformTransactionManager towTransactionsManager(
			@Qualifier("rewardEntityManager") LocalContainerEntityManagerFactoryBean rewardEntityManager) {
		final JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(rewardEntityManager.getObject());
		
		return transactionManager;
	}
}

package com.charter.reward.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.charter.reward.db.dto.CustomerRewardsDTO;
import com.charter.reward.db.dto.RewardsSummaryDTO;
import com.charter.reward.service.RewardService;

@WebMvcTest(RewardsController.class)
@AutoConfigureMockMvc
public class RewardsControllerTest {
	@MockBean
	private RewardService rewardService;

	@Autowired
	MockMvc mockMvc;
	
	@Test
	public void getAllRewardPointsSummary_thenReturnRewardPointsDTOsWith200Status() throws Exception  {

		when(rewardService.getAllRewardPoints()).thenReturn( getAllRewardPointsDTOs());
		MockHttpServletRequestBuilder reqBuilder = MockMvcRequestBuilders.get("/v1/reward-points");
		ResultActions perform = mockMvc.perform(reqBuilder);
		MvcResult mvcResult = perform.andReturn();
		MockHttpServletResponse response = mvcResult.getResponse();
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	public void getAllRewardPointsSummaryByCustomerId_thenReturnRewardPointsDTOsWith200Status() throws Exception  {

		when(rewardService.getAllRewardPointsByCustomer(1L)).thenReturn( getAllRewardPointsDTOs());
		MockHttpServletRequestBuilder reqBuilder = MockMvcRequestBuilders.get("/v1/reward-points");
		ResultActions perform = mockMvc.perform(reqBuilder);
		MvcResult mvcResult = perform.andReturn();
		MockHttpServletResponse response = mvcResult.getResponse();
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
	}
	
	
	@Test
	public void updateCustomerRewardsSummary_thenReturnRewardPointsDTOsWith200Status() throws Exception  {

		when(rewardService.updateCustomerRewardsSummary()).thenReturn(getAllRewardSummaryPointsDTOs());
		MockHttpServletRequestBuilder reqBuilder = MockMvcRequestBuilders.get("/v1/transactions");
		ResultActions perform = mockMvc.perform(reqBuilder);
		MvcResult mvcResult = perform.andReturn();
		MockHttpServletResponse response = mvcResult.getResponse();
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
	}
	
	
	
	/**
	 * 
	 * @return
	 */
	private List<CustomerRewardsDTO> getAllRewardPointsDTOs() {
		List<CustomerRewardsDTO>  customerRewardsDTOs=new ArrayList<>();
		CustomerRewardsDTO customerRewardsDTO = new CustomerRewardsDTO();
		customerRewardsDTO.setId(1L);
		customerRewardsDTO.setMonth("July");
		customerRewardsDTO.setRewardPoints(10);
		customerRewardsDTO.setYear("2024");
		customerRewardsDTO.setCustomerId(1L);
		customerRewardsDTOs.add(customerRewardsDTO);
		return customerRewardsDTOs;
	}
	
	/**
	 * 
	 * @return
	 */
	private List<RewardsSummaryDTO> getAllRewardSummaryPointsDTOs() {
		List<RewardsSummaryDTO> rewardsSummaryDTOs = new ArrayList<>();
		RewardsSummaryDTO rewardsSummaryDTO = new RewardsSummaryDTO();

		rewardsSummaryDTO.setMonth("July");
		rewardsSummaryDTO.setRewardPoints((long) 10);
		rewardsSummaryDTO.setYear(BigDecimal.valueOf(2024));
		rewardsSummaryDTO.setCustomerId(1L);
		rewardsSummaryDTOs.add(rewardsSummaryDTO);
		return rewardsSummaryDTOs;
	}
	
}

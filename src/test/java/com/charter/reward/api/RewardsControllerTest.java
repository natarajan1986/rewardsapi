package com.charter.reward.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.charter.reward.db.dto.CustomerRewardsDTO;
import com.charter.reward.db.dto.RewardsSummaryDTO;
import com.charter.reward.service.RewardService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(value = RewardsController.class, excludeAutoConfiguration = { SecurityAutoConfiguration.class })
@AutoConfigureMockMvc
public class RewardsControllerTest {
	@MockBean
	private RewardService underTest;

	@Autowired
	MockMvc mockMvc;

	@Test
	public void testGetAllRewardPointsSummary_thenReturnRewardPointsDTOsWith200Status() throws Exception  {
		when(underTest.getAllRewardPoints()).thenReturn( getAllRewardPointsDTOs());
		MockHttpServletRequestBuilder reqBuilder = MockMvcRequestBuilders.get("/v1/reward-points");
		
		ResultActions perform = mockMvc.perform(reqBuilder);
		
		MvcResult mvcResult = perform.andReturn();
		MockHttpServletResponse response = mvcResult.getResponse();
		
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertEquals(response.getStatus(), HttpStatus.OK.value());
	}
	
	@Test
	public void testGetAllRewardPointsByCustomerId_thenReturnRewardPointsDTOsWith200Status() throws Exception  {

		when(underTest.getAllRewardPointsByCustomer(1L)).thenReturn( getAllRewardPointsDTOs());
		MockHttpServletRequestBuilder reqBuilder = MockMvcRequestBuilders.get("/v1/reward-points/1");
		
		ResultActions perform = mockMvc.perform(reqBuilder);
	
		MvcResult mvcResult = perform.andReturn();
		MockHttpServletResponse response = mvcResult.getResponse();
		
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertEquals(response.getStatus(), HttpStatus.OK.value());
	}
	

	@Test
	public void testGetAllRewardPointsSummaryByCustomerId_thenReturnRewardPointsDTOsWith200Status() throws Exception  {
		when(underTest.getAllRewardPointsByCustomer(1L)).thenReturn( getAllRewardPointsDTOs());
		MockHttpServletRequestBuilder reqBuilder = MockMvcRequestBuilders.get("/v1/reward-points");
		ResultActions perform = mockMvc.perform(reqBuilder);
		
		MvcResult mvcResult = perform.andReturn();
		
		MockHttpServletResponse response = mvcResult.getResponse();
		
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertEquals(response.getStatus(), HttpStatus.OK.value());
	}

	@Test
	public void testUpdateCustomerRewardsSummary_thenReturnRewardPointsDTOsWith200Status() throws Exception  {
		when(underTest.updateCustomerRewardsSummary()).thenReturn(getAllRewardSummaryPointsDTOs());
		String expectedJson = mapToJson(getAllRewardSummaryPointsDTOs());
		MockHttpServletRequestBuilder reqBuilder = MockMvcRequestBuilders.put("/v1/reward-points/customer-rewards-summary")
				.contentType(MediaType.APPLICATION_JSON).content(expectedJson);
		
		ResultActions perform = mockMvc.perform(reqBuilder);
		
		MvcResult mvcResult = perform.andReturn();
		MockHttpServletResponse response = mvcResult.getResponse();
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertEquals(response.getStatus(), HttpStatus.OK.value());
	}

	private String mapToJson(Object object) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(object);
	}

	/**
	 * 
	 * @return
	 */
	private List<CustomerRewardsDTO> getAllRewardPointsDTOs() {
		List<CustomerRewardsDTO> customerRewardsDTOs = new ArrayList<>();
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

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

import com.charter.reward.constants.ApplicationConstants;
import com.charter.reward.db.dto.Transaction;
import com.charter.reward.service.TransactionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(value = CustomerTransactionController.class, excludeAutoConfiguration = { SecurityAutoConfiguration.class })
@AutoConfigureMockMvc
public class CustomerTransactionControllerTest {

	@MockBean
	private TransactionService underTest;

	@Autowired
	MockMvc mockMvc;

	@Test
	public void testSaveTransaction_thenReturnCustomerWith200Status() throws Exception  {
		when(underTest.createTransaction( getTransaction())).thenReturn( getTransaction());
		String expectedJson = mapToJson(getTransaction());
		MockHttpServletRequestBuilder reqBuilder = MockMvcRequestBuilders.post("/v1/transactions")
				.contentType(MediaType.APPLICATION_JSON).content(expectedJson);
		
		ResultActions perform = mockMvc.perform(reqBuilder);
		
		MvcResult mvcResult = perform.andReturn();
		MockHttpServletResponse response = mvcResult.getResponse();
		
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertEquals(response.getStatus(), HttpStatus.OK.value());
	}

	@Test
	public void testUpdateTransaction_thenReturnTransactionWith200Status() throws Exception  {
		when(underTest.updateTransaction( getTransaction())).thenReturn( getTransaction());
		String expectedJson = mapToJson(getTransaction());
		MockHttpServletRequestBuilder reqBuilder = MockMvcRequestBuilders.put("/v1/transactions")
				.contentType(MediaType.APPLICATION_JSON).content(expectedJson);
		
		ResultActions perform = mockMvc.perform(reqBuilder);
		MvcResult mvcResult = perform.andReturn();
		MockHttpServletResponse response = mvcResult.getResponse();
		
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertEquals(response.getStatus(), HttpStatus.OK.value());
	}

	@Test
	public void testGetTransactionById_thenReturnTransactionWith200Status() throws Exception  {
		when(underTest.getTransactionById(1L)).thenReturn( getTransaction());
		MockHttpServletRequestBuilder reqBuilder = MockMvcRequestBuilders.get("/v1/transactions/1");
		
		ResultActions perform = mockMvc.perform(reqBuilder);
		MvcResult mvcResult = perform.andReturn();
		MockHttpServletResponse response = mvcResult.getResponse();
	
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertEquals(response.getStatus(), HttpStatus.OK.value());
	}

	@Test
	public void testGetTransactionsByCustomerId_thenReturnTransactionWith200Status() throws Exception  {
		when(underTest.getTransactionsByCustomerId(1L)).thenReturn( getTransactions());
		MockHttpServletRequestBuilder reqBuilder = MockMvcRequestBuilders.get("/v1/transactions/transaction-list/1");
		
		ResultActions perform = mockMvc.perform(reqBuilder);
		MvcResult mvcResult = perform.andReturn();
		MockHttpServletResponse response = mvcResult.getResponse();
		
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertEquals(response.getStatus(), HttpStatus.OK.value());
	}

	@Test
	public void testGetAllTransactions_thenReturnTransactionWith200Status() throws Exception  {

		when(underTest.getAllTransactions()).thenReturn( getTransactions());
		MockHttpServletRequestBuilder reqBuilder = MockMvcRequestBuilders.get("/v1/transactions");
		
		ResultActions perform = mockMvc.perform(reqBuilder);
		MvcResult mvcResult = perform.andReturn();
		MockHttpServletResponse response = mvcResult.getResponse();
		
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertEquals(response.getStatus(), HttpStatus.OK.value());
	}

	@Test
	public void testDeleteTransaction_thenReturnTransactionWith200Status() throws Exception  {
		when(underTest.deleteTransactionById(1L)).thenReturn(ApplicationConstants.TRANSACTION_DELETED_SUCCESS);
		MockHttpServletRequestBuilder reqBuilder = MockMvcRequestBuilders.delete("/v1/transactions/1");
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
	private List<Transaction> getTransactions() {
		List<Transaction> txs = new ArrayList<>();
		Transaction tx = new Transaction();
		tx.setId(1L);
		tx.setAmount(BigDecimal.valueOf(60.00));
		tx.setRewardPoints(10);
		tx.setTransactionDate(null);
		tx.setCustomerId(1L);
		txs.add(tx);
		return txs;
	}

	/**
	 * 
	 * @return
	 */
	private Transaction getTransaction() {
		Transaction tx = new Transaction();
		tx.setId(1L);
		tx.setAmount(BigDecimal.valueOf(60.00));
		tx.setRewardPoints(10);
		tx.setTransactionDate(null);
		tx.setCustomerId(1L);
		return tx;
	}
}

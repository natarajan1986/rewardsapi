package com.charter.reward.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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

import com.charter.reward.db.dto.Transaction;
import com.charter.reward.service.TransactionService;

@WebMvcTest(CustomerTransactionController.class)
@AutoConfigureMockMvc
public class CustomerTransactionControllerTest {

	@MockBean
	private TransactionService transactionService;

	@Autowired
	MockMvc mockMvc;
	

	@Test
	public void saveTransaction_thenReturnCustomerWith200Status() throws Exception  {

		when(transactionService.createTransaction( getTransaction())).thenReturn( getTransaction());
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
	private Transaction getTransaction() {
		Transaction tx = new Transaction();
		tx.setId(1L);
		tx.setAmount(BigDecimal.valueOf(60.00));
		tx.setRewardPoints(10);
		tx.setTransactionDate(LocalDateTime.now());
		tx.setCustomerId(1L);
		return tx;
	}
}

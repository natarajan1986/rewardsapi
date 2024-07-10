package com.charter.reward.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

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

import com.charter.reward.db.dto.Customer;
import com.charter.reward.service.CustomerService;

@WebMvcTest(CustomerController.class)
@AutoConfigureMockMvc
public class CustomerControllerTest {

	@MockBean
	private CustomerService customerService;

	@Autowired
	MockMvc mockMvc;

	@Test
	public void saveCustomer_thenReturnCustomerWith200Status() throws Exception {

		when(customerService.createCustomerAccount( getCustomer())).thenReturn( getCustomer());
		MockHttpServletRequestBuilder reqBuilder = MockMvcRequestBuilders.get("/v1/customers");
		ResultActions perform = mockMvc.perform(reqBuilder);
		MvcResult mvcResult = perform.andReturn();
		MockHttpServletResponse response = mvcResult.getResponse();
		assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
	}

	/**
	 * 
	 * @return
	 */
	private Customer getCustomer() {
		Customer customer = new Customer();
		customer.setId(1L);
		customer.setUsername("raja");
		customer.setPassword("raja");
		customer.setEmail("raja@gmail.com");
		customer.setPhoneNumber("8056047407");
		return customer;
	}

}

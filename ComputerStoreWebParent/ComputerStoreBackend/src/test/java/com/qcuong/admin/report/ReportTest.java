package com.qcuong.admin.report;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class ReportTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Test
	@WithMockUser(username = "admin@admin.com", password = "admin123", authorities="Admin")
	public void test7days() throws Exception {
		String requestURL = "/reports/sales_by_date/last_7_days";
		
		mockMvc.perform(get(requestURL)).andExpect(status().isOk()).andDo(print());
	}
}

package com.theironyard.invoicify.controllers;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.servlet.ModelAndView;

import com.theironyard.invoicify.models.BillingRecord;
import com.theironyard.invoicify.models.Company;
import com.theironyard.invoicify.models.FlatFeeBillingRecord;
import com.theironyard.invoicify.models.User;
import com.theironyard.invoicify.repositories.BillingRecordRepository;
import com.theironyard.invoicify.repositories.CompanyRepository;

public class FlatFeeBillingRecordControllerTests {
	
	private FlatFeeBillingRecordController controller;
	private FlatFeeBillingRecord record;
	private BillingRecordRepository repo;
	private CompanyRepository companyRepository;
	private Authentication authentication;
	private User user;
	
	@Before
	public void setup() {
		user = new User();
		record = new FlatFeeBillingRecord();
		authentication = mock(Authentication.class);
		companyRepository = mock(CompanyRepository.class);
		controller = new FlatFeeBillingRecordController(repo, companyRepository);
	}

	@Test
	public void test_create() {
		Company company = new Company();
		FlatFeeBillingRecord record = new FlatFeeBillingRecord();

		when(authentication.getPrincipal()).thenReturn(user);
		when(companyRepository.findOne(1L)).thenReturn(company);

		//ModelAndView actual = controller.create(record, 1L, authentication);
		
		//verify(companyRepository).findOne(1L);
		//assertThat(record.getClient()).isSameAs(company);
		//assertThat(actual.getViewName()).isEqualTo("redirect:/billing-records");
	}
}

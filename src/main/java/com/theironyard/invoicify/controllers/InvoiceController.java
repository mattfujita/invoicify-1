package com.theironyard.invoicify.controllers;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.theironyard.invoicify.models.BillingRecord;
import com.theironyard.invoicify.models.Company;
import com.theironyard.invoicify.models.Invoice;
import com.theironyard.invoicify.models.InvoiceLineItem;
import com.theironyard.invoicify.models.User;
import com.theironyard.invoicify.repositories.BillingRecordRepository;
import com.theironyard.invoicify.repositories.CompanyRepository;
import com.theironyard.invoicify.repositories.InvoiceRepository;

@Controller
@RequestMapping("/invoices")
public class InvoiceController {
	
	private CompanyRepository companyRepo;
	private BillingRecordRepository billingRepo;
	private InvoiceRepository invoiceRepo;
	
	public InvoiceController(CompanyRepository companyRepo, BillingRecordRepository billingRepo, InvoiceRepository invoiceRepo) {
		this.companyRepo = companyRepo;
		this.billingRepo = billingRepo;
		this.invoiceRepo = invoiceRepo;
	}

	@GetMapping("")
	public ModelAndView list(Authentication auth) {
		User user = (User) auth.getPrincipal();
		ModelAndView mv = new ModelAndView("invoices/list");
		mv.addObject("user", user);
		mv.addObject("invoices", invoiceRepo.findAll());
		return mv;
	}
	
	@GetMapping("new")
	public ModelAndView step1() {
		ModelAndView mv = new ModelAndView("invoices/step1");
		mv.addObject("companies", companyRepo.findAll());
		return mv;
	}
	
	@PostMapping("new") 
	public ModelAndView step2(long clientId) {
		ModelAndView mv = new ModelAndView("invoices/step2");
		mv.addObject("clientId", clientId);
		mv.addObject("records", billingRepo.findByClientId(clientId));
		return mv;
	}
	
	@PostMapping("create")
	public String createInvoice(Invoice invoice, long clientId, long[] recordIds, Authentication authentication) {
		User creator = (User) authentication.getPrincipal();
		List<BillingRecord> records = billingRepo.findByIdIn(recordIds);
		long nowish = Calendar.getInstance().getTimeInMillis();
		Date now = new Date(nowish);
		Company company = companyRepo.findOne(clientId);
		
		List<InvoiceLineItem> items = new ArrayList<InvoiceLineItem>();
		for (BillingRecord record : records) {
			InvoiceLineItem lineItem = new InvoiceLineItem();
			lineItem.setBillingRecord(record);
			lineItem.setCreatedBy(creator);
			lineItem.setCreatedOn(now);
			lineItem.setInvoice(invoice);
			items.add(lineItem);
		}
		
		invoice.setLineItems(items);
		invoice.setCreatedBy(creator);
		invoice.setCreatedOn(now);
		invoice.setId(clientId);
		invoice.setCompany(company);
		invoiceRepo.save(invoice);
		
		return "redirect:/invoices";
	}
	
}


















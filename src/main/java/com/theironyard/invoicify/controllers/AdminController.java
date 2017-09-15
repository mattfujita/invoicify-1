package com.theironyard.invoicify.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.theironyard.invoicify.models.Company;
import com.theironyard.invoicify.repositories.CompanyRepository;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	private CompanyRepository companyRepo;
	
	public AdminController(CompanyRepository companyRepo) {
		this.companyRepo = companyRepo;
	}
	
	@GetMapping("companies")
	public ModelAndView getCompanies() {
		ModelAndView mv = new ModelAndView("companies/companies");
		mv.addObject("companies", companyRepo.findAll());
		return mv;
	}
	
	@PostMapping("companies")
	public String createCompany(Company company) {
		companyRepo.save(company);
		
		return "redirect:/admin/companies";
	}

}

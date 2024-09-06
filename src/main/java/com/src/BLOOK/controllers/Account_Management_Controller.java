package com.src.BLOOK.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.src.BLOOK.models.Account;
import com.src.BLOOK.repository.AccountRepository;

@Controller
public class Account_Management_Controller {
	@Autowired
	private AccountRepository accountRepository;
	
	@GetMapping("/BLOOK/manager/account_management")
	public String doGet(Model model) {
		model.addAttribute("accounts", accountRepository.findAll());
		return "manager/account_management";
	}
	
	@GetMapping("/BLOOK/manager/remove/{username}")
	public String remove(@PathVariable("username") String username) {
		Optional<Account> optional = accountRepository.findByUsername(username);
		
		if(optional.isPresent()) {
			accountRepository.deleteById(username);
		}

		return "redirect:/BLOOK/manager/account_management";
	}
}

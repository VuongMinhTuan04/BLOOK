package com.src.BLOOK.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.src.BLOOK.models.Role_Account;
import com.src.BLOOK.repository.RoleAccountRepository;

@Service
public class RoleAccountService {
	@Autowired
	private RoleAccountRepository roleAccountRepository;
	
	@Transactional(readOnly = true)
	public Optional<Role_Account> findByRoleWithAccount(Boolean role){
		return roleAccountRepository.findByRoleWithAccounts(role);
	}
}

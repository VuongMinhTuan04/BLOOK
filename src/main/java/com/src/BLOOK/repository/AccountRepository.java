package com.src.BLOOK.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.src.BLOOK.models.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, String>{
	public Optional<Account> findByUsername(String username);
}

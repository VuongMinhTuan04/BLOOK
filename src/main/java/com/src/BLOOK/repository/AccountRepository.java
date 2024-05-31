package com.src.BLOOK.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.src.BLOOK.models.Account;

public interface AccountRepository extends JpaRepository<Account, String>{

}

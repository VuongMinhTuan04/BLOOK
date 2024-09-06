package com.src.BLOOK.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.src.BLOOK.models.Role_Account;

@Repository
public interface RoleAccountRepository extends JpaRepository<Role_Account, Boolean>{
	@Query("SELECT u FROM Role_Account u LEFT JOIN FETCH u.accounts WHERE u.role = :role")
    Optional<Role_Account> findByRoleWithAccounts(@Param("role") Boolean role);
}

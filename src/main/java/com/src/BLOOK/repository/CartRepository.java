package com.src.BLOOK.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.src.BLOOK.models.Account;
import com.src.BLOOK.models.Cart;
import com.src.BLOOK.models.Product;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer>{
	Optional<Cart> findByName(String name);
	Optional<Cart> findByNameAndAccount(String name, Account account);
	List<Cart> findByAccount(Account account);
	Optional<Cart> findByProductAndAccount(Product product, Account account);
}

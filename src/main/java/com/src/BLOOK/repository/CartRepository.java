package com.src.BLOOK.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.src.BLOOK.models.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer>{
	Optional<Cart> findByName(String name);
}

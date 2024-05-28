package com.src.BLOOK.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.src.BLOOK.models.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{

}

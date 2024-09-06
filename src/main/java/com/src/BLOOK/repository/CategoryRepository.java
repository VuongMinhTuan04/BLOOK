package com.src.BLOOK.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.src.BLOOK.models.Categories;

@Repository
public interface CategoryRepository extends JpaRepository<Categories, Integer>{

}

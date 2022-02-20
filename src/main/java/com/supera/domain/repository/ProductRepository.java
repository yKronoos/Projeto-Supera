package com.supera.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.supera.domain.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{
	Product findByName(String name);
}

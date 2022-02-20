package com.supera.domain.service;

import java.util.List;

import com.supera.domain.model.Product;

public interface ProductService {
	public abstract Product insert(Product p);
	public abstract Product delete(Long id);
	public abstract Product update(Long id, Product p);
	public abstract List<Product> findAll();
	public abstract Product findOne(Long id);
	public abstract Boolean existProduct(Long id);
}

package com.supera.domain.service;

import java.util.List;

import com.supera.domain.model.Cart;


public interface CartService {
	public abstract Cart insert(Cart c);
	public abstract Cart delete(Long id);
	public abstract List<Cart> findAll();
	public abstract Cart findOne(Long id);
	public abstract Boolean existProduct(Long id);
}

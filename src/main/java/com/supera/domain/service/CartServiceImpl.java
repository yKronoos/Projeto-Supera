package com.supera.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supera.domain.model.Cart;
import com.supera.domain.repository.CartRepository;

@Service
public class CartServiceImpl implements CartService {

	@Autowired
	private CartRepository cartRepository;

	@Override
	public Cart insert(Cart c) {

		return cartRepository.save(c);
	}

	@Override
	public Cart delete(Long id) {
		var cart = findOne(id);

		if (!cartRepository.existsById(id)) {
			return null;
		}
		cartRepository.deleteById(id);
		return cart;
	}

	@Override
	public List<Cart> findAll() {
		return cartRepository.findAll();
	}

	@Override
	public Cart findOne(Long id) {
		Optional<Cart> cart = cartRepository.findById(id);

		if (cart.isPresent()) {
			return cart.get();
		}

		return null;
	}

	@Override
	public Boolean existProduct(Long id) {
		if(!cartRepository.existsById(id)) {
			return false;
		}
		return true;
	}
}

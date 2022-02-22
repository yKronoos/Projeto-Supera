package com.supera.api.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.supera.api.model.CartInputModel;
import com.supera.api.model.CartModel;
import com.supera.domain.model.Cart;
import com.supera.domain.service.CartService;


@RestController
@RequestMapping("/cart")
public class CartController {

	@Autowired
	private CartService cartService;
	@Autowired
	private ModelMapper modelMapper;

	//Lista todos
	@GetMapping
	public List<CartModel> list() {
		return toColletionModel(cartService.findAll());
	}
	
	// Adicionar Produto
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CartModel add(@Valid @RequestBody CartInputModel cartInput) {

		Cart cart = toEntity(cartInput);

		Cart cartRes = cartService.insert(cart);

		CartModel cartModel = toModel(cartRes);

		return cartModel;
	}

	// deletar curso
	@DeleteMapping("/{cartId}")
	public ResponseEntity<Void> remove(@PathVariable Long cartId) {
		if (!cartService.existProduct(cartId)) {
			return ResponseEntity.notFound().build();
		}

		cartService.delete(cartId);

		return ResponseEntity.noContent().build();
	}

	// Models
	private CartModel toModel(Cart cart) {
		return modelMapper.map(cart, CartModel.class);
	}

	private List<CartModel> toColletionModel(List<Cart> carts) {
		return carts.stream().map(cart -> toModel(cart)).collect(Collectors.toList());
	}

	// transforma o de representação para o de dominio
	private Cart toEntity(CartInputModel cartInputModel) {
		return modelMapper.map(cartInputModel, Cart.class);
	}
}

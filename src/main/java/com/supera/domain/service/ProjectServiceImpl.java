package com.supera.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.supera.domain.exception.NegocioException;
import com.supera.domain.model.Product;
import com.supera.domain.repository.ProductRepository;

@Service
public class ProjectServiceImpl implements ProductService{

	@Autowired
	private ProductRepository productRepository;
	
	@Override
	public Product insert(Product p) {
		Optional<Product> productExists=productRepository.findById(p.getId());
		if(productExists.isPresent()) {
			throw new NegocioException("Já existe um produto cadastrado com esse id");
		}
		return productRepository.save(p);
	}

	@Override
	public Product delete(Long id) {
		var product = findOne(id);
		
		if(!productRepository.existsById(id)) {
			return null;
		}
		productRepository.deleteById(id);
		return product;
	}

	@Override
	public Product update(Long id, Product p) {
		if(!productRepository.existsById(id)) {
			throw new NegocioException("Esse produto não existe");
		}
		Product product = p;
		product.setId(id);
		product = productRepository.save(product);
		
		return p;
	}

	@Override
	public List<Product> findAll() {
		return productRepository.findAll();
	}

	@Override
	public Product findOne(Long id) {
		Optional<Product> product= productRepository.findById(id);
		
		if(product.isPresent()) {
			return product.get();
		}
		
		return null;
	}

	@Override
	public Boolean existProduct(Long id) {
		if(!productRepository.existsById(id)) {
			return false;
		}
		return true;
	}

	@Override
	public List<Product> orderPrice() {
		return productRepository.findAll(Sort.by(Sort.Direction.ASC, "price"));
	}

	@Override
	public List<Product> orderScore() {
		return productRepository.findAll(Sort.by(Sort.Direction.ASC, "score"));
	}

	@Override
	public List<Product> orderName() {
		return productRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
	}

}

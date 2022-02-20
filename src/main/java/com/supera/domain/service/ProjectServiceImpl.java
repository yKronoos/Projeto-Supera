package com.supera.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
		Product productExists=productRepository.findByName(p.getName());
		if(productExists!=null && !productExists.equals(p)) {
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
		
		p = productRepository.save(p);
		
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

}

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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.supera.api.model.ProductInputModel;
import com.supera.api.model.ProductModel;
import com.supera.domain.model.Product;
import com.supera.domain.service.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController {

	@Autowired
	private ProductService productService;
	@Autowired
	private ModelMapper modelMapper;

	@GetMapping
	public List<ProductModel> list() {
		return toColletionModel(productService.findAll());
	}

	// Busca Produtos
	@GetMapping("/{productId}")
	public ResponseEntity<ProductModel> search(@PathVariable Long productId) {
		if (!productService.existProduct(productId)) {
			return ResponseEntity.notFound().build();
		}

		Product productRes = productService.findOne(productId);

		ProductModel productModel = toModel(productRes);

		return ResponseEntity.ok(productModel);
	}

	// Adicionar Produto
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ProductModel add(@Valid @RequestBody ProductInputModel productInput) {

		Product product = toEntity(productInput);

		Product productRes = productService.insert(product);

		ProductModel productModel = toModel(productRes);

		return productModel;
	}

	// atualizar atravez do id
	@PutMapping("/{productId}")
	public ResponseEntity<ProductModel> update(@Valid @PathVariable Long productId,
			@RequestBody ProductInputModel productInput) {

		Product product = toEntity(productInput);

		if (!productService.existProduct(productId)) {
			return ResponseEntity.notFound().build();
		}

		Product productRes = productService.update(productId, product);

		ProductModel productModel = toModel(productRes);

		return ResponseEntity.ok(productModel);
	}

	// deletar curso
	@DeleteMapping("/{productId}")
	public ResponseEntity<Void> remove(@PathVariable Long productId) {
		if (!productService.existProduct(productId)) {
			return ResponseEntity.notFound().build();
		}

		productService.delete(productId);

		return ResponseEntity.noContent().build();
	}

	// Models
	private ProductModel toModel(Product product) {
		return modelMapper.map(product, ProductModel.class);
	}

	private List<ProductModel> toColletionModel(List<Product> products) {
		return products.stream().map(product -> toModel(product)).collect(Collectors.toList());
	}

	// transforma o de representação para o de dominio
	private Product toEntity(ProductInputModel productInputModel) {
		return modelMapper.map(productInputModel, Product.class);
	}

}

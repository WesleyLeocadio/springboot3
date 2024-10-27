package br.com.wesleydev.springboot.controllers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.wesleydev.springboot.dtos.ProductRecordDto;
import br.com.wesleydev.springboot.models.Product;
import br.com.wesleydev.springboot.repositories.ProductRepository;
import jakarta.validation.Valid;

@RestController
public class ProductController {
	@Autowired
	ProductRepository productRepository;
	
	@PostMapping(value="/products")
	public ResponseEntity<Product> saveProduct(@RequestBody @Valid ProductRecordDto productRecord){
		var product = new Product();
		BeanUtils.copyProperties(productRecord, product);
		return ResponseEntity.status(HttpStatus.CREATED).body(productRepository.save(product));
	}
	
	@GetMapping(value = "/products")
	public ResponseEntity<List<Product>> findAll() {
		List<Product> productList = productRepository.findAll();
		return ResponseEntity.status(HttpStatus.OK).body(productList);
	}
	
	@GetMapping(value = "/products/{id}")
	public ResponseEntity<Object> getOneProduct(@PathVariable(value = "id") UUID id) {
		Optional<Product> prod = productRepository.findById(id);
		if (prod.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found!");
		}
		return ResponseEntity.status(HttpStatus.OK).body(prod.get());
	}
	
	@PutMapping(value = "/products/{id}")
	public ResponseEntity<Object> updateProject(@PathVariable(value = "id") UUID id,
			@RequestBody @Valid ProductRecordDto productRecord) {
		Optional<Product> prod = productRepository.findById(id);
		if (prod.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found!");
		}
		var product = prod.get();
		BeanUtils.copyProperties(productRecord , product);

		return ResponseEntity.status(HttpStatus.OK).body(productRepository.save(product));
	}
	
	@DeleteMapping(value = "/products/{id}")
	public ResponseEntity<Object> deleteProduct(@PathVariable(value = "id") UUID id) {
		Optional<Product> product = productRepository.findById(id);
		if (product.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found!");
		}
		productRepository.delete(product.get());

		return ResponseEntity.status(HttpStatus.OK).body("Product deleted successfully.");
	}
	
	
	
}

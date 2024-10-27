package br.com.wesleydev.springboot.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.wesleydev.springboot.models.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {

}

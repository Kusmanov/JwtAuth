package com.example.JwtAuth.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductApi {
    @Autowired
    private ProductRepository repository;

    @PostMapping
    public ResponseEntity<Product> create(@RequestBody @Valid Product product) {
        Product savedProduct = repository.save(product);
        URI productURI = URI.create("/products/" + savedProduct.getId());
        return ResponseEntity.created(productURI).body(savedProduct);
    }

    @GetMapping
    public List<Product> list() {
        return repository.findAll();
    }
}


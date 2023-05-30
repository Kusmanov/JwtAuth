package com.example.JwtAuth.product.api;

import com.example.JwtAuth.jwt.JwtTokenUtil;
import com.example.JwtAuth.product.Product;
import com.example.JwtAuth.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
public class ProductApi {
    @Autowired
    private ProductRepository repository;
    @Autowired
    private Environment environment;
    @Autowired
    private JwtTokenUtil jwtUtil;

    @PostMapping(value = "/products")
    public ResponseEntity<Product> create(@RequestHeader("Authorization") String authorizationHeader, @RequestBody @Valid Product product) {
        String token = authorizationHeader.substring(7); // Извлечь токен без префикса "Bearer "
        String[] jwtSubject = jwtUtil.getSubject(token).split(",");
        String username = jwtSubject[1];
        product.setAdded(username);
        Product savedProduct = repository.save(product);
        URI productURI = URI.create("/products/" + savedProduct.getId());
        return ResponseEntity.created(productURI).body(savedProduct);
    }

    @GetMapping(value = "/products")
    public List<Product> list() {
        return repository.findAll();
    }

    //@RequestMapping(value = "/product/{id}", method = RequestMethod.GET)
    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getById(@PathVariable Integer id) {
        Product product = repository.findById(id).orElse(null);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(product);
    }
}
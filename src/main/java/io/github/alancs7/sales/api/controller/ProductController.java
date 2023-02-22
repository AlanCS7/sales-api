package io.github.alancs7.sales.api.controller;

import io.github.alancs7.sales.domain.entity.Product;
import io.github.alancs7.sales.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService service;

    @PostMapping
    public ResponseEntity<Product> save(@RequestBody @Valid Product product) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(product));
    }

    @GetMapping
    public ResponseEntity<List<Product>> find(Product product) {
        return ResponseEntity.ok(service.find(product));
    }

    @GetMapping("{id}")
    public ResponseEntity<Product> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PutMapping("{id}")
    public ResponseEntity<Product> update(@PathVariable("id") Long id, @RequestBody @Valid Product product) {
        return ResponseEntity.ok(service.update(id, product));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable("id") Long id) {
        service.deleteById(id);
    }
}

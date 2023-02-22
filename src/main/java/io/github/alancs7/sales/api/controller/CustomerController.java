package io.github.alancs7.sales.api.controller;

import io.github.alancs7.sales.domain.entity.Customer;
import io.github.alancs7.sales.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService service;

    @PostMapping
    public ResponseEntity<Customer> save(@RequestBody @Valid Customer customer) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(customer));
    }

    @GetMapping
    public ResponseEntity<List<Customer>> find(Customer customer) {
        return ResponseEntity.ok(service.find(customer));
    }

    @GetMapping("{id}")
    public ResponseEntity<Customer> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PutMapping("{id}")
    public ResponseEntity<Customer> update(@PathVariable("id") Long id, @RequestBody @Valid Customer customer) {
        return ResponseEntity.ok(service.update(id, customer));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable("id") Long id) {
        service.deleteById(id);
    }
}

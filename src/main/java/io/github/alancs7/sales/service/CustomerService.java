package io.github.alancs7.sales.service;

import io.github.alancs7.sales.domain.entity.Customer;

import java.util.List;

public interface CustomerService {

    Customer save(Customer customer);

    List<Customer> find(Customer customer);

    Customer findById(Long id);

    Customer update(Long id, Customer customer);

    void deleteById(Long id);
}

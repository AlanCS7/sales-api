package io.github.alancs7.sales.service;

import io.github.alancs7.sales.domain.entity.Product;

import java.util.List;

public interface ProductService {

    Product save(Product product);

    List<Product> find(Product product);

    Product findById(Long id);

    Product update(Long id, Product product);

    void deleteById(Long id);
}

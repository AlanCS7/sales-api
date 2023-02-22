package io.github.alancs7.sales.service.impl;

import io.github.alancs7.sales.domain.entity.Product;
import io.github.alancs7.sales.domain.repository.ProductRepository;
import io.github.alancs7.sales.exception.BusinessException;
import io.github.alancs7.sales.exception.ResourceNotFoundException;
import io.github.alancs7.sales.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

import static org.springframework.data.domain.ExampleMatcher.StringMatcher.CONTAINING;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public Product save(Product product) {
        if (product.getPrice().compareTo(BigDecimal.ZERO) < 0) {
            throw new BusinessException("The value of the product cannot be less than zero.");
        }
        return productRepository.save(product);
    }

    @Override
    public List<Product> find(Product product) {
        ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreCase().withStringMatcher(CONTAINING);
        Example<Product> example = Example.of(product, matcher);
        return productRepository.findAll(example);
    }

    @Override
    public Product findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Product %s not found.", id)));
    }

    @Override
    public Product update(Long id, Product product) {
        return productRepository.findById(id)
                .map(record -> {
                    product.setId(record.getId());
                    return productRepository.save(product);
                }).orElseThrow(() -> new ResourceNotFoundException(String.format("Product %s not found.", id)));
    }

    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(findById(id).getId());
    }
}

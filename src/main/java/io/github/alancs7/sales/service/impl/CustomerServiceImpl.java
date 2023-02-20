package io.github.alancs7.sales.service.impl;

import io.github.alancs7.sales.domain.entity.Customer;
import io.github.alancs7.sales.domain.repository.CustomerRepository;
import io.github.alancs7.sales.exception.ResourceNotFoundException;
import io.github.alancs7.sales.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.data.domain.ExampleMatcher.StringMatcher.CONTAINING;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public List<Customer> find(Customer customer) {
        ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreCase().withStringMatcher(CONTAINING);
        Example<Customer> example = Example.of(customer, matcher);
        return customerRepository.findAll(example);
    }

    @Override
    public Customer findById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Customer %s not found.", id)));
    }

    @Override
    public Customer update(Long id, Customer customer) {
        return customerRepository.findById(id)
                .map(record -> {
                    customer.setId(record.getId());
                    return customerRepository.save(customer);
                }).orElseThrow(() -> new ResourceNotFoundException(String.format("Customer %s not found.", id)));
    }

    @Override
    public void deleteById(Long id) {
        customerRepository.deleteById(findById(id).getId());
    }
}

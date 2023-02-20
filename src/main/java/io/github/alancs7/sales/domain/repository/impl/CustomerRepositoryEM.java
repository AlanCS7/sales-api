package io.github.alancs7.sales.domain.repository.impl;

import io.github.alancs7.sales.domain.entity.Customer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class CustomerRepositoryEM {

    @Autowired
    private EntityManager entityManager;

    @Transactional
    public Customer save(Customer customer) {
        entityManager.persist(customer);
        return customer;
    }

    @Transactional
    public Customer update(Customer customer) {
        entityManager.merge(customer);
        return customer;
    }

    @Transactional
    public void delete(Long id) {
        Customer customer = entityManager.find(Customer.class, id);
        entityManager.remove(customer);
    }

    @Transactional(readOnly = true)
    public List<Customer> getByName(String name) {
        String jpql = " SELECT c FROM Client c WHERE c.name LIKE :name ";
        TypedQuery<Customer> query = entityManager.createQuery(jpql, Customer.class);
        query.setParameter("name", "%" + name + "%");
        return query.getResultList();
    }

    @Transactional(readOnly = true)
    public List<Customer> getAll() {
        return entityManager.createQuery("FROM Client", Customer.class).getResultList();
    }

}

package io.github.alancs7.sales.domain.repository;

import io.github.alancs7.sales.domain.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    List<Customer> findByNameContainingIgnoreCase(String name);

    boolean existsByNameContainingIgnoreCase(String name);

    @Query(value = " select * from tb_customer c where c.name like %:name% ", nativeQuery = true)
    List<Customer> findAllCustomerByNameWithQuery(@Param("name") String name);

    @Query(value = " delete from Customer c where c.name = :name ")
    @Modifying
    void deleteByName(String name);

    @Query(value = " select c from Customer c left join fetch c.orders where c.id = :id ")
    Customer findCustomerFetchOrders(@Param("id") Long id);
}

package com.example.springcsvparsingwithmultithreading.repository;

import com.example.springcsvparsingwithmultithreading.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}

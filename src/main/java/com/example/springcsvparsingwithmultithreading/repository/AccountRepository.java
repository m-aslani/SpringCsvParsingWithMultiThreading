package com.example.springcsvparsingwithmultithreading.repository;

import com.example.springcsvparsingwithmultithreading.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}

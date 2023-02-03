package com.smaaaak.Portfolio.repository;

import com.smaaaak.Portfolio.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}

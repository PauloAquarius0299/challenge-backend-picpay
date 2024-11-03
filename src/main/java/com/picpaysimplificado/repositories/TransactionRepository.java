package com.picpaysimplificado.repositories;

import com.picpaysimplificado.Domain.users.Transection.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository <Transaction, Long> {

}

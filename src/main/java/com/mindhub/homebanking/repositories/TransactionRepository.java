package com.mindhub.homebanking.repositories;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RepositoryRestResource
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

//        -Buscar una lista de transacciones entre dos fechas pasadas por parametro
    List <Transaction> findByDateBetween(LocalDateTime date, LocalDateTime date2);
//    -Buscar una lista de transacciones en las cuales el monto se mayor a x monto pasado como primer parametro,
//    y menor a x monto  pasado como segundo parametro
    List <Transaction> findByAmountBetween( double amount1, double amount2);


//    -Buscar una lista de transacciones por type
    List <Transaction> findTransactionByType(TransactionType type);
}

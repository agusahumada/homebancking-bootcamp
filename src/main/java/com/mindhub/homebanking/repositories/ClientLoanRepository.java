package com.mindhub.homebanking.repositories;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.ClientLoan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface ClientLoanRepository extends JpaRepository<ClientLoan, Long> {
//-Buscar una lista de ClientLoan por cliente
    List<ClientLoan> findByClient(Client  client);


//    -Buscar una lista de ClientLoan que sean mayores a x monto pasado por parametro


    List<ClientLoan> findByAmountGreaterThan(Double amount);

//    -Buscar una lista de ClientLoan por cliente que  en cual sus balances sean menores a x monto pasado por parametro
    List<ClientLoan> findByClientAndAmountLessThan(Client client, Double amount);


}

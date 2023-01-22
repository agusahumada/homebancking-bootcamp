package com.mindhub.homebanking.repositories;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface AccountRepository extends JpaRepository <Account, Long> {

   // Optional<Object> findByNumber(String number);

 // -Buscar una lista de cuentas en el cual su balance se mayor a x monto pasado por parametro
  List<Account> findByBalanceGreaterThan(Double balance);
//          -Buscar una lista de cuentas por en la cual sue fecha se menor a la que le pasemos por parametro

  List<Account> findByCreationDateBefore(LocalDateTime creationDate);

    //  -Buscar una cuenta por Numero de cuenta
  Optional <Account> findByNumber(String number);


}
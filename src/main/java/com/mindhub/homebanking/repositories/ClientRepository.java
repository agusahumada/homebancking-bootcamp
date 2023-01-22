
package com.mindhub.homebanking.repositories;

import com.mindhub.homebanking.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface ClientRepository extends JpaRepository<Client, Long> {
   Optional<Client> findByEmail(String email);
  // Client findByEmail(String email);

   //Buscar cliente por nombre

   List <Client> findByFirstNameIgnoreCase(String firstName);
   //Buscar cliente por nombre y email

   Optional <Client> findByFirstNameAndEmailIgnoreCase(String firstName, String email);

   //-Buscar una lista de clientes por apellido

   List <Client> findByLastNameIgnoreCase(String lastName);


   //-----para el client loan----------------------------------


}





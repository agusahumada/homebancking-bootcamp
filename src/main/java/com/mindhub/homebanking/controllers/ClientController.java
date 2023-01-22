package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.dtos.ClientLoanDTO;
import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.ClientLoan;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")

public class ClientController {

        @Autowired
        private ClientRepository clientRepository;

        @Autowired
        private AccountRepository accountRepository;

        @Autowired
        PasswordEncoder passwordEncoder;

        @GetMapping("/clients")
        public List<ClientDTO> getClients() {
                return clientRepository.findAll().stream().map(ClientDTO::new).collect(toList());
        }

        @GetMapping("/clients/{id}")
        public ClientDTO getClient(@PathVariable Long id) {
                Optional<Client> clientOptional = clientRepository.findById(id);
                return clientRepository.findById(id).map(ClientDTO::new).orElse(null);
        }


        @GetMapping("/clients/email/{email}")
        public ClientDTO getClient(@PathVariable String email) {

                return clientRepository.findByEmail(email).map(ClientDTO::new).orElse(null);
        }

        @GetMapping("/clients/current")
        public ClientDTO getClient(Authentication authentication) {
                Client client = this.clientRepository.findByEmail(authentication.getName()).get();
                return new ClientDTO(client);


        }

        //------para encontrar por nombre------------//
        @GetMapping("/clients/firstName/{firstName}")
        public List<ClientDTO> getFirstNameIgnoreCase(@PathVariable String firstName) {
                return clientRepository.findByFirstNameIgnoreCase(firstName).stream().map(ClientDTO::new).collect(toList());
        }

        //--------para encontrar por nombre e email-----------------------//
        @GetMapping("/clients/firstName/{firstName}/email/{email}")
        public ClientDTO getFirstNameAndEmail(@PathVariable String firstName, @PathVariable String email) {
                return clientRepository.findByFirstNameAndEmailIgnoreCase(firstName, email).map(ClientDTO::new).orElse(null);
        }

        //-----para encontrar por apellido LISTA ------------------/
        @GetMapping("/clients/lastName/{lastName}")
        public List<ClientDTO> getLastNameIgnoreCase(@PathVariable String lastName) {
                return clientRepository.findByLastNameIgnoreCase(lastName).stream().map(ClientDTO::new).collect(toList());
        }

//------------------------------------------------------------original--------------------------------------
/*@PostMapping("/clients")
        public ResponseEntity<Object> createClient(@RequestParam String firstName, @RequestParam String lastName,
                                                        @RequestParam String email, @RequestParam String password) {
        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
                return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }

        if (clientRepository.findByEmail(email).isPresent()) {
                return new ResponseEntity<>("UserName already exists", HttpStatus.FORBIDDEN);
        }
        Client client = clientRepository.save(new Client(firstName, lastName, email, passwordEncoder.encode(password)));
        //Crear tambien una Account para el nuevo cliente, con accountNumber "VIN" + Random 3 cifras

        Account account = accountRepository.save(new Account(LocalDateTime.now(), 0.00, client));

        //Si todó salió bien. Retorno un estado CREATED que es correcto
        return new ResponseEntity<>(HttpStatus.CREATED);
  }
}*/
//-----------------------------original-fin--------------------------

        //------Excepciones!!-----------------------------
        @PostMapping("/clients")
        public ResponseEntity<Object> createClient(@RequestParam String firstName, @RequestParam String lastName,
                                                   @RequestParam String email, @RequestParam String password) {
                if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
                        return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
                }

                if (clientRepository.findByEmail(email).isPresent()) {
                        return new ResponseEntity<>("UserName already exists", HttpStatus.FORBIDDEN);
                }
                try {
                        Client client = clientRepository.save(new Client(firstName, lastName, email, passwordEncoder.encode(password)));
                        Account account;
                        int accounts = accountRepository.findAll().size();

                        do {
                                account = new Account(LocalDateTime.now(), 00.0, client);

                        } while (accountRepository.findByNumber(account.getNumber()).isPresent());

                        accountRepository.save(account);
                        return new ResponseEntity<>(HttpStatus.CREATED);


                } catch (Exception ex) {
                        ex.printStackTrace();
                        return new ResponseEntity<>("Unexpected error", HttpStatus.INTERNAL_SERVER_ERROR);
                }
        }
}


package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class AccountController {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    AccountRepository accountRepository;

    @RequestMapping("/accounts")
    public List<AccountDTO> getAccounts() {
        return accountRepository.findAll().stream().map(AccountDTO::new).collect(toList());
    }

    ;


    @RequestMapping("/accounts/{id}")
    public AccountDTO getAccount(@PathVariable Long id) {
        return accountRepository.findById(id).map(AccountDTO::new).orElse(null);
    }

    ;
//version1
        /*@GetMapping("/accounts/rico")
    public List<AccountDTO> getAccounts7000(){
        return this.accountRepository.findAll()
                        .stream()
                        .filter(account -> account.getBalance()>7000)
                        .map(account -> new AccountDTO(account))
                        .collect(toList());
    }*/


    //------------------------------------------POST-------------------------------------------
//        @PostMapping("/clients/{clientId}/accounts") //localhost:8080/api/clients/1/accounts
//        public ResponseEntity<Object> createAccount(@PathVariable Long clientId) {
//            try{
//                Optional<Client> client = clientRepository.findById(clientId);
//                 if(client.isPresent()) {
//                    Account account = new Account(LocalDateTime.now(), 0.00, client.get());
//                    accountRepository.save(account);
//                    return new ResponseEntity<>(HttpStatus.CREATED);
//                }else{
//                     return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//                 }
//            }catch(Exception exception){
//                System.out.println(exception.getMessage());
//                exception.printStackTrace();
//                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//            }
//
//        }
//----------------------------------------Buscar una lista de cuentas en el cual su balance se mayor a x monto pasado por parametro
    @GetMapping("/accounts/balanceGreaterThan/{balance}")
    public List<AccountDTO> getAccountGreaterThan(@PathVariable Double balance) {
        return accountRepository.findByBalanceGreaterThan(balance).stream().map(AccountDTO::new).collect(toList());
    }

    //-------------------------------------Buscar una lista de cuentas por en la cual sue fecha se menor a la que le pasemos por parametro
    @GetMapping("/accounts/dateBefore/{creationDate}")
    public List<AccountDTO> getAccount(@PathVariable String creationDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return accountRepository.findByCreationDateBefore(LocalDateTime.parse(creationDate, formatter)).stream().map(AccountDTO::new).collect(toList());
    }

    //------------------------------------------Buscar una cuenta por Numero de cuenta

    public AccountDTO getAccountNumber(@PathVariable String number) {
        return accountRepository.findByNumber(number).map(AccountDTO::new).orElse(null);
    }

    //---------------------------------------------------------------------------------
    @PostMapping("/clients/current/accounts")

    public ResponseEntity<Object> createAccount(Authentication authentication) {
        Optional<Client> client = clientRepository.findByEmail(authentication.getName());
        try {
            if (client.get().getAccounts().stream().count() >= 3) {
                return new ResponseEntity<>("you already have 3 accounts", HttpStatus.FORBIDDEN);
            }
            Account account = new Account(LocalDateTime.now(), 00.00, client.get());
            accountRepository.save(account);

            return new ResponseEntity<>("new account ", HttpStatus.CREATED);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            exception.printStackTrace();
            return new ResponseEntity<>("Error creating account", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}


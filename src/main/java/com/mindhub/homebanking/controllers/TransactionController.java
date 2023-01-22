package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.dtos.TransactionDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class TransactionController {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    ClientRepository clientRepository;

    @RequestMapping("/transactions")
    public List<AccountDTO> getAccounts() {
        return accountRepository.findAll().stream().map(AccountDTO::new).collect(toList());
    }

    ;


    @RequestMapping("/transactions/{id}")
    public AccountDTO getAccount(@PathVariable Long id) {
        return accountRepository.findById(id).map(AccountDTO::new).orElse(null);
    }


    //-----------------------Para encontrar transactions entre dos fechas----------------------------------------
    @GetMapping("/transactions/date-between/date1/{date1}/date2/{date2}")
    public List<TransactionDTO> getTransactionBetween(@PathVariable String date1, @PathVariable String date2) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return transactionRepository.findByDateBetween(LocalDateTime.parse(date1, formatter), LocalDateTime.parse(date2, formatter)).stream().map(TransactionDTO::new).collect(toList());
    }

    //-Buscar una lista de transacciones por type
    @GetMapping("/transactions/type/{type}")
    public List<TransactionDTO> getTransaction(@PathVariable TransactionType type) {
        return transactionRepository.findTransactionByType(type).stream().map(TransactionDTO::new).collect(toList());
    }
    //-Buscar una lista de transacciones en las cuales el monto se mayor a x monto pasado como primer parametro,

    @GetMapping("transactions/amount1/{amount1}/amount2/{amount2}")
    public List<TransactionDTO> getTransaction(@PathVariable double amount1, @PathVariable double amount2) {
        return transactionRepository.findByAmountBetween(amount1, amount2).stream().map(TransactionDTO::new).collect(toList());
    }

    //-----------------------------------------------------Transaccion Unidad 8--------------------------------------------
    @Transactional
    @PostMapping("/transactions/transactionsBetweenAccounts")
    public ResponseEntity<Object> createdTransactionBetweenAccounts(Authentication authentication, @RequestParam Double amount, @RequestParam String description,
                                                                   @RequestParam String fromAccount, @RequestParam String toAccount) {
        try {
            Client client = clientRepository.findByEmail(authentication.getName()).get();
            if (!(amount<=0) && !(description.isEmpty()) && !(fromAccount.isEmpty()) && !(toAccount.isEmpty())) {
                Optional<Account> optionalFromAccount = accountRepository.findByNumber(fromAccount);
                Optional<Account> optionalToAccount = accountRepository.findByNumber(toAccount);

                if (!(fromAccount.equals(toAccount)) && optionalFromAccount.isPresent()) {//valuamos q los numeros sean diferentes
                    Set<Account> accountsClientAuthentication = client.getAccounts();//toma todas las cuentas del cliente autenticado
                    if (accountsClientAuthentication.contains(optionalFromAccount.get()) &&
                            optionalToAccount.isPresent() &&
                            optionalFromAccount.get().getBalance() >= amount) {
                        Transaction transactionFrom = new Transaction(amount, description, LocalDateTime.now(), TransactionType.DEBIT, optionalFromAccount.get());
                        transactionRepository.save(transactionFrom);
                        Transaction transactionTo = new Transaction(amount, description, LocalDateTime.now(), TransactionType.CREDIT, optionalToAccount.get());
                        transactionRepository.save(transactionTo);
                        return new ResponseEntity<>("Transaction successful", HttpStatus.CREATED);
                    } else {
                        return new ResponseEntity<>("Transaction error", HttpStatus.FORBIDDEN);
                    }
                } else {
                    return new ResponseEntity<>("The accounts are the same", HttpStatus.FORBIDDEN);
                }
            } else {
                return new ResponseEntity<>("Empty parameters", HttpStatus.FORBIDDEN);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>("CATCH ERROR", HttpStatus.FORBIDDEN);
        }
    }
}
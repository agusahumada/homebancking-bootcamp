package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.CardDTO;
import com.mindhub.homebanking.dtos.ClientLoanDTO;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.ClientLoan;
import com.mindhub.homebanking.repositories.ClientLoanRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;


@RestController
@RequestMapping("/api")
public class ClientLoanController {

    @Autowired
    private ClientLoanRepository clientloanRepository;

    @Autowired
    private ClientRepository clientRepository;

    @GetMapping("/clientloans")
    public List<ClientLoanDTO> getClients() {
        return clientloanRepository.findAll().stream().map(ClientLoanDTO::new).collect(toList());
    }

    @GetMapping("/clientloans/{id}")
    public ClientLoanDTO getLoan(@PathVariable Long id) {
        Optional<ClientLoan> clientOptional = clientloanRepository.findById(id);
        return clientloanRepository.findById(id).map(ClientLoanDTO::new).orElse(null);
    }

    //-------lista clientloan por Cliente
    @GetMapping("/clientLoans/client/{client}")
    public List<ClientLoanDTO> getClientLoansByClient(@PathVariable Client client) {
        return clientloanRepository.findByClient(client).stream().map(ClientLoanDTO::new).collect(toList());
    }

    //--------clienloan por amount mayor al pasado---------
    @GetMapping("/clientloans/amountGreater/{amount}")
    public List<ClientLoanDTO> clientLoan(@PathVariable Double amount) {
        return clientloanRepository.findByAmountGreaterThan(amount).stream().map(ClientLoanDTO::new).collect(toList());
    }


    @GetMapping("/clientloans/client/{client}/amountLessThan/{amount}")
    public List<ClientLoanDTO> clientAndAmountLessThan(@PathVariable Client client, @PathVariable Double amount) {
        return clientloanRepository.findByClientAndAmountLessThan(client, amount).stream().map(ClientLoanDTO::new).collect(toList());
    }
}


package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.CardDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class CardController {

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private ClientRepository clientRepository;


    @GetMapping("/clients/{id}/cards")
    public List<CardDTO> getCard(@PathVariable Long id) {
        Optional<Client> optionalClient = clientRepository.findById(id);
        if (optionalClient.isPresent()) {
            return optionalClient.get().getCards().stream().map(CardDTO::new).collect(toList());
        } else {
            return new ArrayList<>();
        }

    }

    //----------------------------------POST----------------------------
    @PostMapping("/clients/current/cards")
    public ResponseEntity<Object> createCard(@RequestParam CardType cardType,
                                             @RequestParam CardColor cardColor,
                                             Authentication authentication) {
        Optional<Client> client = this.clientRepository.findByEmail(authentication.getName());

        if (client.isPresent()) {
            int contador = 0;
            for (Card card : client.get().getCards()) {
                //System.out.println("entra al ciclo "+card.getType() + "-"+ cardType);--banderas
                if (card.getType().equals(cardType)) {
                    contador++;
                    // System.out.println("entro");--banderas
                }
            }
            if (contador >= 3) {
                return new ResponseEntity<>("You already have 3 " + cardType + " cards", HttpStatus.FORBIDDEN);
            } else {
                Card card = new Card(client.get(), cardType, cardColor);
                cardRepository.save(card);
                return new ResponseEntity<>(HttpStatus.CREATED);
            }
        } else {
            return new ResponseEntity<>("Client doesnt exist", HttpStatus.FORBIDDEN);
        }

    }
    //----------otra opcion al if each usando lambda------------------------------------------------------//
//    if (client.get().getCards().stream().filter(card -> card.getType() == cardType).count() >= 3) {
//        return new ResponseEntity<>("You already have 3 " + cardType + " cards", HttpStatus.FORBIDDEN);
//    }

}

package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;
import utils.CardUtils;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;

    private String cardHolder;

    private CardType type;

    private CardColor color;

    private String number;

    private String cvv;

    private LocalDate thruDate;

    private LocalDate fromDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    private Client client;

    //--------------------aux--------------------

    //Constructor vacio
    public Card() {
    }
    //buenas practicas, nombre fecha.
    /*public Card(Client client,  CardType type, ColorType color, String number, int cvv,  LocalDate fromDate) {
        this.client= client;
        this.cardHolder = client.getFirstName()+" "+client.getLastName();
        this.type = type;
        this.color = color;
        this.number = number;
        this.cvv= cvv;
        this.fromDate = fromDate;
        switch (this.color) {
            case GOLD : this.thruDate = fromDate.plusYears(5); break;
            case SILVER: this.thruDate = fromDate.plusYears(3);break;
            case TITANIUM:this.thruDate=fromDate.plusYears(4);break;
            default: this.thruDate=fromDate;
        }
    }*/

    //--------------------------------------------------

    //Constructor que se solicita en MindHub
//    public Card(Client client, String cardHolder, CardType type, ColorType color, String number, LocalDate thruDate, LocalDate fromDate) {
//        this.client = client;
//        this.cardHolder = cardHolder;
//        this.type = type;
//        this.color = color;
//        this.number = number;
//        this.cvv = generateCvv();
//        this.thruDate = thruDate;
//        this.fromDate = fromDate;
//    }

    //-------------------constructor para el card controller y Homebanking desde Unidad 6---------------------------------
    public Card(Client client, CardType type, CardColor color) {
        this.client = client;
        this.cardHolder = client.getFirstName() + " " + client.getLastName();
        this.type = type;
        this.color = color;
        this.number = CardUtils.generateRandomNumbers(4) + " " + CardUtils.generateRandomNumbers(4) + " " + CardUtils.generateRandomNumbers(4) + " " + CardUtils.generateRandomNumbers(4);
        this.cvv = CardUtils.generateRandomNumbers(3);
        this.fromDate = LocalDate.now();
        this.thruDate = fromDate.plusYears(5); //del banco toma el entero de cuantos años.

    }

    //----------------------------------------- metodo para generar numeros de tarjeta
//    public String generateNumber() {
//        String cardNumber = "";
//        for (int i = 0; i < 4; ++i) {
//            int newNumber = (int) (Math.random() * 10);
//            cardNumber += String.valueOf(newNumber);
//        }
//        return cardNumber;
//    }
//
//    //generador de cvv-----------------------------
//    public String generateCvv() {
//        String newCvv = "";
//        for(int i = 0; i < 3; i++) {
//            int newNumber = (int) (Math.random() * 10);
//            newCvv += String.valueOf(newNumber);
//        }
//        return  newCvv;
//    }

    //--------------------------------------------------
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
    }

    public CardType getType() {
        return type;
    }

    public void setType(CardType type) {
        this.type = type;
    }

    public CardColor getColor() {
        return color;
    }

    public void setColor(CardColor color) {
        this.color = color;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public LocalDate getThruDate() {
        return thruDate;
    }

    public void setThruDate(LocalDate thruDate) {
        this.thruDate = thruDate;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }
}

package com.mindhub.homebanking.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")

    private Long id;

    private double amount;
    private String description;
    private LocalDateTime date;

    private TransactionType type;

    //relacion
   // @JsonIgnore

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id")
    private Account account;

    // constructor vacio
    public Transaction() {
    }

    //Constructor

    public Transaction( double amount, String description, LocalDateTime date, TransactionType type, Account account) {

        this.amount = amount;
        this.description = description;
        this.date = date;
        this.type = type;
        this.account = account;
        if (type.equals(TransactionType.DEBIT)) {
            this.account.setBalance(this.account.getBalance() - amount);
        } else{
            this.account.setBalance(this.account.getBalance() + amount);}

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }


}


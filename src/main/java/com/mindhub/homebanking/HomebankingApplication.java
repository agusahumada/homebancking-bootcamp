package com.mindhub.homebanking;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}

	//private PasswordEncoder passwordEncoder;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Bean
	public CommandLineRunner initData(TransactionRepository transactionRepository, AccountRepository accountRepository,
									  ClientRepository clientRepository, LoanRepository loanRepository,
									  ClientLoanRepository clientLoanRepository,
									  CardRepository cardRepository){
		return (args) -> {
			Client client1 = new Client("Melba", "Morel", "melba@mindhub.com", passwordEncoder.encode("contraseñapeligrosa"));
			Client client2 = new Client("Marta", "Lopez", "martalopez@yahoo.com", passwordEncoder.encode("contraseñapeligrosa"));
			Client client3 = new Client("Melba", "Perez", "melbare@mindhub.com", passwordEncoder.encode("contraseñapeligrosa"));
			clientRepository.save(client1);
			clientRepository.save(client2);
			clientRepository.save(client3);


			Account account1= new Account(LocalDateTime.now(),5500.00, client1);
			Account account2= new Account(LocalDateTime.now().plusDays(1),2020.00, client1);
			Account account3= new Account(LocalDateTime.now(),4000.00, client2);
			accountRepository.save(account1);
			accountRepository.save(account2);
			accountRepository.save(account3);

			Transaction transaction1 = new Transaction(22.00, "cuenta de prueba", LocalDateTime.now(), TransactionType.CREDIT,account1);
			Transaction transaction2 = new Transaction(250, "compra", LocalDateTime.now().plusDays(1), TransactionType.DEBIT,account2);
			Transaction transaction3 = new Transaction(250, "compra", LocalDateTime.now().minusDays(2), TransactionType.DEBIT,account2);
			transactionRepository.save(transaction1);
			transactionRepository.save(transaction2);
			transactionRepository.save(transaction3);

			Loan loan1 = new Loan("Hipotecario", 500000.000, List.of(12, 24, 36, 48, 60) );
			Loan loan2 = new Loan("Personal", 100000.00, List.of(6, 12, 24) );
			Loan loan3 = new Loan("Automotriz", 300000.00, List.of(6,12, 24, 36) );
			loanRepository.save(loan1);
			loanRepository.save(loan2);
			loanRepository.save(loan3);

			ClientLoan clientLoan1 = new ClientLoan(400000.00, 60, client1, loan1);
			ClientLoan clientLoan2 = new ClientLoan(50000.00, 12, client1, loan2);
			ClientLoan clientLoan3 = new ClientLoan(100000.00, 24, client2, loan2);
			ClientLoan clientLoan4 = new ClientLoan(200000.00, 36, client2, loan3);
			clientLoanRepository.save(clientLoan1);
			clientLoanRepository.save(clientLoan2);
			clientLoanRepository.save(clientLoan3);
			clientLoanRepository.save(clientLoan4);

			Card card1 = new Card(client1,CardType.DEBIT, CardColor.GOLD);
			Card card2 = new Card(client1,CardType.CREDIT, CardColor.TITANIUM);
			Card card4 = new Card(client1,CardType.CREDIT, CardColor.SILVER);
			Card card3 = new Card(client2, CardType.CREDIT, CardColor.SILVER);
			cardRepository.save(card1);
			cardRepository.save(card2);
			cardRepository.save(card3);
			cardRepository.save(card4);


		};
	}

}

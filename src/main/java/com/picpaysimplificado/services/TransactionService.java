package com.picpaysimplificado.services;

import com.picpaysimplificado.DTOs.TransactionDTO;
import com.picpaysimplificado.Domain.users.Transection.Transaction;
import com.picpaysimplificado.Domain.users.User;
import com.picpaysimplificado.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Service
public class TransactionService {
    @Autowired
    private UserServices userService;

    @Autowired
    private TransactionRepository repository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private NotificationService notificationService;

    public Transaction createTransaction(TransactionDTO transaction) throws Exception {
        User sender = this.userService.findUserById(transaction.senderId());
        User receiver = this.userService.findUserById(transaction.receiverId());

        userService.validateTransaction(sender, transaction.value());

        boolean isAuthorized = this.authorizeTransaction(sender, transaction.value());
        if(!isAuthorized){
            throw new Exception("Transição não autorizada");
        }

        Transaction newTransaction = new Transaction();
        newTransaction.setAmount(transaction.value());
        newTransaction.setSender(sender);
        newTransaction.setReceiver(receiver);
        newTransaction.setTimestamp(LocalDateTime.now());

        sender.setBalance(sender.getBalance().subtract(transaction.value()));
        receiver.setBalance(receiver.getBalance().add(transaction.value()));

        this.repository.save(newTransaction);
        this.userService.saveUser(sender);
        this.userService.saveUser(receiver);

        this.notificationService.sendNotification(sender, "Transação realizada com sucesso!");
        this.notificationService.sendNotification(receiver, "Transação recebida com sucesso!");

        return newTransaction;
    }
//    public boolean authorizeTransaction(User sender, BigDecimal value){
//       ResponseEntity<Map> authorizationResponse = restTemplate.getForEntity("https://util.devi.tools/api/v2/authorize", Map.class);
//
//       if(authorizationResponse.getStatusCode() == HttpStatus.OK){
//           String message = (String) authorizationResponse.getBody().get("Message");
//           return "Autorizado". equalsIgnoreCase(message);
//       } else return false;
//    }

    //Para garantir que a transição seja autorizada, ajustei a logica de verificação
    //na função 'autorizeTransaction', pois o retorno da API externa indica explicitamente
    //que a autorização falhou, então adaptei a lógica para analisar o conteudo da resposta.

    public boolean authorizeTransaction(User sender, BigDecimal value) {
        ResponseEntity<Map> authorizationResponse = restTemplate.getForEntity("https://util.devi.tools/api/v2/authorize", Map.class);

        if (authorizationResponse.getStatusCode() == HttpStatus.OK) {
            Map<String, Object> responseBody = authorizationResponse.getBody();
            if (responseBody != null) {
                Map<String, Object> data = (Map<String, Object>) responseBody.get("data");
                Boolean isAuthorized = (Boolean) data.get("authorization");
                return isAuthorized != null && isAuthorized;
            }
        }
        return false;

        //Verifiquei o corpo de resposta caso for nulo evita 'NullPointerExpection'.
        //A função retorna 'true' se 'autorization' for 'true'; caso contrário false.
    }

}

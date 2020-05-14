package com.capgemini.main.controller;

import java.util.List;

import javax.transaction.Transaction;
import javax.validation.Valid;
import javax.validation.constraints.Min;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.main.dto.TransactionDto;
import com.capgemini.main.entity.WalletTransactions;
import com.capgemini.main.exceptions.AccountNotFoundException;
import com.capgemini.main.exceptions.TransactionNotFoundException;
import com.capgemini.main.services.TransactionServices;

@RestController
@RequestMapping("/transactions")
@Validated 

public class TransactionRestController {
	private static final Logger Log= LoggerFactory.getLogger(TransactionRestController.class);

    @Autowired
    private TransactionServices service;

   @PostMapping("/add")
    public ResponseEntity<WalletTransactions>createTransaction(@RequestBody @Valid TransactionDto dto){
	   WalletTransactions transaction=convertFromDto(dto);
        transaction=service.save(transaction);
        ResponseEntity<WalletTransactions>response=new ResponseEntity<>(transaction, HttpStatus.OK);
        return response;
   }

   public WalletTransactions convertFromDto( TransactionDto dto){
	   WalletTransactions transaction=new WalletTransactions();
       transaction.setTransactionId(dto.getTransactionId());
       transaction.setDescription(dto.getDescription());
       transaction.setTransactionTime(dto.getTransactionTime());
       transaction.setAmount(dto.getAmount());
       transaction.setBalance(dto.getBalance());
       
       return transaction;
   }

   @GetMapping("/getbyid/{id}")
   public ResponseEntity<WalletTransactions>findTransactionById( @PathVariable("id") @Min(1)  long id){
	   WalletTransactions transaction= service.findById(id);
      ResponseEntity<WalletTransactions>response=new ResponseEntity<>(transaction,HttpStatus.OK);
      return response;
   }
   
   @GetMapping("/getbyaccount/{account}")
   public ResponseEntity<List<WalletTransactions>>findTransactionByAccount( @PathVariable("account") @Min(1)  int account){
	   List<WalletTransactions> transactions= service.findByAccount(account);
      ResponseEntity<List<WalletTransactions>>response=new ResponseEntity<>(transactions,HttpStatus.OK);
      return response;
   }
   
    @GetMapping
   public ResponseEntity<List<WalletTransactions>>fetchAll(){
       List<WalletTransactions>transaction=service.fetchAll();
       ResponseEntity<List<WalletTransactions>>response=new ResponseEntity<>(transaction,HttpStatus.OK);
       return response;
   }


   @ExceptionHandler(TransactionNotFoundException.class)
   public ResponseEntity<String>handleTransactionNotFound(TransactionNotFoundException ex){
       Log.error("Transaction not found exception",ex);
       String msg=ex.getMessage();
       ResponseEntity<String>response=new ResponseEntity<>(msg,HttpStatus.NOT_FOUND);
       return response;
   }

   @ExceptionHandler(AccountNotFoundException.class)
   public ResponseEntity<String>handleAccountNotFound(AccountNotFoundException ex){
       Log.error("Account not found exception",ex);
       String msg=ex.getMessage();
       ResponseEntity<String>response=new ResponseEntity<>(msg,HttpStatus.NOT_FOUND);
       return response;
   }
   
   @ExceptionHandler(Throwable.class)
   public ResponseEntity<String>handleAll(Throwable ex){
     Log.error("exception caught",ex);
     String msg=ex.getMessage();
     ResponseEntity<String>response=new ResponseEntity<>(msg,HttpStatus.INTERNAL_SERVER_ERROR);
     return response;
   }




}

package com.capgemini.main.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.main.dao.TransactionDao;
import com.capgemini.main.entity.WalletTransactions;
import com.capgemini.main.exceptions.AccountNotFoundException;
import com.capgemini.main.exceptions.TransactionNotFoundException;
import com.capgemini.main.services.TransactionServices;


@Service
@Transactional
public class TransactionServiceImpl implements TransactionServices {


    private TransactionDao transactionDao;

   

    @Autowired
    public void setTransactionDao(TransactionDao dao) {
        this.transactionDao = dao;
    }
    public TransactionDao getTransactionDao() {
        return transactionDao;
    }

	@Override
	public WalletTransactions save(WalletTransactions transaction) {
		transaction= transactionDao.save(transaction);
        return transaction ;

	}

	@Override
	public WalletTransactions findById(long id) {


	     Optional< WalletTransactions>optional=transactionDao.findById(id);
	     if(optional.isPresent()){
	    	 WalletTransactions transaction=optional.get();
	         return transaction ;
	     }
	   
	     throw new TransactionNotFoundException("Transaction not found for id="+id);

	}

	@Override
	public List<WalletTransactions> fetchAll() {

	     List<WalletTransactions>list=  transactionDao.findAll();
	      return list;

	}
	@Override
	public List <WalletTransactions> findByAccount(int account) {
		
		

	       List <WalletTransactions> transactions =transactionDao.findByAccount(account);
	       
	       if(!transactions.isEmpty())
	       {
	    	   return transactions ;
	       }
	       else
	       {
		     throw new AccountNotFoundException("Account not found for account="+account);
	       }


	}
	}

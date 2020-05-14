package com.capgemini.main.services;

import java.util.List;

import com.capgemini.main.entity.WalletTransactions;

public interface TransactionServices {



    WalletTransactions save(WalletTransactions transaction);
    WalletTransactions findById(long id);
    List<WalletTransactions> fetchAll();
    List<WalletTransactions> findByAccount(int id);


}

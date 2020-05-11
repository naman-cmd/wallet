package services;

import java.util.List;

import entity.WalletTransactions;

public interface TransactionServices {



    WalletTransactions save(WalletTransactions transaction);
    WalletTransactions findById(long id);
    List<WalletTransactions> fetchAll();
    WalletTransactions findByAccount(int id);


}

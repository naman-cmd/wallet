package dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import entity.WalletTransactions;

public interface TransactionDao extends JpaRepository<WalletTransactions,Long> {

	List <WalletTransactions> findByAccount(int account);

}

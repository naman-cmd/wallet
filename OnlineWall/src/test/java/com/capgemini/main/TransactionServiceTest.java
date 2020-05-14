package com.capgemini.main;

import java.util.List;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.capgemini.main.entity.WalletTransactions;
import com.capgemini.main.services.TransactionServiceImpl;
import com.capgemini.main.services.TransactionServices;



@DataJpaTest
@ExtendWith(SpringExtension.class)
@Import(TransactionServiceImpl.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class TransactionServiceTest {
	
	@Autowired
	private TransactionServices transactionService;
	
	@Autowired
	EntityManager entityManager;
	
	@Test
	public void testSave()
	{
	WalletTransactions transaction = new WalletTransactions();
	transaction.setAmount(500);
	transaction.setBalance(500);
	transaction.setDescription("xyz");
	WalletTransactions result = transactionService.save(transaction);
	List<WalletTransactions> fetched = entityManager.createQuery("from WalletTransactions").getResultList();
    int size = fetched.size();
    WalletTransactions expected = fetched.get(size-1);
    System.out.println(expected);
    System.out.println(result);
    Assertions.assertEquals(expected, result);
	
	
	}
	@Test
	public void testGetTransaction() {
		WalletTransactions transaction = new WalletTransactions();
		transaction.setAmount(50);
		transaction.setBalance(5000);
		transaction.setDescription("abc");
		WalletTransactions transaction1 = transactionService.save(transaction);
		List<WalletTransactions> fetched = entityManager.createQuery("from WalletTransactions").getResultList();
        int size = fetched.size();
        WalletTransactions expected = fetched.get(size-1);
        WalletTransactions result = transactionService.findById(transaction1.getTransactionId());
        System.out.println(expected);
        System.out.println(result);
        Assertions.assertEquals(expected, result);
	}
	@Test
	public void testGetAllTransaction() {
		List< WalletTransactions> expected = entityManager.createQuery("from  WalletTransactions").getResultList();
        List< WalletTransactions> result = transactionService.fetchAll();
        System.out.println(expected);
        System.out.println(result);
        Assertions.assertEquals(expected, result);
	}
	
	
	
}

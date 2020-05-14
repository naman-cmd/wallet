package com.capgemini.main.exceptions;

public class AccountNotFoundException extends RuntimeException {

	public AccountNotFoundException(String msg){
        super(msg);
    }
}

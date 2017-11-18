package com.abc.account;

import java.util.ArrayList;
import java.util.List;

import com.abc.Transaction;

public abstract class Account {

	private List<Transaction> transactions;
	public double totalBalance = 0;
	
	public Account() {
		this.transactions = new ArrayList<>();
	}
	
		
	public void deposit(double amount) {
		if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount));
        }
	}

	public void withdraw(double amount) {
		if (amount <= 0) {
	        throw new IllegalArgumentException("amount must be greater than zero");
	    } else {
	        transactions.add(new Transaction(-amount));
	    }
	}

	

	protected void sumTransactions() {
		double sum = 0;
		for (Transaction t: transactions)
            sum += t.amount;
        this.totalBalance = sum;
	}
	
	
	public abstract double calculateInterest();
	

}
package com.abc;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;

public class Account {

	private final Type accountType;
	public List<Transaction> transactions;
	private double balance;
	private double interestEarned = 0.0;

	public double getSumMoney() {
		return balance;
	}
	public static void main(String []args) {
		double initial = 10000;
		double store = initial;
		int withdrawal = 100;
		
//		for(int i = 1; i<=365; i++) {
//			store += store*(0.05/365);	
//			System.out.println("Daily compound of day " + i + ":" + store);
//		}
		
		
		for(int i = 1; i<=withdrawal; i++) {
			store += store*(0.05/365);	
			System.out.println("Daily compound of day " + i + ":" + store);
		}
		for(int i = withdrawal+1; i<=withdrawal+11; i++) {
			store += store*(0.001/365);		
			System.out.println("Daily compound of day " + i + ":" + store);
		}
		for(int i = withdrawal+12; i<=365; i++) {
			store += store*(0.05/365);		
			System.out.println("Daily compound of day " + i + ":" + store);
		}
		System.out.println("Final Daily compound: " + store);
		System.out.println("Yearly compound: " + (initial+initial*0.05) );
	}
	
	public Account(Type accountType) {
		this.accountType = accountType;
		this.transactions = new ArrayList<Transaction>();
		this.balance = 0.0;
	}

	/**
	 * @param amount
	 *            must be greater than zero
	 */
	public void deposit(double amount) {
		if (amount < 0)
			throw new IllegalArgumentException("amount must be greater than zero");

		processTransaction(new Transaction(amount));

	}

	/**
	 * @param amount
	 *            must be greater than zero
	 */
	public void withdraw(double amount) {
		if (amount < 0)
			throw new IllegalArgumentException("amount must be greater than zero");

		processTransaction(new Transaction(-amount));
	}

	public double computeInterestEarned() {
		return accountType.interest.apply(sumTransactions());
	}
	
	private boolean checkLastTransaction(int daysSince) {
		
		Calendar now = Calendar.getInstance();
		now.add(Calendar.DAY_OF_MONTH, -daysSince);
		Date limit = now.getTime();	
		
		return transactions.stream().anyMatch(t->t.getTransactionDate().after(limit));
		
	}
	
	private void computeInterestDaily() {
		
	}

	private double sumTransactions() {
		double amount = 0.0;
		for (Transaction t : transactions)
			amount += t.getAmount();
		return amount;

	}

	public String getStatement() {
		StringBuilder str = new StringBuilder();
		NumberFormat us = NumberFormat.getCurrencyInstance(Locale.US);

		str.append(getAccountType().name).append("\n");
		for (Transaction t : transactions) {
			str.append(String.format("%-15s%10s", t.getAmount() < 0.0 ? "withdrawal" : "deposit",
					us.format(Math.abs(t.getAmount()))));
			str.append("\n");
		}
		str.append("Total ").append(us.format(balance));

		return str.toString();
	}

	protected void processTransaction(Transaction t) {
		transactions.add(t);
		balance += t.getAmount();
	}

	public Type getAccountType() {
		return accountType;
	}

	enum Type {
		CHECKING("Checking Account", money -> money * 0.001),
		SAVINGS("Savings Account", money -> { return money <= 1000 ? money * 0.001 : 1 + (money - 1000) * 0.002; }), 
		
		
		MAXI_SAVINGS("Maxi Savings Account", money -> {		
			if (money <= 1000)
				return money * 0.02;
			if (money <= 2000)
				return 20 + (money - 1000) * 0.05;
			return 70 + (money - 2000) * 0.1;
		});

		public final String name;
		public final Function<? super Double, ? extends Double> interest;
		
		private Type(String name, Function<? super Double, ? extends Double> interest) {
			this.name = name;
			this.interest = interest;
		}
				

	}

}

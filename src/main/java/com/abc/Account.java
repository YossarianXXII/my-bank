package com.abc;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;

public class Account {

	enum Type {
		CHECKING("Checking Account", account -> {
			return account.getTotalBalance() * CHECKING_INTEREST/365;
		}), 
		SAVINGS("Savings Account", account -> {
			double money = account.getTotalBalance();			
			if (money <= 1000)
				return money * (SAVINGS_INTEREST_INITIAL/365.0);
			else
				return 1/365 + (money - 1000.0) * (SAVINGS_INTEREST_STANDARD/365.0); 
		}), 
		MAXI_SAVINGS("Maxi Savings Account", new Function<Account, Double>() {
			@Override
			public Double apply(Account account) {
				double money = account.getTotalBalance();
				if(account.checkLastWithdrawal(MAXI_DAYS_OF_LOW_INTEREST)) {
					return money * MAXI_INTEREST_RATE_WITHDRAWAL/365;
				}else
				return money * MAXI_INTEREST_RATE_NO_WITHDRAWAL/365;
				
			}
		});

		public final String name;
		public final Function<Account, Double> interest;
		
		private Type(String name, Function<Account, Double> interest) {
			this.name = name;
			this.interest = interest;
		}
				
		
		
	}
	public static double MAXI_INTEREST_RATE_NO_WITHDRAWAL = 0.05;
	public static double MAXI_INTEREST_RATE_WITHDRAWAL = 0.001;
	public static int MAXI_DAYS_OF_LOW_INTEREST = 10;
	
	public static double CHECKING_INTEREST = 0.001;
	public static double SAVINGS_INTEREST_INITIAL = 0.001;
	public static double SAVINGS_INTEREST_STANDARD = 0.002;
	private final Type accountType;
	public List<Transaction> transactions;
	private double balance;

	private double interestEarned = 0.0;
	
	
	public Account(Type accountType) {
		this.accountType = accountType;
		this.transactions = new ArrayList<Transaction>();
		this.balance = 0.0;
	}

	/**
	 * @return True if there was any transaction in past @param daysSince
	 */
	private boolean checkLastWithdrawal(int daysSince) {
		
		Calendar now = Calendar.getInstance();
		now.add(Calendar.DAY_OF_MONTH, -daysSince);
		Date limit = now.getTime();	
		
		return transactions.stream().anyMatch(t -> t.getTransactionDate().after(limit) && t.getAmount()<0 );
		
	}

	

	/**
	 * @return Interest earned counted from total balance(sum of transactions + interest)
	 * Method should be called once per day.
	 */
	public void computeInterestEarned() {
		this.interestEarned += accountType.interest.apply(this);		
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
	
	public Type getAccountType() {
		return accountType;
	}
	
	public double getInterestEarned() {
		return interestEarned;
	}

	/**
	 * @return Readable format of account transactions.
	 */
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
	
	public double getSumMoney() {
		return balance;
	}

	/**
	 * @return Sum of account balance(\u2211 transactions + earned interest)
	 */
	public double getTotalBalance() {
		return this.balance + this.interestEarned;
	}

	protected void processTransaction(Transaction t) {
		transactions.add(t);
		balance += t.getAmount();
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

}

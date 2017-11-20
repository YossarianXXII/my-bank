package com.abc;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Transaction {
    private final double amount;

    private Date transactionDate;

    public Transaction(double amount) {
        this.amount = amount;
        this.transactionDate = Calendar.getInstance().getTime();
    }
    
    public Transaction(double amount, Date date) {
    	this.amount = amount;
    	this.transactionDate = date;
    
    }

	/**
	 * @return Transaction value
	 */
	public double getAmount() {
		return amount;
	}

	/**
	 * @return Transaction date
	 */
	public Date getTransactionDate() {
		return transactionDate;
	}
	
	@Override
	public String toString() {
		StringBuilder s = new StringBuilder("");
		return s.append(new SimpleDateFormat("dd/MM/yyyy, Ka").format(transactionDate)).append(" " + amount).toString();
	}

}

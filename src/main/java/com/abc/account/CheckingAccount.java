package com.abc.account;

public class CheckingAccount extends Account {

	@Override
	public double calculateInterest() {
		// Checking account rate calculator
		System.out.println("Checking account interest");
		sumTransactions();
		
		return totalBalance * 0.001;
		
	}

}

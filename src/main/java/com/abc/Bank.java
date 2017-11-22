package com.abc;

import java.util.ArrayList;
import java.util.List;

public class Bank {

	private List<Customer> customers = new ArrayList<>();

	public void addCustomer(Customer customer) {
		customers.add(customer);
	}

	public String customerSummary() {
		StringBuilder summary = new StringBuilder("Customer Summary");
		for (Customer c : customers)
			summary.append("\n - " + c.getName() + " (" + Utils.pluralFormat(c.getNumberOfAccounts(), "account") + ")");
		return summary.toString();
	}



	public double totalInterestPaid() {
		return customers.stream().mapToDouble(e -> e.totalInterestEarned()).sum();
	}

}

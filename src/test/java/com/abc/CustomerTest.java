package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomerTest {
	
	 protected class DateAccount extends Account{

			public DateAccount(Type accountType) {
				super(accountType);
				// TODO Auto-generated constructor stub
			}
			
			public void deposit(double amount, Date date) {
				if(amount<0) throw new IllegalArgumentException();
				
				Transaction t = new Transaction(amount, date);
				processTransaction(t);
				
			}

			public void withdraw(double amount, Date date) {
				if(amount<0) throw new IllegalArgumentException();
				
				Transaction t = new Transaction(-amount, date);
				processTransaction(t);
				
			}
	    	
	    }

    @Test //Test customer statement generation
    public void testApp(){

        Account checkingAccount = new Account(Account.Type.CHECKING);
        Account savingsAccount = new Account(Account.Type.SAVINGS);

        Customer henry = new Customer("Henry");
        henry.openAccount(checkingAccount);
        henry.openAccount(savingsAccount);

        checkingAccount.deposit(100.0);
        savingsAccount.deposit(4000.0);
        savingsAccount.withdraw(200.0);

        assertEquals("Statement for Henry\n" +
                "\n" +
                "Checking Account\n" +
                "deposit           $100.00\n" +
                "Total $100.00\n" +
                "\n" +
                "Savings Account\n" +
                "deposit         $4,000.00\n" +
                "withdrawal        $200.00\n" +
                "Total $3,800.00\n" +
                "\n" +
                "Total In All Accounts $3,900.00", henry.getStatement());
    }

    @Test
    public void testOneAccount(){
        Customer oscar = new Customer("Oscar");
        oscar.openAccount(new Account(Account.Type.SAVINGS));
        assertEquals(1, oscar.getNumberOfAccounts());
    }

    @Test
    public void testTwoAccount(){
        Customer oscar = new Customer("Oscar");
        oscar.openAccount(new Account(Account.Type.SAVINGS));
        oscar.openAccount(new Account(Account.Type.CHECKING));
        assertEquals(2, oscar.getNumberOfAccounts());
    }

    @Test
    public void testThreeAcounts() {
        Customer oscar = new Customer("Oscar");
        oscar.openAccount(new Account(Account.Type.SAVINGS));
        oscar.openAccount(new Account(Account.Type.CHECKING));
        oscar.openAccount(new Account(Account.Type.MAXI_SAVINGS));
        assertEquals(3, oscar.getNumberOfAccounts());
    }
    
   
    
    @Test
    public void timedMaxiSavings() throws ParseException {
    	Customer oscar = new Customer("Oscar");
    	DateAccount maxi = new DateAccount(Account.Type.MAXI_SAVINGS);
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    	
    	Date deposit1date = format.parse("2017-11-01");
    	Date deposit2date = format.parse("2017-11-05");
    	Date withdrawal1date = format.parse("2017-11-06");
    	Date withdrawal2date = format.parse("2017-11-19");
    	
    	maxi.deposit(1000.0, deposit1date);
    	maxi.deposit(500.0, deposit2date);
    	maxi.withdraw(500.0, withdrawal1date);
    	
    	
    	
    	oscar.openAccount(maxi);
    	
    	
    	
    	
    	
    }
    
    
    
    
    
    
}

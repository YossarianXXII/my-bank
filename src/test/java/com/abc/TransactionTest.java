package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TransactionTest {
    @Test
    public void transaction() {
        Transaction t = new Transaction(5);
        assertTrue(t instanceof Transaction);
    }
    
    @Test
    public void transactionDate() {
    	Date date = Calendar.getInstance().getTime();
    		
    	Transaction t = new Transaction(100.0, date);
    	
    	assertEquals(new SimpleDateFormat("dd/MM/yyyy, Ka").format(date) + " 100.0", t.toString());
    	
    	
    }
}

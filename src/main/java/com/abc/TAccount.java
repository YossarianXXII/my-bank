package com.abc;

import java.util.ArrayList;
import java.util.List;

import com.abc.account.Account;

public class TAccount implements Account {

    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;
    
    
    private final AccountType accountType;
    public List<Transaction> transactions;

    public TAccount(AccountType accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
    }

    /* (non-Javadoc)
	 * @see com.abc.Account#deposit(double)
	 */
    @Override
	public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount));
        }
    }

/* (non-Javadoc)
 * @see com.abc.Account#withdraw(double)
 */
@Override
public void withdraw(double amount) {
    if (amount <= 0) {
        throw new IllegalArgumentException("amount must be greater than zero");
    } else {
        transactions.add(new Transaction(-amount));
    }
}

    /* (non-Javadoc)
	 * @see com.abc.Account#interestEarned()
	 */
    @Override
	public double interestEarned() {
        double amount = sumTransactions();
        switch(accountType){
            case SAVINGS:
                if (amount <= 1000)
                    return amount * 0.001;
                else
                    return 1 + (amount-1000) * 0.002;
//            case SUPER_SAVINGS:
//                if (amount <= 4000)
//                    return 20;
            case MAXI_SAVINGS:
                if (amount <= 1000)
                    return amount * 0.02;
                if (amount <= 2000)
                    return 20 + (amount-1000) * 0.05;
                return 70 + (amount-2000) * 0.1;
            default:
                return amount * 0.001;
        }
    }

    /* (non-Javadoc)
	 * @see com.abc.Account#sumTransactions()
	 */
    @Override
	public double sumTransactions() {
       return checkIfTransactionsExist(true);
    }

    private double checkIfTransactionsExist(boolean checkAll) {
        double amount = 0.0;
        for (Transaction t: transactions)
            amount += t.amount;
        return amount;
    }

    public AccountType getAccountType() {
        return accountType;
    }

}

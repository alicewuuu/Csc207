package main.accounts;

import main.system.Date;

/**
 * An abstract class representing transaction.
 */
public abstract class Transaction implements java.io.Serializable {
    private Date date;
    private Account transferInAccount;
    private Account transferOutAccount;
    private Double amount;

    /**
     * record of a transaction between two accounts
     *
     * @param date            the date of the transaction
     * @param transferOutAcct account that transfer money out
     * @param transferInAcct  account that receive money
     * @param amount          amount of money transfer
     */
    Transaction(Date date, Account transferOutAcct, Account transferInAcct, Double amount) {
        this.date = date;
        this.transferInAccount = transferInAcct;
        this.transferOutAccount = transferOutAcct;
        this.amount = amount;
    }

    /**
     * record of a pay bill/ withdraw/ deposit transaction
     *
     * @param num    1 if money is transferred in, 0 if money is transferred out
     * @param date   date of the transaction
     * @param acc    account that transfer money
     * @param amount amount of money pay/withdraw/deposit
     */
    Transaction(int num, Date date, Account acc, Double amount) {
        this.date = date;
        this.amount = amount;
        if (num == 1) {
            this.transferInAccount = acc;
        } else {
            this.transferOutAccount = acc;
        }
    }


    // ========================== Getter ===========================

    /**
     * Gets date of the transaction gets proceed.
     *
     * @return the transaction date.
     * @see Date
     */
    public Date getDate() {
        return date;
    }

    /**
     * Gets the account that transfer money in.
     *
     * @return the transfer in account
     * @see Account
     */
    Account getTransferInAccount() {
        return this.transferInAccount;
    }

    /**
     * Gets the account that transfer money out.
     *
     * @return the transfer out account
     * @see Account
     */
    Account getTransferOutAccount() {
        return this.transferOutAccount;
    }

    /**
     * Gets the amount of the transaction
     *
     * @return transaction amount
     */
    public Double getAmount() {
        return amount;
    }

    // ========================== Method ===========================

    /**
     * Represents transaction by string.
     *
     * @return String representation of the transaction
     */
    public abstract String toString();

    @Override
    public boolean equals(Object t) {
        boolean trans = t instanceof Transaction;
        if (!trans) {
            return false;
        } else {
            boolean date = this.date == ((Transaction) t).date;
            boolean in = this.transferInAccount == ((Transaction) t).transferInAccount;
            boolean out = this.transferOutAccount == ((Transaction) t).transferOutAccount;
            boolean amount = this.amount.equals(((Transaction) t).amount);
            return date && in && out && amount;
        }

    }
}

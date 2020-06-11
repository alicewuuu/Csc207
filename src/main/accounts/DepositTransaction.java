package main.accounts;

import main.system.Date;
import main.system.SerializationProcessor;

/**
 * A class representing a record of deposit transaction.
 * This is a subclass of {@code Transaction} and it is {@code Undoable}
 *
 * @see Transaction
 * @see Undoable
 */
public class DepositTransaction extends Transaction implements Undoable {

//    ========================== Constructor ===========================

    /**
     * Initializes a deposit transaction with a given date, the account making deposit and the amount
     *
     * @param date           date of the transaction made
     * @param transferInAcct account that makes this deposit
     * @param amount         the deposit amount
     */
    public DepositTransaction(Date date, Account transferInAcct, Double amount) {
        super(1, date, transferInAcct, amount);
    }

//    ========================== Public Method ===========================

    /**
     * Undoes this deposit transaction.
     * Withdraws amount of money from the account
     * Creates a new withdrawTransaction and set it as last transaction
     *
     * @param newDate the date of withdraw transaction has been made.
     * @return true if it is undone successfully.
     */
    @Override
    public boolean undoTransaction(Date newDate) {
        Account outAcc = getTransferInAccount();
        outAcc.changeBalance(-getAmount());

        outAcc.deleteTransaction(this);

        SerializationProcessor processor = new SerializationProcessor();
        processor.storeAccount(outAcc);
        return true;
    }

    /**
     * Represent the deposit transaction in {@code String}
     * It shows the deposit amount, account and date making this deposit.
     * String format: "depositCash [amount] into [accId] on [date]."
     *
     * @return String representation of this deposit transaction
     */
    @Override
    public String toString() {
        return String.format("Deposit Transaction %.2f into %s on %s", getAmount(),
                getTransferInAccount().getId(), getDate());
    }

}
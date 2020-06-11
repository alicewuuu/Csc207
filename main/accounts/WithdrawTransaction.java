package main.accounts;

import main.system.Date;
import main.system.SerializationProcessor;

/**
 * A class representing withdraw transaction.
 * This is a subclass of {@code Transaction}
 * This type of transaction is {@code Undoable}
 *
 * @see Transaction
 * @see Undoable
 */
public class WithdrawTransaction extends Transaction implements Undoable {

    /**
     * Initializes the withdraw transaction with user's account, withdraw amount and date of the withdraw.
     *
     * @param date            date money gets withdrawn.
     * @param transferOutAcct the account money gets withdrawn from.
     * @param amount          amount of the withdraw transaction.
     */
    WithdrawTransaction(Date date, Account transferOutAcct, Double amount) {
        super(0, date, transferOutAcct, amount);
    }


//    ========================== Method ===========================

    /**
     * deposit amount of money back to the account
     * create a new depositTransaction and set it as last transaction
     *
     * @param newD date of new undo transaction
     * @return true if it gets undone successfully
     */
    @Override
    public boolean undoTransaction(Date newD) {
        Account inAcc = getTransferOutAccount();
        inAcc.changeBalance(getAmount());
        inAcc.deleteTransaction(this);
        SerializationProcessor processor = new SerializationProcessor();
        processor.storeAccount(inAcc);
        return true;
    }

    /**
     * Represents the withdraw transaction by string in the format "withdrawCash [amount] from [account id] on [date]"
     *
     * @return String representation of this transaction
     */
    @Override
    public String toString() {
        return String.format("Withdraw Transaction of amount $%.2f on %s", getAmount(),
                getDate());
        /*
        return String.format("Withdraw Transaction %d from %s on %s", getAmount(),
                getTransferInAccount().getId(), getDate());*/
    }
}

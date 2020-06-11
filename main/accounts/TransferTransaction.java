package main.accounts;

import main.system.Date;
import main.system.SerializationProcessor;

/**
 * A class representing transfer transaction.
 * This is a subclass of {@code Transaction}
 * This transaction is {@code Undoable}.
 *
 * @see Transaction
 * @see Undoable
 */
public class TransferTransaction extends Transaction implements Undoable {

//    ========================== Constructor ===========================

    /**
     * Initializes the transfer transaction with the transfer in and out account, a certain amount and its date.
     *
     * @param date            date this transaction is proceed
     * @param transferOutAcct account transfer money out
     * @param transferInAcct  account transfer money in
     * @param amount          amount of the transaction
     */
    TransferTransaction(Date date, Account transferOutAcct,
                        Account transferInAcct, Double amount) {
        super(date, transferOutAcct, transferInAcct, amount);
    }

//    ========================== Public Method ===========================

    /**
     * Undo the transaction.
     *
     * @param newD new date of the undo transaction gets proceed.
     * @return true if the transaction is undone successfully.
     */
    @Override
    public boolean undoTransaction(Date newD) {
        Account inAcc = getTransferOutAccount();
        Account outAcc = getTransferInAccount();
        boolean valid = outAcc.isValidTransfer(getAmount());

        outAcc.changeBalance(-getAmount());
        inAcc.transferIn(getAmount());

        if (!valid) {
            outAcc.setStatus(true);
        }
        inAcc.deleteTransaction(this);
        outAcc.deleteTransaction(this);

        SerializationProcessor processor = new SerializationProcessor();
        processor.storeAccount(inAcc);
        processor.storeAccount(outAcc);
        return true;
    }

    /**
     * Represents the transaction in String
     * Form: "[Transfer] [amount] from [accOutId] to [accInId] on [date]"
     *
     * @return string representation of the transfer transaction.
     */
    @Override
    public String toString() {
        /*
        String result = String.format("Transfer Transaction %d from %s to %s on %d", getAmount(),
                getTransferOutAccount().getId(), getTransferInAccount(), getDate());*/
        return String.format("Transfer Transaction of amount $%.2f on %s", getAmount(), getDate());
    }
}

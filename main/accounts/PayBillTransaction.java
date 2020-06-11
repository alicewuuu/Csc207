package main.accounts;

import main.system.Date;

/**
 * A class representing pay bill transaction.
 * This is a subclass of {@code Transaction}
 *
 * @see Transaction
 */
public class PayBillTransaction extends Transaction {

    /**
     * payee username of the pay bill transaction
     */
    private String payee;

//    ========================== Constructor ===========================

    /**
     * Initializes the pay bill transaction with the basic information.
     *
     * @param date            date this transaction gets proceed.
     * @param transferOutAcct payer account of the pay bill transaction
     * @param payee           username of payee in this pay bill transaction.
     * @param amount          amount of the transaction
     */
    PayBillTransaction(Date date, Account transferOutAcct, String payee, Double amount) {
        super(0, date, transferOutAcct, amount);
        this.payee = payee;
    }

//    ========================== Public Method ===========================

    /**
     * Represent the deposit transaction in {@code String}
     * It shows the amount, payer's and payee's account and date making this pay bill transaction.
     * String representation: "payBill [amount] from [accOutId] to [payee] on [date]."
     *
     * @return String representation of this pay bill transaction
     */
    @Override
    public String toString() {
        return String.format("PayBill Transaction of amount $%.2f to %s on %s", getAmount(),
                payee, getDate());
    }

}

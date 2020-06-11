package main.accounts;

import main.system.Date;

/**
 * A class representing the line of credit account.
 * This is a subclass of {@code DebtAccount}
 * This account is {@code Transferable}
 *
 * @see DebtAccount
 * @see Transferable
 */
public class LineOfCreditAccount extends DebtAccount implements Transferable {

//    ========================== Constructor ===========================

    /**
     * Initializes the line of credit account with the account id, user who owns this account and the date this
     * account gets created
     *
     * @param id          account id
     * @param owner       owner of the account
     * @param dateCreated date of the account get created.
     */
    public LineOfCreditAccount(String id, User owner, Date dateCreated) {
        super(id, owner, dateCreated, 10000);
    }

//    ========================== Public Method ===========================

    /**
     * Transfers money to the given account with a certain amount.
     *
     * @param transferInAcct account that will be transfer to.
     * @param amount         amount of the transaction
     * @param date           date of the transfer gets processed
     * @return true if the process is successful
     */
    @Override
    public boolean transferOut(Account transferInAcct, double amount, Date date) {
        if (isValidTransfer(amount) && !(transferInAcct instanceof CreditCardAccount)) {
            Transaction t = new TransferTransaction(date, this, transferInAcct, amount);

            changeBalance(-amount);
            setLastTransaction(t);
            addTransaction(t);

            transferInAcct.transferIn(t);
            return true;
        }
        return false;
    }

}

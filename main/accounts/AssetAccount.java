package main.accounts;

import main.system.Date;

/**
 * A class representing Asset account
 * This is a subclass of Account
 *
 * @see Account
 */
public abstract class AssetAccount extends Account implements Transferable {

//    ========================== Constructor ===========================

    /**
     * Initializes the asset account with the given if and it's owner
     *
     * @param id          id of this asset account
     * @param owner       the user who owns this account
     * @param dateCreated date that this account gets created
     */
    AssetAccount(String id, User owner, Date dateCreated) {
        super(id, owner, dateCreated);
    }


//    ========================== Public Method ===========================

    /**
     * changes balance of the asset balance
     *
     * @param amount the amount to change to
     */
    @Override
    public void changeBalance(double amount) {
        this.balance += amount;
    }

    /**
     * increase the account balance by interest rate of 2%
     *
     * @param date date that the balance increase
     */
    @Override
    public void update(Date date) {
        if (balance < 0) {
            payBill("BANK's interest account", 0.02 * Math.abs(balance), date);
        }
    }

    /**
     * transfers to another account with an amount if withdrawal of given value is valid
     *
     * @param transferInAcct the account to transfer money to
     * @param amount         the amount of the transfer
     * @return true if the process is successful
     */
    @Override
    public boolean transferOut(Account transferInAcct, double amount, Date date) {
        if (isValidTransfer(amount)) {
            changeBalance(-amount);
            Transaction t = new TransferTransaction(date, this, transferInAcct, amount);
            setLastTransaction(t);
            addTransaction(t);
            transferInAcct.transferIn(t);
            return true;
        }
        return false;
    }

    /**
     * Deposits money to this account.
     *
     * @param amount amount of money to deposit to this account
     * @param date   date when the deposit is made
     */
    public void deposit(double amount, Date date) {
        changeBalance(amount);
        Transaction t = new DepositTransaction(date, this, amount);
        setLastTransaction(t);
        addTransaction(t);
    }


//    ========================== Abstract ===========================

    /**
     * Checks if the transfer with given amount valid
     *
     * @param amount amount of the transfer
     */
    @Override
    public abstract boolean isValidTransfer(double amount);
}

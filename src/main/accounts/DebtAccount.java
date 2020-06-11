package main.accounts;

import main.system.Date;

/**
 * A class represents the debt accounts.
 * This is a subclass of {@code Account}
 *
 * @see Account
 */
public abstract class DebtAccount extends Account {

    private int limit;

//    ========================== Constructor ===========================

    /**
     * Initializes debt account with the account id, its owner and the date it gets created
     *
     * @param id          the account id
     * @param owner       the user who owns this account
     * @param dateCreated the date this account gets created
     * @see User
     * @see Date
     */
    DebtAccount(String id, User owner, Date dateCreated, int limit) {
        super(id, owner, dateCreated);
        this.limit = limit;
    }

//    ========================== Public Method ===========================

    /**
     * Checks if the transfer is valid.
     * A transfer is considered to be valid if
     *
     * @param amount the amount to be checked
     * @return true if the transaction is considered to be valid.
     */
    public boolean isValidTransfer(double amount) {
        boolean positive = amount >= 0;
        boolean max = this.balance + amount <= limit;
        boolean freeze = !isFreeze();
        return positive && max && freeze;
    }

    /**
     * Changes the account balance with the given amount.
     *
     * @param amount amount of the change
     */
    @Override
    public void changeBalance(double amount) {
        this.balance -= amount;
    }

    /**
     * if the account is owing money, increase the amount of owing by 2%
     *
     * @param date the date that the balance increased
     */
    @Override
    public void update(Date date) {
        if (balance > 0) {
            payBill("BANK's interest account", 0.02 * balance, date);
        }
    }

}

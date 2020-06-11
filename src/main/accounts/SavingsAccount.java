package main.accounts;

import main.system.Date;

/**
 * A class representing the savings account.
 * This is a subclass of {@code AssetAccount}
 * This account type is {@code Transferable}
 *
 * @see AssetAccount
 * @see Transferable
 */
public class SavingsAccount extends AssetAccount {

//    ========================== Constructor ===========================

    /**
     * Initializes saving account with account id, user who owns this account and date it gets created
     *
     * @param id    account id
     * @param owner account owner
     * @param date  date this account gets created
     * @see User
     * @see Date
     */
    public SavingsAccount(String id, User owner, Date date) {
        super(id, owner, date);
    }

//    ========================== Public Method ===========================

    /**
     * Checks if the transfer with given amount is valid.
     * Transfer is considered to be valid if the amount is smaller than the balance.
     *
     * @param amount amount of the transfer
     * @return true if transfer is valid.
     */
    @Override
    public boolean isValidTransfer(double amount) {
        boolean pAmount = (amount >= 0);
        boolean pBalance = (getBalance() >= 0);
        boolean valid = (amount <= getBalance());
        return pAmount && pBalance && valid;
    }

    /**
     * increase the balance by 0.1%
     *
     * @param date date that the balance increase
     */
    @Override
    public void update(Date date) {
        if (getBalance() >= 0) deposit(balance * 0.001, date);
    }

}

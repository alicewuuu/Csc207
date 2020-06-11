package main.accounts;

import main.system.Date;

/**
 * A class representing chequing accounts
 * This is a subclass of {@code AssetAccount}
 *
 * @see AssetAccount
 */
public class ChequingAccount extends AssetAccount {

//    ========================== Constructor ===========================

    /**
     * Initializes the chequing account with given id, owner and creation date
     *
     * @see User
     * @see Date
     */
    public ChequingAccount(String id, User owner, Date createdDate) {
        super(id, owner, createdDate);
    }

//    ========================== Method ===========================

    /**
     * Checks if the transfer from this chequing account valid.
     * A transfer is considered to be valid if the account's balance is non negative and have at least 100 more than
     * the transfer amount.
     *
     * @param amount amount of the transfer
     * @return true if the transfer is considered to be valid
     */
    public boolean isValidTransfer(double amount) {
        boolean amt = amount >= 0;
        boolean bal = false;
        if (amount <= this.balance + 100 && this.balance > 0) {
            bal = true;
        }
        return amt && bal && !isFreeze();
    }

}

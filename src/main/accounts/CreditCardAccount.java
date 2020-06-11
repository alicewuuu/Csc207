package main.accounts;

import main.system.Date;

/**
 * A class representing a credit card account.
 * This is a subclass of {@code DebtAccount}
 *
 * @see DebtAccount
 */
public class CreditCardAccount extends DebtAccount {

//    ========================== Constructor ===========================

    /**
     * Initialize the credit card account with the account id, its owner and the date it gets created.
     *
     * @param id          account id.
     * @param owner       the user who ows this credit card account
     * @param createdDate the date this account gets created
     * @see User
     * @see Date
     */
    public CreditCardAccount(String id, User owner, Date createdDate) {
        super(id, owner, createdDate, 2000);
    }

}

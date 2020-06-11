package main.accounts;

import main.system.Date;

/**
 * An interface Transferable has the following method
 */
public interface Transferable {
    /**
     * Transfers money out to the given account with certain amount of money.
     *
     * @param TransferInAccount account to transfer money in.
     * @param amount            amount of the transfer
     * @param date              date the transfer gets proceed.
     * @return true if transfer is successfully.
     */
    boolean transferOut(Account TransferInAccount, double amount, Date date);
}

package main.accounts;

import main.system.Date;

/**
 * A interface Undoable that has the following methods
 */
public interface Undoable {
    /**
     * undo the transaction and set this undoTransaction as the last Transaction of the account
     *
     * @param newDate the date when the transaction is undone.
     * @see Date
     */
    boolean undoTransaction(Date newDate);

}

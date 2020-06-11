package main.accounts;

import main.managers.FileManager;
import main.system.Date;

import java.util.ArrayList;
import java.util.List;

/**
 * A class representing accounts.
 */
public abstract class Account implements java.io.Serializable {
    /**
     * the last transaction on this account
     */
    private Undoable lastTransaction;

    /**
     * all the transaction on this account
     */
    private List<Transaction> allTrans;

    /**
     * user who owns this account
     */
    private final User owner;

    /**
     * user who co-owns this account
     */
    private User jointUser = null;

    /**
     * account balance
     */
    double balance;

    /**
     * the date this account get created
     */
    private final Date dateCreated;

    /**
     * account id
     */
    private final String id;

    /**
     * if this account is frozen
     */
    private boolean freeze;

//    ========================== Constructor ===========================

    /**
     * Initialize account with given id, owner and the creation date.
     *
     * @param id          account id
     * @param owner       user who owns this account
     * @param dateCreated creation date
     */
    Account(String id, User owner, Date dateCreated) {
        this.id = id;
        this.owner = owner;
        this.balance = 0;
        this.lastTransaction = null;
        this.dateCreated = dateCreated;
        this.allTrans = new ArrayList<>();
        this.freeze = false;
    }

    // ========================== Getter ===========================

    /**
     * get all transactions of this account
     */
    public List getTransaction() {
        return this.allTrans;
    }

    /**
     * Gets account id
     *
     * @return account id
     */
    public String getId() {
        return id;
    }

    /**
     * Gets user who owns this account.
     *
     * @return a {@code User} object indicate the owner.
     */
    public User getUser() {
        return this.owner;
    }

    /**
     * gets the second user of this account
     *
     * @return join user
     */
    public User getJointUser() {
        return this.jointUser;
    }

    /**
     * Gets balance on this account
     *
     * @return account balance
     */
    public double getBalance() {
        return this.balance;
    }

    /**
     * get status of this account
     */
    public boolean isFreeze() {
        return this.freeze;
    }

    // ========================== Setter ===========================

    /**
     * set the last transaction of this account.
     *
     * @param lastTransaction last transaction...
     */
    void setLastTransaction(Transaction lastTransaction) {
        this.lastTransaction = (Undoable) lastTransaction;
    }

    /**
     * set the given user as the second user of this account
     *
     * @param u user that wanted to add as second user
     * @return true if the given user is set as join user, false if this account has joint user already
     */
    public boolean setJointUser(User u) {
        if (this.jointUser == null) {
            this.jointUser = u;
            return true;
        }
        return false;
    }

    /**
     * freeze or unfreeze the account
     *
     * @param status true to freeze the account, false to unfreeze the account
     */
    public void setStatus(boolean status) {
        this.freeze = status;
    }


    // ========================== Method ===========================

    /**
     * delete the given transaction from the transaction list of the
     *
     * @param t transaction to be deleted
     */
    void deleteTransaction(Transaction t) {
        allTrans.remove(t);
    }

    /**
     * handle a transfer-in transaction
     *
     * @param t transfer-in transaction information
     */
    void transferIn(Transaction t) {
        changeBalance(t.getAmount());
        setLastTransaction(t);
        addTransaction(t);
        if (isFreeze() && getBalance() >= 0) {
            setStatus(false);
        }
    }

    /**
     * handle a transfer-in transaction due to undo
     *
     * @param amount amount of money to transfer in
     */
    void transferIn(double amount) {
        changeBalance(amount);
        if (isFreeze() && getBalance() >= 0) {
            setStatus(false);
        }
    }

//    ========================== Public Method ===========================

    /**
     * add new record of transaction of this account
     *
     * @param t transaction to add
     */
    void addTransaction(Transaction t) {
        this.allTrans.add(t);
    }

    /**
     * Undo the last transaction of this account.
     *
     * @param date date of the transaction.
     * @return true if the undo process successful.
     */
    public boolean undoTransaction(Date date) {
        boolean result = this.lastTransaction.undoTransaction(date);
        allTrans.remove(allTrans.size() - 1);
        return result;
    }

    /**
     * get the most recent 3 days' transaction of this account of given date
     *
     * @param d given date
     * @return list of transactions of this account
     */
    public List<Transaction> getRecentTransaction(Date d) {
        Date date = d;
        List<Date> dateList = new ArrayList<>();
        dateList.add(date);
        for (int i = 1; i < 3; i++) {
            Date add = date.subtract();
            date = add;
            dateList.add(add);
        }
        List<Transaction> result = new ArrayList<>();
        for (Transaction t : this.allTrans) {
            boolean in = dateList.contains(t.getDate());
            if (in) {
                result.add(t);
            }
        }
        return result;
    }

    /**
     * withdraw cash from this asset account with the given amount if it's valid.
     *
     * @param amount the amount of cash to be withdrawn
     * @param date   date that the withdraw transaction has been made
     * @return true if the withdrawal successful
     */
    public boolean withdrawCash(double amount, Date date) {
        if (isValidTransfer(amount)) {
            changeBalance(-amount);
            Transaction t = new WithdrawTransaction(date, this, amount);
            setLastTransaction(t);
            addTransaction(t);
            return true;
        }
        return false;
    }

    /**
     * pays bill to payee with the given amount if withdrawal with the given amount valid.
     *
     * @param payee  payee of the transaction
     * @param amount the amount of pay bill transaction
     * @param date   date of the pay bill transaction have been made.
     * @return true if the process is successful
     */
    public boolean payBill(String payee, double amount, Date date) {
        if (isValidTransfer(amount)) {
            changeBalance(-amount);
            PayBillTransaction t = new PayBillTransaction(date, this, payee, amount);
            FileManager fileManager = new FileManager();
            fileManager.newPayBill(t);
            addTransaction(t);
            return true;
        }
        return false;
    }

    /**
     * Represents the account in string.
     * It shows all information of the account, i.e., id, balance etc.
     *
     * @return string representation of the account.
     */
    public String toStringAccountType() {
        String result;
        result = String.format("%s - %s", this.getClass().getSimpleName(), this.getId());
        return result;
    }

    /**
     * Represents the account in string.
     * It shows all information of the account, i.e., id, type, balance etc.
     *
     * @return string representation of the account.
     */
    @Override
    public String toString() {
        String t = "none";
        if (lastTransaction != null) t = lastTransaction.toString();
        return "Your account with id " + id + "'s info are shown below:\n" +
                "     Owner id: " + owner.getLogin() + "\n" +
                "     Account type: " + this.getClass().getSimpleName() + "\n" +
                "     Current balance: $" + balance + "\n" +
                "     Created date: " + dateCreated.toString() + "\n" +
                "     The most recent transaction: " + t;
    }

    public String toStringForShow() {
        String jointID;
        if (jointUser != null) {
            jointID = jointUser.getLogin();
        } else {
            jointID = "none";
        }
        return "Your account info are shown below:\n" +
                "     Owner id: " + owner.getLogin() + "\n" +
                "     Joint user id: " + jointID + "\n" +
                "     Current balance: $" + balance + "\n" +
                "     Created date: " + dateCreated.toString() + "\n";
    }

    /**
     * Checks if the given object is considered to be "equal" to this one.
     * Two accounts are considered to be equal if they are the same object or have same id
     *
     * @param obj the reference object with which to compare.
     * @return true if two accounts are considered to be equal
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else {
            return obj instanceof Account && ((Account) obj).id.equals(getId());
        }
    }

    // ========================== Abstract Method ===========================

    /**
     * check the transfer with given amount is valid
     *
     * @param amount the amount to be checked
     */
    public abstract boolean isValidTransfer(double amount);

    /**
     * Change the balance of the account
     *
     * @param amount amount of the change
     */
    public abstract void changeBalance(double amount);

    /**
     * update the account balance on given date
     *
     * @param date date that this account is updated
     */
    public abstract void update(Date date);

}
package main.system;

import javafx.util.Pair;
import main.accounts.Account;
import main.accounts.Undoable;
import main.accounts.User;
import main.managers.AccountManager;

import java.util.ArrayList;
import java.util.List;

import main.managers.CashManager;

/**
 * A class holding basic information of the current ATM machine system.
 */
public class SystemInfo implements java.io.Serializable {
    /**
     * Current cash manager corresponding to this ATM machine
     */
    private CashManager cashManager;

    /**
     * Current date
     */
    private Date date;

    /**
     * A list of supported account types currently
     */
    private List<String> accountTypes;

    private AccountManager accountManager;

    /**
     * list of new user request
     */
    private List<String> pendingUsers;

    /**
     * list of transaction request to undo
     */
    private List<Undoable> pendingUndo;

    /**
     * list of account request to add
     */
    private List<Pair<String, String>> pendingAccs;

    /**
     * list of request of adding a second user to account
     */
    private List<Pair<Account, User>> pendingJointUser;

    /**
     * number of request to be a new user
     */
    private int numUser;

    /**
     * number of request to add a new account
     */
    private int numAccount;

    /**
     * number of request to add a joint user
     */
    private int numJoint;

    /**
     * number of request to undo a transaction
     */
    private int numUndo;

    private List<String> users;

//    ========================== Constructor ===========================

    /**
     * Loads basic information for this ATM machine.
     *
     * @param cashManager    a cash manager responds to current ATM machine.
     * @param accountTypes   list of account types supported currently.
     * @param accountManager manager manage all accounts of the system
     * @see CashManager
     */
    public SystemInfo(CashManager cashManager, List<String> accountTypes, AccountManager accountManager) {
        this.cashManager = cashManager;
        this.date = null;
        this.accountTypes = accountTypes;
        this.accountManager = accountManager;
        this.pendingUsers = new ArrayList<>();
        this.pendingAccs = new ArrayList<>();
        this.pendingJointUser = new ArrayList<>();
        this.pendingUndo = new ArrayList<>();
        this.users = new ArrayList<>();
    }

//    ========================== Getter ===========================

    public List<String> getUsers() {
        return users;
    }

    /**
     * Gets the cash manager responsible for this ATM.
     */
    public CashManager getCashManager() {
        return cashManager;
    }

    /**
     * get the account manager for this ATM
     */
    public AccountManager getAccountManager() {
        return accountManager;
    }

    /**
     * Gets the current date.
     */
    public Date getDate() {
        return date;
    }

    /**
     * get number of requests of creating new account
     */
    public int getNumAccount() {
        return numAccount;
    }

    /**
     * get number of requests to add a joint user
     */
    public int getNumJoint() {
        return numJoint;
    }

    /**
     * get number of requests to undo a transaction
     */
    public int getNumUndo() {
        return numUndo;
    }

    /**
     * get number of requests to be a new user
     */
    public int getNumUser() {
        return numUser;
    }

    /**
     * get list all supported account type on this ATM machine.
     */
    public List<String> getAccountTypes() {
        return accountTypes;
    }

    /**
     * Gets the first id that request to be the new user.
     */
    public String getPendingUser() {
        if (pendingUsers.isEmpty()) {
            return null;
        } else {
            String current = pendingUsers.get(0);
            pendingUsers.remove(0);
            numUser--;
            return current;
        }
    }

    /**
     * Gets requests of creating new account.
     *
     * @return a 2D array containing account request information.
     */
    public Pair<String, String> getPendingAccs() {
        if (pendingAccs.isEmpty()) {
            return null;
        } else {
            Pair<String, String> current = pendingAccs.get(0);
            pendingAccs.remove(0);
            numAccount--;
            return current;
        }
    }

    /**
     * Gets the first request that asks to add a joint user
     *
     * @return a 2D array containing account id and user id that want to add
     */
    public Pair<Account, User> getPendingJointUser() {
        if (pendingJointUser.isEmpty()) {
            return null;
        } else {
            Pair<Account, User> current = pendingJointUser.get(0);
            pendingJointUser.remove(0);
            numJoint--;
            return current;
        }
    }

    /**
     * get the first transaction request to undo
     *
     * @return an undoable transaction
     */
    public Undoable getPendingUndo() {
        if (pendingUndo.isEmpty()) {
            return null;
        } else {
            Undoable current = pendingUndo.get(0);
            pendingUndo.remove(0);
            numUndo--;
            return current;
        }
    }

//    ========================== Setter ===========================

    public void addUser(String login) {
        users.add(login);
    }

    /**
     * Set date of the ATM machine system.
     *
     * @param date a new date.
     * @see Date
     */
    public void setDate(Date date) {
        this.date = date;
    }

//    ========================== public method ===========================

    /**
     * add a new id to PendingUser-list
     *
     * @param id id that the user use to login
     */
    public void addPendingUser(String id) {
        pendingUsers.add(id);
        numUser++;
    }

    /**
     * Adds a new request of new account creation for a user in the queue.
     *
     * @param type  account type that is requested to be created.
     * @param login login of user who submitted the request.
     */
    public void addPendingAcc(String type, String login) {
        Pair<String, String> pair = new Pair<>(type, login);
        pendingAccs.add(pair);
        numAccount++;
    }

    /**
     * Check did the user w/ given login request for a specific account type
     * This method is applied when checking request for some limited account types, gambling account for example.
     *
     * @param type  account type user trying to apply for.
     * @param login username of the user.
     * @return true if the user have applied for this type of account already.
     */
    public boolean isPendingAcc(String type, String login) {
        Pair<String, String> pair = new Pair<>(type, login);
        return pendingAccs.contains(pair);
    }

    /**
     * add a new request of joint user to the request list
     *
     * @param account   account that request to add a joint user
     * @param jointUser joint user's id
     */
    public void addPendingJoint(Account account, User jointUser) {
        Pair<Account, User> pair = new Pair<>(account, jointUser);
        pendingJointUser.add(pair);
        numJoint++;
    }

    /**
     * add a new request of undoing a transaction
     *
     * @param undoable a transaction that is undoable
     */
    public void addPendingUndo(Undoable undoable) {
        pendingUndo.add(undoable);
        numUndo++;
    }


}

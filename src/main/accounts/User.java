package main.accounts;

import main.system.LoginInfo;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.io.Serializable;
import java.util.List;

/**
 * A class representing the banking users.
 */
public class User implements Serializable {

// ========================== Variables ===========================
    /**
     * Login information of the user (username and password)
     */
    private LoginInfo info;

    /**
     * List of accounts this user owns.
     */
    private List<Account> accountList;


// ========================== Constructors ===========================

    /**
     * Initialize this user with the given login information.
     * By default, a new user does not own any account.
     *
     * @param info Login information of this user.
     * @see LoginInfo
     * @see Account
     */
    public User(LoginInfo info) {
        this.info = info;
        this.accountList = new ArrayList<>();
    }

// ========================== Get And Set ===========================

    /**
     * Gets the username of this user.
     *
     * @return a string representing current username.
     */
    public String getLogin() {
        return info.getLogin();
    }

    /**
     * Gets the password of this user.
     *
     * @return a string representing current password.
     */
    public String getPassword() {
        return info.getPassword();
    }

    /**
     * Lists accounts that this user owns.
     *
     * @return an {@code List} contains all accounts of this user.
     */
    public List<Account> getAccountList() {
        return accountList;
    }


    /**
     * Sets a new password for this user.
     *
     * @param password A new password for this user.
     */
    public void setPassword(String password) {
        info.setPassword(password);
    }

// ========================== Method ===========================

    /**
     * A {@code String} representation for this user.
     * It shows login information and the accounts of this user.
     *
     * @return a String contains user's information.
     */
    @Override
    public String toString() {
        ArrayList<String> loginName = new ArrayList<>();
        for (Field login : info.getClass().getDeclaredFields()) {
            loginName.add(login.getName());
        }
        return "User{" +
                "ID=" + loginName.get(0) +
                ", Password=" + loginName.get(1) +
                ", accountList=" + getAccountList() +
                '}';
    }

    /**
     * Adds an account for this user.
     *
     * @param acc a new account to be added.
     */
    public void addAccount(Account acc) {
        accountList.add(acc);
    }

    /**
     * Gets the net balance of all accounts this user owns.
     *
     * @return net balance.
     */
    public double getNetBalance() {
        int sum = 0;
        for (Account acc : accountList) {
            if (acc instanceof DebtAccount) {
                sum -= acc.getBalance();
            } else if (acc instanceof AssetAccount) {
                sum += acc.getBalance();
            }
        }
        return sum;
    }

    /**
     * Indicates whether some other users is "equal to" this one.
     * <p>
     * Two users are considered to be the same if they are the same user object, or they have the same username.
     *
     * @param obj the reference user with which to compare.
     * @return true if the given user is considered to be the same as this one.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else {
            return obj instanceof User && ((User) obj).info.getLogin().equals(getLogin());
        }
    }

    /**
     * Gets an account this user owns with an given account id.
     *
     * @param accountID id of the account user trying to get.
     * @return an {@code Account} object if the account with given id found.
     * @see Account
     */
    public Account getAccount(String accountID) {
        for (Account account : accountList) {
            if (account.getId().equals(accountID)) {
                return account;
            }
        }
        return null;
    }

    // ========================== New Method Phrase 2 ===========================
    public List<String> getTransferableAccount() {
        List<String> transferableAccount = new ArrayList<>();
        for (Account account : accountList) {
            if (account instanceof Transferable) {
                transferableAccount.add(account.getClass().getSimpleName() + ":" + account.getId());
            }
        }
        return transferableAccount;
    }

    public List<String> getAllAccount(){
        List<String> Account = new ArrayList<>();
        for (Account account : accountList) {
            Account.add(account.getClass().getSimpleName() + ":" + account.getId());
        }
        return Account;
    }

    public boolean hasGamblingAccount() {
        for (Account account : accountList) {
            if (account instanceof GamblingAccount) return true;
        }
        return false;
    }

    public GamblingAccount getGamblingAccount() {
        for (Account account : accountList) {
            if (account instanceof GamblingAccount) return (GamblingAccount) account;
        }
        return null;
    }

    public void updateAccount(Account acc) {
        for (Account account : accountList) {
            if (account.equals(acc)) {
                accountList.remove(account);
                accountList.add(acc);
                break;
            }
        }
    }
}

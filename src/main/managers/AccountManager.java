package main.managers;

import main.system.Date;
import main.system.SerializationProcessor;
import main.accounts.*;

import java.util.ArrayList;
import java.util.List;

/**
 * a class representing manager handling accounts in the bank
 */
public class AccountManager implements java.io.Serializable {
    /**
     * number of account created
     */
    private int createdAccount;

    /**
     * a list of all account in the bank
     */
    private List<Account> allAccounts;

    /**
     * initializes the manager
     */
    public AccountManager() {
        createdAccount = 0;
        allAccounts = new ArrayList<>();
    }

//    ========================== Getter ===========================

    /**
     * get list of all accounts
     */
    public List<Account> getAccounts() {
        return allAccounts;
    }

    // ========================== Method ===========================

    /**
     * Creates an account of the given type for the given user.
     *
     * @param user        owner of the new account
     * @param type        type of the new account
     * @param createdDate date when the account created
     */
    public void createAccount(User user, String type, Date createdDate) {
        Account accountNew;
        String newId = String.format("%08d", createdAccount);
        switch (type) {
            case "Line of Credit Account":
                accountNew = new LineOfCreditAccount(newId, user, createdDate);
                break;
            case "Savings Account":
                accountNew = new SavingsAccount(newId, user, createdDate);
                break;
            case "Credit Card Account":
                accountNew = new CreditCardAccount(newId, user, createdDate);
                break;
            case "Gambling Account":
                accountNew = new GamblingAccount(newId, user, createdDate);
                break;
            default:
                accountNew = new ChequingAccount(newId, user, createdDate);
                break;
        }
        createdAccount += 1;
        allAccounts.add(accountNew);
        user.addAccount(accountNew);
        SerializationProcessor processor = new SerializationProcessor();
        processor.storeUser(user);
    }


}

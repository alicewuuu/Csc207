package main.managers;

import main.accounts.*;
import main.system.SerializationProcessor;
import main.system.SystemInfo;

import java.util.*;

/**
 * A class handling actions received from user's user interface.
 */
public class UserManager {
    /*** current user */
    private User currentUser;
    /*** List of accounts that this user owns */
    //private List<Account> accounts;
    private SystemInfo systemInfo;
    private SerializationProcessor processor;

    private List<Transaction> recentTransactions;

//    ========================== Constructor ===========================

    /**
     * Initialize user menu with information of the ATM system {@link SystemInfo} and the user {@link User}.
     *
     * @param systemInfo basic info of the current system
     */
    public UserManager(SystemInfo systemInfo, User currentUser) {
        this.systemInfo = systemInfo;
        this.currentUser = currentUser;
        processor = new SerializationProcessor();
    }

//    ========================== Getter ===========================

    /*** get the current user */
    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * Gets account types the bank syste currently supported for adding account.
     * Since each user can have at most one gambling account, when a user w/ his gambling account trying to add a new
     * account, gambling account will not be shown in the list.
     *
     * @return a list of Strings representing account types that current user can add.
     */
    public List<String> getAccountTypes() {
        List<String> typeCopy = new ArrayList<>(systemInfo.getAccountTypes());
        if (currentUser.hasGamblingAccount()) {
            typeCopy.remove("Gambling Account");
            return typeCopy;
        }
        return systemInfo.getAccountTypes();
    }
//    ========================== Public Method ===========================

    /**
     * reset the user's password
     *
     * @param oldPass user's current password
     * @param newPass user's new password
     * @return String
     */
    public String resetPassword(String oldPass, String newPass) {
        // if old password is correct
        if (oldPass.equals(currentUser.getPassword())) {
            boolean valid = newPass.matches("^[a-zA-Z0-9][a-zA-Z0-9]*$");
            if (!valid)
                return "Password must contain (at least one) letter(s) and/or number(s). " +
                        "Failed to reset password.";
            else if (newPass.length() > 20)
                return "New password exceeds 20 characters. Failed to reset password.";
            else {
                currentUser.setPassword(newPass);
                processor.storeUser(currentUser);
                return "Your password has been reset";
            }
        } else return "Incorrect old password.";
    }

    /**
     * Proceed a transfer between given accounts or to one of the given payee's account.
     *
     * @param fromAccID the account transfer money from.
     * @param payeeID   the payee of transaction. If it is internal transfer, the payee will be current user himself.
     * @param toAccID   the account transfer money to.
     * @param amount    the amount of transaction.
     * @return a message indicating result of the process.
     */
    public String transfer(String fromAccID, String payeeID, String toAccID, String amount) {
        boolean succeeded;
        //TODO: check if is transferable
        User payee;
        Transferable fromAccount = (Transferable) currentUser.getAccount(fromAccID);
        if (payeeID.equals(currentUser.getLogin())) payee = currentUser;
        else if (!processor.isExistingUser(payeeID)) return "Payee not found.";
        else {
            payee = processor.getUser();
        }
        Account toAccount = payee.getAccount(toAccID);
        //TODO: check amount is positive
        double transAmount;
        try {
            transAmount = Double.parseDouble(amount);
        } catch (NumberFormatException e) {
            return "Invalid amount";
        }
        if (toAccount != null) {
            succeeded = fromAccount.transferOut(toAccount, transAmount, systemInfo.getDate());
        } else return "Account of Payee not found.";
        if (succeeded) {
            processor.storeAccount((Account) fromAccount);
            if (!(currentUser.getLogin().equals(payee.getLogin()))) processor.storeAccount(toAccount);
            return "Transaction succeeded.";
        } else return "Transaction failed. Please check your account balance.";
    }

    /**
     * Process a pay bill transaction from an account to a given payee with a specific amount.
     *
     * @param fromAccID id of the account to pay the bill.
     * @param payeeID   id of the payee.
     * @param amount    amount of the bill.
     * @return a message indicating result of the pay bill transaction.
     */
    public String payBill(String fromAccID, String payeeID, String amount) {
        Account fromAccount = currentUser.getAccount(fromAccID);
        double transAmount;
        try {
            transAmount = Double.parseDouble(amount);
        } catch (NumberFormatException e) {
            return "Invalid amount";
        }
        if (fromAccount.payBill(payeeID, transAmount, systemInfo.getDate())) {
            processor.storeAccount(fromAccount);
            return "Your bill has been paid.";
        } else return "Payment failed. Please check your account balance.";
    }

    /**
     * Process a withdraw action from an account with a specific amount.
     *
     * @param fromAccID id of account withdrawing money from.
     * @param amount    amount of the withdrawal.
     * @return a message indicating result of the withdrawal.
     */
    public String withdraw(String fromAccID, String amount) {
        boolean succeeded;
        Account fromAccount = currentUser.getAccount(fromAccID);
        CashManager cm = systemInfo.getCashManager();
        int transAmount;
        try {
            transAmount = Integer.parseInt(amount);
        } catch (NumberFormatException e) {
            return "Invalid amount";
        }
        if (cm.canWithdraw(transAmount, "Canadian")) {
            succeeded = fromAccount.withdrawCash(transAmount, systemInfo.getDate());
            if (succeeded) {
                cm.withdraw(transAmount, "Canadian");
                processor.storeAccount(fromAccount);
                return "Withdrawal succeeded. Please don't forget to take your cash.";
            } else return "Withdrawal failed. Please check your account balance.";
        } else return "Sorry. The entered amount cannot be offered by this machine.";
    }

    /**
     * Play the game with the given risk level and an amount.
     *
     * @param riskLevel risk level of the game. Lesser chance to win the game as it gets higher.
     * @param amount    amount of money to play the game.
     * @return a message indicating result of the game.
     */
    public String gamble(String riskLevel, String amount) {
        GamblingAccount account = currentUser.getGamblingAccount();
        try {
            int risk = Integer.parseInt(riskLevel);
            double transAmount = Double.parseDouble(amount);
            if (risk <= 0) {
                return "Risk level cannot be less than 1.";
            }
            if (transAmount < 0) {
                return "Amount cannot be less than 0.";
            }
            String result = account.gamble(risk, transAmount, systemInfo.getDate());
            processor.storeAccount(account);
            return result;
        } catch (NumberFormatException e) {
            return "Sorry. Invalid input. Try again!";
        }
    }

    /**
     * Process the request to add a joint user to an account.
     *
     * @param accID       id of account that requests a joint user.
     * @param jointUserID id of user who requested to be a joint user.
     * @return a message indicating whether the request is submitted successfully.
     */
    public String requestJointUser(String accID, String jointUserID) {

        Account account = currentUser.getAccount(accID);
        if (!account.getUser().getLogin().equals(jointUserID)) {
            if (account.getJointUser() == null) {
                if (processor.isExistingUser(jointUserID)) {
                    systemInfo.addPendingJoint(account, processor.getUser());
                    return "Your request has been recorded. You will be notified by the manager " +
                            "once it is processed. Thank you.";
                } else return "Sorry. User not found.";
            } else return "Sorry. This account already has two joint owners.";
        } else {
            return "You cannot request yourself as a joint user.";
        }
    }

    /**
     * Process the request to undoing a transaction.
     *
     * @param i index of the transaction to be undone in the recent transaction list.
     * @return a message indicating whether the request is submitted successfully.
     */
    public String requestUndo(int i) {
        systemInfo.addPendingUndo((Undoable) recentTransactions.get(i));
        return "Your request has been recorded. You will be notified by the manager once your request " +
                "has been processed.";
    }

    /**
     * Process the request to add a new account.
     * An error message will pop out if user apply for gambling account more than once.
     *
     * @param accountType type of account to be added.
     * @return a message indicating whether the request is submitted successfully.
     */
    public String requestNewAccount(String accountType) {
        if ((systemInfo.isPendingAcc(accountType, currentUser.getLogin())) && accountType.equals("Gambling Account")) {
            return "You have already requested for a gambling account! Limited 1 gambling account per user!";
        } else {
            systemInfo.addPendingAcc(accountType, currentUser.getLogin());
            return "Your request has been recorded. You will see your new account once your " +
                    "request is approved.";
        }
    }

    /**
     * Gets recent transactions of the given account.
     *
     * @param accountID id of the account to be checked.
     * @return a list of recent transaction.
     */
    public List<Transaction> getRecentTransactions(String accountID) {
        recentTransactions = currentUser.getAccount(accountID).getRecentTransaction(systemInfo.getDate());
        return recentTransactions;
    }

    /**
     * Logout to the login scene.
     *
     * @return a main manager that needed to send info back to login scene.
     */
    public MainManager logOut() {
        return new MainManager(systemInfo);
    }

}
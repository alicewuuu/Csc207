package main.managers;

import main.accounts.Undoable;
import main.system.LoginInfo;
import main.system.SerializationProcessor;
import main.system.SystemInfo;
import main.accounts.User;
import main.accounts.Account;
import main.system.Date;


/**
 * A class representing a manager handling actions of bank manager from user interface.
 */
public class ManagerManager extends EmployeeManager {
    private SerializationProcessor processor;

//    ==================== Constructor ==============================

    /**
     * initialize a manager menu
     *
     * @param systemInfo information of the atm machine
     */
    public ManagerManager(SystemInfo systemInfo) {
        super(systemInfo);
        processor = new SerializationProcessor();
    }

//    ==================== public Method ==============================


    /**
     * Process the manager's decision of new user requests.
     * If the request get approved, it will create a new login with the given id and a password "1111".
     * A message will be returned to indicate whether the decision is proceed.
     *
     * @param userID username of the login to be created by request.
     * @param result result of the approval received from manager's user interface.
     * @return a message indicating result of the process.
     */
    public String processUserReq(String userID, String result) {
        String password = "1111";
        String content = userID + "'s request to be a new user has been ";
        if (result.equals("approved")) {
            LoginInfo loginInfo = new LoginInfo(userID, password);
            User user = new User(loginInfo);
            processor.storeUser(user);
            getSystemInfo().addUser(userID);
            return content + "approved";
        } else {
            return content + "disapproved";
        }
    }

    /**
     * Process the manager's decision of new joint user requests.
     * If the request get approved, the given account will have a joint user.
     * A message will be returned to indicate whether the decision is proceed.
     *
     * @param account account that requests a joint user.
     * @param user    user who requested to be a joint user.
     * @param result  result of the approval received from manager's user interface.
     * @return a message indicating result of the process.
     */
    public String processJointUserReq(Account account, User user, String result) {
        String content = "Joint user request has been ";
        if (result.equals("true")) {
            boolean succeeded = account.setJointUser(user);
            if (succeeded) {
                user.addAccount(account);
                processor.storeAccount(account);
                return content + "approved.";
            } else return "Sorry. An account can only have a maximum of two joint users.";
        } else {
            return content + "disapproved.";
        }
    }

    /**
     * Process the manager's decision of undo transaction requests.
     * If the request get approved, the transaction will be undone and removed from history record of this account.
     * A message will be returned to indicate whether the decision is proceed.
     *
     * @param transact the undoable transaction that is requested to be undone.
     * @param result   result of the approval received from manager's user interface.
     * @return a message indicating result of the process.
     */
    public String processUndoTransact(Undoable transact, String result) {
        if (result.equals("true")) {
            transact.undoTransaction(getSystemInfo().getDate());
            return "The transaction has been undone.";
        } else {
            return "The most recent transaction cannot be undone.";
        }
    }

    /**
     * Set date of the operating system with given month, day and year.
     * A message will be returned to indicate the result.
     *
     * @param month month of the date. Expressed in two digits string. E.g., January is expressed in "01"
     * @param day   day of the date. Expressed in two digits string. E.g., "02" is the second day of the month.
     * @param year  year of the date. Expressed in four digits string. E.g., "1970".
     * @return a message indicating result of the process.
     */
    public String setDate(String month, String day, String year) {
        if (getSystemInfo().getDate() == null) {
            try {
                Date date = new Date(Integer.parseInt(month), Integer.parseInt(day), Integer.parseInt(year));

                if (date.isValidDate()) {
                    getSystemInfo().setDate(date);
                    return "The date has been set.";
                } else {
                    return "Invalid input.";
                }
            } catch (NumberFormatException e) {
                return "Invalid input";
            }
        } else {
            return "The date has already been set.";
        }

    }

    /**
     * Processing manager's decision of freezing the given account.
     * A message will be returned to indicate the result of freezing account.
     *
     * @param userID    owner id of the account to be frozen.
     * @param accountID id of the account to be frozen.
     * @param freeze    manager's decision of freezing the account.
     * @return a message indicating result of the process.
     */
    public String freeze(String userID, String accountID, boolean freeze) {
        String content;
        if (processor.isExistingUser(userID)) {
            Account account = processor.getUser().getAccount(accountID);
            if (account == null) return "Account not found.";
            else {
                if (account.isFreeze() != freeze) {
                    account.setStatus(freeze);
                    processor.storeAccount(account);
                    content = userID + "'s account: " + accountID + " has been ";
                } else {
                    content = userID + "'s account: " + accountID + " has already been ";
                }
                if (freeze) {
                    return content + "frozen.";
                } else {
                    return content + "unfrozen";
                }
            }
        } else {
            return "User not found.";
        }
    }

    /**
     * Process manager's decision of creating a new employee login.
     * A message will be created to indicate whether the decision is proceed.
     *
     * @param userID   new id of the employee.
     * @param password password of the employee.
     * @return a message indicating result of the process.
     */
    public String addEmployee(String userID, String password) {
        if (new FileManager().isEmployee(new LoginInfo(userID, password))) {
            return "Employee with login: " + userID + " already exists.";
        } else if (processor.isExistingUser(userID)) {
            return "User with login: " + userID + " already exists.";
        } else {
            new FileManager().addEmployee(userID, password);
            LoginInfo loginInfo = new LoginInfo(userID, password);
            processor.storeUser(new User(loginInfo));
            return "Employee added successfully.";
        }

    }
}

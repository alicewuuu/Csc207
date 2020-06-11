package main.managers;

import java.util.*;

import main.system.SerializationProcessor;
import main.system.SystemInfo;
import main.accounts.User;
import main.accounts.Account;
import main.accounts.ChequingAccount;

public class EmployeeManager {
    private SystemInfo systemInfo;
    private SerializationProcessor processor;

//    ==================== Constructor ==============================

    /**
     * initial a employee menu
     *
     * @param systemInfo system information
     */
    public EmployeeManager(SystemInfo systemInfo) {
        this.systemInfo = systemInfo;
        processor = new SerializationProcessor();
    }

//    ==================== Getter ==============================

    /**
     * get the system's information
     *
     * @return SystemInfo
     */
    public SystemInfo getSystemInfo() {
        return systemInfo;
    }

//    ==================== public method ==============================

    /**
     * approve or disapproved an new account request
     *
     * @param userID user id that request a new account
     * @param type   account type requesting
     * @param result 'true' to approve and 'false' to disapprove
     * @return string telling whether approve or not
     */
    public String processAccountReq(String userID, String type, String result) {
        String content = userID + "'s request of account type: " + type;
        if (result.equals("true")) {
            processor.isExistingUser(userID);
            User user = processor.getUser();
            systemInfo.getAccountManager().createAccount(user, type, systemInfo.getDate());
            return content + "is approved";
        } else {
            return content + "is disapproved";
        }
    }

    /**
     * restock the atm machine
     *
     * @param country      the currency type
     * @param denomination denomination
     * @param num          number of corresponding denomination
     * @return restock status
     */
    public String restock(String country, String denomination, String num) {
        try {
            if (!systemInfo.getCashManager().getCountryToDenomination().containsKey(country)) {
                return "This type of currency is currently not supported.";
            }
            int denominationInt = Integer.parseInt(denomination);
            if (!systemInfo.getCashManager().getCountryToDenomination().get(country).contains(denominationInt)) {
                return "Invalid denomination. Enter 5, 10, 20, or 50 only.";
            }
            int numInt = Integer.parseInt(num);
            if (numInt >= 0) {
                systemInfo.getCashManager().restock(denominationInt, numInt, country);
                return "Restock succeeded.";
            } else {
                return "Invalid number of bills.";
            }
        } catch (NumberFormatException e) {
            return "Invalid input, please enter an integer for denomination and number of bills.";
        }

    }

    /**
     * shut down the system: update the date
     * handle deposit money
     * store the system information
     * update account if it's the last day of the month
     *
     * @return whether the money is deposited successfully
     */
    public String shutDown() {
        updateDate();
        String s = processDeposits();
        new SerializationProcessor().storeSystemInfo(systemInfo);
        if (systemInfo.getDate().isLastDay()) {
            List<String> users = systemInfo.getUsers();
            for (String login : users) {
                List<Account> accounts;
                if (processor.isExistingUser(login)) {
                    User user = processor.getUser();
                    accounts = user.getAccountList();
                    for (Account account : accounts) {
                        account.update(systemInfo.getDate());
                    }
                    processor.storeUser(user);
                }
            }
        }
        return s;
    }

//    ==================== private method ==============================

    /**
     * read deposit.txt and deposit money into corresponding account
     */
    private String processDeposits() {
        StringBuilder content = new StringBuilder();
        FileManager fileManager = new FileManager();
        ArrayList<ArrayList<Object>> deposits;
        try {
            deposits = fileManager.getDeposit();
        } catch (IndexOutOfBoundsException e) {
            return "Deposit file has invalid format.";
        }
        for (ArrayList<Object> deposit : deposits) {
            try {
                User user = new SerializationProcessor().getUser((String) deposit.get(0));
                String accID = (String) deposit.get(1);
                String type = (String) deposit.get(3);
                if (user == null || user.getAccount(accID) == null) {
                    throw new ClassCastException();
                }
                ChequingAccount account = (ChequingAccount) user.getAccount(accID);
                if (type.equals("cash")) {
                    cashChoice(deposit, account);
                } else if (type.equals("cheque")) {
                    int amt = (Integer) deposit.get(2);
                    account.deposit(amt, systemInfo.getDate());
                } else {
                    throw new ClassCastException();
                }
                processor.storeAccount(account);
            } catch (Exception e) {
                String s = "Info: " + deposit + " was not able to be processed. \n";
                content.append(s);
            }
        }
        if (content.length() == 0) {
            content.append("Deposit processing was successful");
        }
        return content.toString();
    }

    /**
     * Deposit money into chequing account if cashes is deposited.
     *
     * @param deposit cash deposit
     * @param account account that will be deposited
     */
    private void cashChoice(ArrayList<Object> deposit, ChequingAccount account) throws IndexOutOfBoundsException {
        ArrayList amountList = (ArrayList) deposit.get(2);
        String country = (String) deposit.get(4);
        Map<Integer, Integer> l = new HashMap<>();
        int acc = 0;
        int amt = 0;
        ArrayList<Integer> denomination = systemInfo.getCashManager().getCountryToDenomination().get(country);
        Collections.sort(denomination);
        for (int i : denomination) {
            l.put(i, Integer.parseInt((String) amountList.get(acc)));
            amt += i * Integer.parseInt((String) amountList.get(acc));
            acc++;
        }
        account.deposit(amt, systemInfo.getDate());
        for (Map.Entry<Integer, Integer> pair : l.entrySet()) {
            systemInfo.getCashManager().restock(pair.getKey(), pair.getValue(), country);
        }
    }

    /**
     * updates current date to the next day.
     */
    private void updateDate() {
        systemInfo.getDate().addOneDay();
    }


}

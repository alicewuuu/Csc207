package main.managers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * A class representing the manager handling cash.
 */
public class CashManager implements java.io.Serializable {
    //===================variables================================
    /**
     * map country to a map which map domination to number of domination
     */
    private Map<String, Map<Integer, Integer>> stock;

    /**
     * a map storing country and list of corresponding domination
     */
    private Map<String, ArrayList<Integer>> countryToDenomination = new HashMap<>();

    /**
     * a map strong country to amount of money
     */
    private Map<String, Integer> countryToAmount = new HashMap<>();

    //==================== constructor ==============================

    /**
     * Initializes the stock with given number of cashes in different denomination.
     *
     * @param stock mapping with key currency_type and value of another map mapping denomination to amount
     */
    public CashManager(Map<String, Map<Integer, Integer>> stock) {
        this.stock = stock;
        for (String country : stock.keySet()) {
            ArrayList<Integer> l = new ArrayList<>();
            int acc = 0;
            for (Integer dom : stock.get(country).keySet()) {
                l.add(dom);
                acc += dom * stock.get(country).get(dom);
            }
            countryToDenomination.put(country, l);
            countryToAmount.put(country, acc);
        }
        checkStock();
    }

//    ===================== getter =============================

    /**
     * get information of type of currency and corresponding domination they have
     */
    Map<String, ArrayList<Integer>> getCountryToDenomination() {
        return countryToDenomination;
    }

    //===================== methods =============================

    /**
     * Checks if user can withdraw from account with the given amount.
     * <p>
     * The ATM machine should have a larger total amount than the redraw amount.
     * Amount of withdrawal should be a multiple of 5.
     *
     * @param amount Amount of money that the user trying to withdraw.
     * @return true if there is sufficient amount of money to be withdraw from.
     */
    boolean canWithdraw(int amount, String country) {
        System.out.println(countryToDenomination.keySet());
        Collections.sort(countryToDenomination.get(country), Collections.reverseOrder());
        ArrayList<Integer> domination = countryToDenomination.get(country);
        int amt = amount;
        //see if amount is less than total stock
        if (countryToAmount.get(country) < amt) {
            return false;
        }
        //see if can be withdrew
        else {
            for (int i : domination) {
                int r = 0;
                while (amt >= i && stock.get(country).get(i) - r >= 1) {
                    r += 1;
                    amt -= i;
                }
            }
            System.out.println(amt == 0);
            return amt == 0;
        }
    }

    /**
     * Withdraws money from the stock.
     * It will check the stock after every time it withdraws.
     *
     * @param amount amount of money that will be withdraw from the stock.
     */
    public void withdraw(int amount, String country) {
        Collections.sort(countryToDenomination.get(country), Collections.reverseOrder());
        ArrayList<Integer> domination = countryToDenomination.get(country);
        int amt = amount;
        for (int i : domination) {
            while (amt >= i && stock.get(country).get(i) >= 1) {
                stock.get(country).replace(i, stock.get(country).get(i) - 1);
                amt -= i;
            }
        }
        checkStock();
    }

    /**
     * Restock the a certain number of cashes in given denomination.
     *
     * @param key the denomination that will get restocked.
     * @param num number of cashes that will get restocked.
     */
    public void restock(int key, int num, String country) {
        int numBefore = stock.get(country).get(key);
        stock.get(country).replace(key, numBefore + num);

    }

    @Override
    public String toString() {
        StringBuilder content = new StringBuilder();
        for (String country : stock.keySet()) {
            content.append(country);
            content.append(" : ");
            for (Integer d : stock.get(country).keySet()) {
                String s = d + " : " + stock.get(country).get(d) + " ; ";
                content.append(s);
            }
        }
        return content.toString();
    }

//    ===================== private methods =============================

    /**
     * Checks if there is under-stock of cash of any denomination.
     * Record alerts if there is.
     *
     * @see FileManager#recordAlert(String) for details on recording alerts.
     */
    private void checkStock() {
        for (String country : stock.keySet()) {
            StringBuilder alert = new StringBuilder();
            for (Integer d : stock.get(country).keySet()) {
                if (stock.get(country).get(d) < 20) {
                    alert.append("Alert: the number of ");
                    alert.append(country);
                    alert.append(" ");
                    alert.append(d);
                    alert.append(" dollars bill in the cash machine is now below 20!\n");
                }
                if (alert.length() != 0) {
                    FileManager fileManager = new FileManager();
                    fileManager.recordAlert(alert.toString());
                }
            }
        }
    }

}
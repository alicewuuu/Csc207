package main.accounts;

import main.system.Date;

import java.util.Random;

public class GamblingAccount extends AssetAccount {

    /**
     * initialize a new gambling account
     *
     * @param id    account id
     * @param owner owner of this account
     * @param date  date that this account is created
     */
    public GamblingAccount(String id, User owner, Date date) {
        super(id, owner, date);
    }

    /**
     * play the game and win if you get the same number three times
     *
     * @param riskLevel risk level larger than 1
     * @param amount    amount of money invest
     * @param date      date to play the game
     * @return string (whether you win or not)
     */
    public String gamble(int riskLevel, double amount, Date date) {
        Random random = new Random();
        int range = 5 + riskLevel;
        int n1 = random.nextInt(range);
        int n2 = random.nextInt(range);
        int n3 = random.nextInt(range);
        if (n1 == n2 && n2 == n3) {
            double percent = 0.15 + 0.05 * riskLevel;
            deposit(percent * amount, date);
            return "Congratulations! You got three " + n1 + "'s!!! Your account balance is " +
                    "increased by " + percent * 100 + "!!!";
        } else {
            payBill("BANK's gambling account", amount, date);
            return "You got " + n1 + ", " + n2 + " and " + n3 + " :( Good luck next time!";
        }
    }

    @Override
    public void update(Date date) {
        Random random = new Random();
        double percent = random.nextInt(10) * 0.01;
        if (random.nextInt(10) < 4) {
            deposit(percent * Math.abs(balance), date);
        } else {
            payBill("BANK's gambling account", percent * Math.abs(balance), date);
        }
        super.update(date);
    }

    @Override
    public boolean isValidTransfer(double amount) {
        return true;
    }

}

package main.system;

import java.util.Arrays;
import java.util.List;

/**
 * The class {@code Date} represents date of the ATM machine system.
 * A valid date in this ATM system is consisted of a day, a month and a year number. They are all represented as
 * integers, i.e. 01021970 represents January 2nd, 1970.
 */
public class Date implements java.io.Serializable {
    /**
     * The integer representing the current year
     */
    private int year;

    /**
     * The integer representing the current month (i.e. {@code 8} represents August in English)
     */
    private int month;

    /**
     * The integer representing the current day of the month
     */
    private int day;

    /**
     * List of all months that have 30 days
     */
    private final List<Integer> thirty = Arrays.asList(4, 6, 9, 11);

    /**
     * List of all months that have 31 days
     */
    private final List<Integer> thirtyOne = Arrays.asList(1, 3, 5, 7, 8, 10, 12);

//    ========================== Constructor ===========================

    /**
     * Initializes a newly created {@code Date} object so that it represents a date with month, day and year.
     * Note that the input of {@code month}, {@code day} and {@code year} must be valid, which is,
     * input for {@code month} must be positive and does not exceed 12.
     * input for {@code day} must be positive and does not exceed 30, or 31 depending on the month.
     * input for {@code year} must be positive.
     *
     * @param month month of the year
     * @param day   day of the month
     * @param year  current year
     */
    public Date(int month, int day, int year) {
        this.month = month;
        this.day = day;
        this.year = year;
    }

//    ========================== Getter ===========================

    /**
     * get the date of current date as string
     */
    public String getDate() {
        return String.format("%04d%02d%02d", this.year, this.month, this.day);
    }

//    ========================== Public Method ===========================

    /**
     * Goes to the day after current date.
     */
    public void addOneDay() {
        // last day of month
        if (isLastDay()) {
            // last month of year
            if (isLastMonth()) {
                this.year += 1;
                this.month = 1;
            } else {
                this.month += 1;
            }
            this.day = 1;
        } else {
            this.day += 1;
        }

    }

    /**
     * Get the previous date of the current date
     */
    public Date subtract() {
        int year = this.year;
        int month = this.month;
        int day = this.day;

        // xxxx-xx-01
        if (isFirstDay()) {
            // xxxx-03-01
            if (isFirstMonth()) {
                year -= 1;
                month = 12;
            }
            // xxxx-xx-01
            else {
                month -= 1;
            }
            // xxxx/2/1
            if (month == 2) {
                // 2018/03/01
                if (this.isLeapYear()) {
                    day = 29;
                } else {
                    day = 28;
                }
            } else {
                if (thirty.contains(month)) {
                    day = 30;
                } else {
                    day = 31;
                }
            }
        }
        // xxxx-xx-xx
        else {
            day -= 1;
        }

        return new Date(month, day, year);
    }

    /**
     * Checks if the current date is valid.
     * <p>
     * i.e., if current {@code day} is positive and does not exceed 30, or 31 depending on the current month,
     * and if current {@code month} is positive and does not exceed 12,
     * and if current {@code year} is positive, it is considered to be valid.
     *
     * @return true if the current date is valid, false otherwise.
     */
    public boolean isValidDate() {
        boolean checkPositive = false;
        boolean checkDay = false;
        boolean checkMonth = false;
        if (this.day >= 0 && this.month >= 0 && this.year >= 0) {
            checkPositive = true;
        }
        if (this.month <= 12) {
            checkMonth = true;
        }
        if (thirty.contains(this.month) && this.day <= 30) {
            checkDay = true;
        } else if (thirtyOne.contains(this.month) && this.day <= 31) {
            checkDay = true;
        } else if (this.month == 2 && isLeapYear() && this.day <= 29) {
            checkDay = true;
        } else if (this.month == 2 && !isLeapYear() && this.day <= 28) {
            checkDay = true;
        }
        return checkPositive && checkMonth && checkDay;
    }

    /**
     * Checks if the current date is the last day of the month.
     */
    public boolean isLastDay() {
        // February has only 28 or 29 days
        if (this.month == 2) {
            // a leap year has 29 days
            if (isLeapYear()) {
                return this.day == 29;
            } else {
                return this.day == 28;
            } // a common year has 28 days
        }
        // otherwise
        if (thirty.contains(this.month) && this.day == 30) { // for months having 30 days
            return true;
        } else {
            return thirtyOne.contains(this.month) && this.day == 31;
        }
    }

//========================== Other Method ===========================

    /* Generates a {@code String} representation of current date. */
    @Override
    public String toString() {
        return String.format("%02d/%02d/%04d", this.month, this.day, this.year);
    }

    /**
     * check if the two dates are equal
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Date) {
            Date date = (Date) obj;
            boolean y = this.year == date.year;
            boolean m = this.month == date.month;
            boolean d = this.day == date.day;
            return y && m && d;
        }
        return false;
    }

    //    ========================== Helper Method ===========================

    /**
     * check if it's the first day of the month
     */
    private boolean isFirstDay() {
        return this.day == 1;
    }

    /**
     * check if it's the first month of the year
     */
    private boolean isFirstMonth() {
        return this.month == 1;
    }

    /**
     * check if it's a leaping year
     */
    private boolean isLeapYear() {
        return this.year % 4 == 0;
    }

    /**
     * Checks if the current month is the last month of the year.
     */
    private boolean isLastMonth() {
        return this.month == 12;
    }
}

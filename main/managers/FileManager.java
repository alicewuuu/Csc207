package main.managers;

import main.system.LoginInfo;
import main.accounts.PayBillTransaction;

import java.io.*;
import java.util.*;

import static com.sun.org.apache.xalan.internal.utils.SecuritySupport.getResourceAsStream;

public class FileManager {

    /**
     * represents a manager that read file
     */

//    ==================== Write ==============================
    void addEmployee(String userID, String password) {
        // data is store in the form of "username,password"
        String content = userID + ',' + password;
        write("employee.txt", content, true);
    }

    /**
     * Takes in alert message and archives it.
     * All alerts will be stored in alerts.txt.
     *
     * @param alert alert message.
     */
    void recordAlert(String alert) {
        write("." + File.separator + "Alert.txt", alert, true);
    }

    /**
     * Gets all deposits records from deposits.txt.
     * The file will be cleared after reading.
     * <p>
     * It returns an ArrayList contains information on the deposit in the form of
     * [login, accountID, amount, cheque (type)] or
     * [login, accountID, [number of $5, number of $10, number of $20, number of $50], cash (type)]
     *
     * <blockquote>For example,
     * if there are two deposit records in the file
     * IronMan;88886666;1,2,3,4;cash
     * CaptainAmerica;10086;233;cheque
     * It returns an ArrayList:
     * {@code [["IronMan", 88886666, [1, 2, 3, 4], "cash"], ["CaptainAmerica", 10086, 233, "cheque"]]}
     * </blockquote>
     *
     * @return an ArrayList contains information of this deposit.
     */
    public ArrayList<ArrayList<Object>> getDeposit() throws IndexOutOfBoundsException {
        String filePath = "." + File.separator + "database" + File.separator + "deposits.txt";
        ArrayList depo = readTxt(filePath);
        for (int i = 0; i < depo.size(); i++) {
            String[] singleArr = ((String) depo.get(i)).split(";");
            ArrayList singleList = new ArrayList(Arrays.asList(singleArr));
            singleList.set(1, singleList.get(1));

            if (singleArr[3].equals("cash")) {
                String[] accArr = singleArr[2].split(",");
                ArrayList accList = new ArrayList(Arrays.asList(accArr));
                singleList.set(2, accList);
            } else if (singleArr[3].equals("cheque")) {
                singleList.set(2, (Integer.valueOf((String) singleList.get(2))));
            }
            depo.set(i, singleList);
        }
        clearTxt("deposits.txt"); // clear the file every time after reading
        return (ArrayList<ArrayList<Object>>) depo;
    }

    /**
     * Takes in PayBillTransaction content and write it into
     * outgoing.txt file
     *
     * @param ptr content of payBillTransaction.
     */
    public void newPayBill(PayBillTransaction ptr) {
        write("outgoing.txt", ptr.toString(), true);
    }


//    ==================== Read ==============================

    /**
     * Checks the validity of the given manager login.
     * The login {@link LoginInfo} must contains valid login and password in type String {@link String}.
     *
     * @param login a manager login.
     * @return true if the given manager login is valid, false otherwise.
     */
    boolean isManager(LoginInfo login) {
        // data is store in the form of "username,password"
        String content = login.getLogin() + ',' + login.getPassword();
        return contains("." + File.separator + "database" + File.separator + "manager.txt", content);
    }

    /**
     * checks the validity of the given employee login
     *
     * @param login a employee login
     * @return true if the given employee login is valid, false otherwise
     */
    boolean isEmployee(LoginInfo login) {
        // data is store in the form of "username,password"
        String content = login.getLogin() + ',' + login.getPassword();
        System.out.println(readTxt("." + File.separator + "database" + File.separator + "employee.txt"));
        return contains("." + File.separator + "database" + File.separator + "employee.txt", content);

    }

//    ==================== Private method ==============================

    /**
     * Checks if the given content is in the file at the given file path
     * {@code filePath} must be the valid path to the file.
     *
     * @param filePath a path to the directory of the file we are going to check
     * @param content  a String we want to check if it is in the given file.
     * @return true if the corresponding content is found in file.
     */
    private boolean contains(String filePath, String content) {
        ArrayList lst = readTxt(filePath);
        return lst.contains(content);
    }

    /**
     * get line separator of the operating system
     *
     * @return line separator.
     */
    private String System() {
        return System.getProperty("line.separator");
    }

    /**
     * Reads content of the file at the given file path.
     * Lines in the file will be putted separately in an ArrayList {@link ArrayList}.
     * {@code filePath} must be the valid path to the file.
     *
     * @param filePath path to the directory of file we are reading.
     * @return an ArrayList of lines as elements.
     */
    private ArrayList readTxt(String filePath) {
        ArrayList<String> TxtContent = new ArrayList<>();
        try {
            InputStream in = getResourceAsStream(filePath);
            InputStreamReader reader = new InputStreamReader(in);
            BufferedReader br = new BufferedReader(reader);
            while (br.ready()) {
                TxtContent.add(br.readLine());
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return TxtContent;
    }

    /**
     * Edits file with the given content at the file path.
     * The file can be edited in two modes.
     * If {@code append} is true then the file will be edited in append mode, that is, appending content at a at the end
     * of the file, in a new line.
     * Otherwise, if {@code append} is false, the whole file will be overwritten by the given content.
     *
     * @param fileName name of the file we want to edit.
     * @param content  the content that will be appended, or overwritten to the file.
     * @param append   append mode will be turned on if true.
     * @see FileWriter for more on append mode switch.
     * {@code filePath} must be the valid path to the file.
     */
    private void write(String fileName, String content, boolean append) {
        BufferedWriter bw = null;
        String path = "." + File.separator + "phase2" + File.separator + "src" + File.separator + "database" + File.separator + fileName;
        try {
            bw = new BufferedWriter(new FileWriter(path, append));
            bw.append(content + System());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                }
            }
        }
    }

    /**
     * Clear content for the selected file.(Helper function for scanDeposit.
     *
     * @param fileName file's name
     */
    private void clearTxt(String fileName) {
        BufferedWriter bw = null;
        //File f = new File(fileName);
        String filePath = "." + File.separator + "phase2" + File.separator + "src" + File.separator + "database" +
                File.separator + fileName;
        try {
            bw = new BufferedWriter(new FileWriter(filePath));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

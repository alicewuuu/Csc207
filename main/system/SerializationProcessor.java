package main.system;

import main.accounts.User;
import main.accounts.Account;

import java.io.*;

/**
 * A class representing processor that helps storing objects with serialization
 */
public class SerializationProcessor {

    private User user;

    /**
     * Stores object to the given path using serialization.
     *
     * @param path the path to store object
     * @param o    object that is being stored
     */
    private void storeObject(String path, Object o) {
        try {
            FileOutputStream fileOut = new FileOutputStream(path);
            fileOut.close();
            fileOut = new FileOutputStream(path);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(o);
            out.close();
            fileOut.close();
        } catch (IOException e) {
            System.out.println("Storing object failed.");
        }
    }

    /**
     * Stores an user as an object.
     *
     * @param user the user that is being stored
     */
    public void storeUser(User user) {
        String path = "./phase2/src/users/" + user.getLogin() + ".ser";
        storeObject(path, user);
    }

    /**
     * Stores information of the system.
     *
     * @param info system information that is being stored
     */
    public void storeSystemInfo(SystemInfo info) {
        String path = "./phase2/src/system_info.ser";
        storeObject(path, info);
    }

    /**
     * Gets an object that had been stored in the given path.
     *
     * @param path path where object is being stored.
     * @return the object if it's found, null otherwise
     */
    private Object getObject(String path) {
        try {
            FileInputStream fileIn = new FileInputStream(path);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            Object o = in.readObject();
            in.close();
            fileIn.close();
            return o;
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }

    /**
     * Gets a user with the login information.
     *
     * @return the user that is found, null otherwise
     */
    public User getUser() {
        return user;
    }

    /**
     * Gets a user with the username.
     *
     * @param login username of the user that the system is trying to get.
     * @return the user that is found, null otherwise
     */
    public User getUser(String login) {
        String path = "./phase2/src/users/" + login + ".ser";
        Object o = getObject(path);
        if (o != null) return (User) o;
        else return null;
    }

    /**
     * Gets the system information stored in the local file.
     *
     * @return the system info.
     */
    public SystemInfo getSystemInfo() {
        String path = "./phase2/src/system_info.ser";
        Object o = getObject(path);
        if (o != null) return (SystemInfo) o;
        else return null;
    }

    /**
     * stores the user(s) of this account information
     *
     * @param account account that wants to store
     */
    public void storeAccount(Account account) {
        storeUser(account.getUser());
        User joint = account.getJointUser();
        if (joint != null) {
            joint.updateAccount(account);
            storeUser(joint);
        }
    }

    /**
     * Checks if an user with the given login (username) exists.
     *
     * @param login login (username) of the user that is being checked
     * @return true if there exists such a user, false otherwise.
     */
    public boolean isExistingUser(String login) {
        String path = "./phase2/src/users/" + login + ".ser";
        Object o = getObject(path);
        if (o != null) {
            user = (User) o;
            return true;
        } else return false;
    }
}

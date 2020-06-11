package main.system;

/**
 * A class representing a login which contains username and password
 */
public class LoginInfo implements java.io.Serializable {

    /**
     * current username
     */
    private String login;

    /**
     * password corresponding to current username
     */
    private String password;

    /**
     * Constructor for LoginInfo.
     * A valid login is made up of username and password.
     * "login"(username) and password should be valid Strings.
     *
     * @param login    refers to username of the login.
     * @param password password of the corresponding username.
     */
    public LoginInfo(String login, String password) {
        this.login = login;
        this.password = password;
    }

    /**
     * Gets the username {@link #login} of the current login.
     *
     * @return current username.
     * @see #setLogin(String) which is a setter for current username.
     */
    public String getLogin() {
        return login;
    }

    /**
     * Gets the password {@link #password} of the current login.
     *
     * @return current password.
     * @see #setPassword(String) which is a setter for current password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the current username to be the new given one.
     *
     * @param login new username.
     * @see #getLogin() which is a getter for current username.
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * Sets the current password to be the new given one.
     *
     * @param password new password.
     * @see #getPassword() which is a getter for current password.
     */
    public void setPassword(String password) {
        this.password = password;
    }
}

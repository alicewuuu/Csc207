package main.managers;

import main.accounts.User;
import main.system.LoginInfo;
import main.system.SerializationProcessor;
import main.system.SystemInfo;

public class MainManager {

    private SystemInfo systemInfo;

//    ==================== Constructor ==============================

    /**
     * initialize a main menu for this program
     *
     * @param systemInfo information of this atm machine
     */
    public MainManager(SystemInfo systemInfo) {
        this.systemInfo = systemInfo;
    }

//    ==================== Getter ==============================

    /**
     * get information of atm machine
     */
    public SystemInfo getSystemInfo() {
        return systemInfo;
    }

//    ==================== Public method ==============================

    /**
     * login as a user
     *
     * @param login    login id
     * @param password default or reset-password
     * @return user menu if login successfully, otherwise null
     */
    public UserManager userLogin(String login, String password) {
        SerializationProcessor processor = new SerializationProcessor();
        if (processor.isExistingUser(login)) {
            User user = processor.getUser();
            if (user.getPassword().equals(password)) {
                return new UserManager(systemInfo, user);
            }
        }
        return null;
    }

    /**
     * login as a bank manager
     *
     * @param login    login id
     * @param password password
     * @return bank manager menu if login successfully, otherwise null
     */
    public ManagerManager managerLogin(String login, String password) {
        LoginInfo info = new LoginInfo(login, password);
        FileManager fileManager = new FileManager();
        if (fileManager.isManager(info)) {
            return new ManagerManager(systemInfo);
        } else return null;
    }

    /**
     * login a an employee of the atm
     *
     * @param login    login id
     * @param password password
     * @return employee menu if login successfully, otherwise null
     */
    public EmployeeManager employeeLogin(String login, String password) {
        LoginInfo info = new LoginInfo(login, password);
        FileManager fileManager = new FileManager();
        if (fileManager.isEmployee(info)) {
            return new EmployeeManager(systemInfo);

        } else return null;
    }

    /**
     * request to be a new user of this machine
     *
     * @param id id used to login
     * @return String telling whether request successfully
     */
    public String newUserRequest(String id) {
        boolean valid = id.matches("^[a-zA-Z0-9][a-zA-Z0-9]*$");
        if (!valid || id.length() > 20) return "Sorry. Invalid birth id. Request failed.";
        SerializationProcessor processor = new SerializationProcessor();
        if (!processor.isExistingUser(id)) {
            systemInfo.addPendingUser(id);
            return "Your request has been recorded. You will be notified by manager once your " +
                    "request gets processed. Thank you.";
        } else return "Sorry. User id already exists. Request failed.";
    }

}

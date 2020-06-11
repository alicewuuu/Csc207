package gui.guimain;

import gui.guiuser.UserController;
import gui.guimanager.BankManagerController;
import gui.guiemployee.EmployeeController;
import javafx.fxml.Initializable;
import main.managers.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import javafx.event.ActionEvent;

import java.io.IOException;

import main.system.Date;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController extends Controller implements Initializable {


    /*** a manager handling w/ archives in txt format.*/
    private MainManager mainManager;

    /**
     * PasswordField used to get the password
     */
    @FXML
    private PasswordField Password;

    /**
     * TextField used to get the username
     */
    @FXML
    private TextField Username;

    /**
     * Label used to get the system date
     */
    @FXML
    private Label date;

    /**
     * Date to store the system date
     */
    private Date sysDate;

    /**
     * Initialize data needed to set for Login.
     * Clear TextField username and PasswordFiled password.
     */
    public void initialize(URL location, ResourceBundle resources) {
        Username.clear();
        Password.clear();
    }

    /**
     * Initialize data needed to set for Login.
     *
     * @param mainManager MainManager handling Login action.
     */
    public void initData(MainManager mainManager) {
        this.mainManager = mainManager;
        this.sysDate = mainManager.getSystemInfo().getDate();
        if (sysDate == null) {
            gui.guimain.Alert.show("Warning", "ATM not ready for service: ask manager to set system date first.");
        }
    }

    /**
     * Action when the event on cd button is detected.
     * It will check the system date and change label date to the system date.
     */
    public void setCheckDate() {
        if (sysDate != null) {
            date.setText(sysDate.toString());
        } else {
            gui.guimain.Alert.show("Result", "The date has not been set by Bank Manager");
        }
    }

    /**
     * Action when the event on BankManager button is detected.
     * It will set the scene to BankManager scene.
     */
    public void setBankManagerScene(ActionEvent actionEvent) {
        String user = Username.getText();
        String pass = Password.getText();
        ManagerManager managerManager = mainManager.managerLogin(user, pass);
        if (managerManager != null) {
            try {
                ((BankManagerController) changeScene("/gui/guimanager/BankManager.fxml")).initData(managerManager);
            } catch (IOException e) {
                e.printStackTrace();
            }
            displayScene(actionEvent);
        } else {
            gui.guimain.Alert.show("Error", "Sorry. Incorrect login and/or password. Login failed.");
        }
    }

    /**
     * Action when the event on employee button is detected.
     * It will set the scene to employee scene.
     */
    public void setEmployeeScene(ActionEvent actionEvent) {
        String user = Username.getText();
        String pass = Password.getText();
        EmployeeManager employeeManager = this.mainManager.employeeLogin(user, pass);
        if (employeeManager != null) {
            try {
                ((EmployeeController) changeScene("/gui/guiemployee/Employee.fxml")).initData(employeeManager);
            } catch (IOException e) {
                e.printStackTrace();
            }
            displayScene(actionEvent);
        } else {
            gui.guimain.Alert.show("Error", "Sorry. Incorrect login and/or password. Login failed.");
        }

    }

    /**
     * Action when the event on add new user button is detected.
     * It will set the scene to add new user scene.
     */
    public void changeRegisterScene(ActionEvent actionEvent) {
        try {
            ((RegisterController) changeScene("/gui/guimain/Register.fxml")).initData(mainManager);
        } catch (IOException e) {
            e.printStackTrace();
        }
        displayScene(actionEvent);
    }

    /**
     * Action when the event on existing_user button is detected.
     * It will set the scene to esisting user scene.
     */
    public void setExistingUser(ActionEvent actionEvent) {
        String user = Username.getText();
        String pass = Password.getText();
        UserManager userManager = mainManager.userLogin(user, pass);
        if (userManager != null) {
            try {
                ((UserController) changeScene("/gui/guiuser/UserUI-ATM.fxml")).initData(userManager);
            } catch (IOException e) {
                e.printStackTrace();
            }
            displayScene(actionEvent);
        } else {
            Alert.show("Error", "Sorry. Incorrect login and/or password. Login failed.");
        }
    }
}

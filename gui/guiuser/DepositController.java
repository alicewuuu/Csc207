package gui.guiuser;

import gui.guimain.Alert;
import javafx.event.ActionEvent;
import main.managers.UserManager;
import gui.guimain.Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * A class representing controller of the deposit scene.
 */
public class DepositController extends Controller implements Initializable {

    /**
     * User manager handling deposit actions
     */
    UserManager userManager;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    /**
     * Initialize data needed to proceed the deposit.
     *
     * @param userManager user manager handling deposit action.
     */
    public void initData(UserManager userManager) {
        this.userManager = userManager;
    }

    /**
     * Action when event on continue button is detected.
     * It will proceed the deposit, show message to indicate whether the process is success and go back to main menu.
     */
    public void setContinue(javafx.event.ActionEvent actionEvent) {
        Alert.show("Deposit", "deposit succeed!");
        backToMenu(actionEvent);
    }

    /**
     * Action when the event on cancel button is detected.
     * It will cancel the deposit and return back to main menu.
     */
    public void setCancel(javafx.event.ActionEvent actionEvent) {
        backToMenu(actionEvent);
    }

    /**
     * This is a method that helps returning back to main menu when given action is detected.
     */
    private void backToMenu(ActionEvent actionEvent) {
        try {
            ((UserController) changeScene("/gui/guiuser/UserUI-ATM.fxml")).initData(userManager);
        } catch (IOException e) {
            e.printStackTrace();
        }
        displayScene(actionEvent);
    }
}

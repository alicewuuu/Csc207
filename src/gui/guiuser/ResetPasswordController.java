package gui.guiuser;

import gui.guimain.Alert;
import gui.guimain.Controller;
import main.managers.UserManager;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class ResetPasswordController extends Controller implements Initializable {

    /**
     * Password fields to enter old password and new password to be reset
     */
    @FXML
    private PasswordField oldPassword;
    @FXML
    private PasswordField newPassword;

    /**
     * User manager handles resetting password
     */
    private UserManager userManager;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    /**
     * Initialize data needed to finish the process
     *
     * @param userM user manager helps handle resetting password.
     */
    public void initData(UserManager userM) {
        this.userManager = userM;
    }

    /**
     * Action when button "Cancel" is hit.
     *
     * @param actionEvent action!
     */
    public void setCancel(javafx.event.ActionEvent actionEvent) {
        backToMenu(actionEvent);
    }

    /**
     * Action when event on continue button is detected.
     * It will check the information, and if information matches the resetting will get proceed.
     * A message box will pop out to indicate whether it is succeed.
     */
    public void setContinue(javafx.event.ActionEvent actionEvent) {
        String o = oldPassword.getText();
        String n = newPassword.getText();
        String result = userManager.resetPassword(o, n);
        Alert.show("Reset Password", result);
        backToMenu(actionEvent);
    }

    /**
     * A method helps to return back to user menu when given action is detected.
     */
    private void backToMenu(javafx.event.ActionEvent actionEvent) {
        try {
            ((UserController) changeScene("/gui/guiuser/UserUI-ATM.fxml")).initData(userManager);
        } catch (IOException e) {
            e.printStackTrace();
        }
        displayScene(actionEvent);
    }
}

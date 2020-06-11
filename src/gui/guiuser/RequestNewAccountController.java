package gui.guiuser;

import gui.guimain.Alert;
import gui.guimain.Controller;
import main.managers.UserManager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class RequestNewAccountController extends Controller implements Initializable {

    /**
     * box that lists accounts to choose from
     */
    @FXML
    private ComboBox<String> accountType;

    /**
     * button to continue or cancel the transaction
     */
    @FXML
    private Button continueButton;

    /**
     * Manager handling request for a new account
     */
    private UserManager userManager;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    /**
     * Initialize data needed to finish the process.
     *
     * @param userManager user manager helps handle requesting.
     */
    public void initData(UserManager userManager) {
        this.userManager = userManager;
        String accStr = userManager.getAccountTypes().toString();
        List<String> acc = Arrays.asList(accStr.substring(1, accStr.length() - 1).split(", "));
        ObservableList<String> oacc = FXCollections.observableArrayList(acc);
        accountType.setItems(oacc);
        continueButton.setDisable(true);
    }

    /**
     * Action when button "Continue" is hit.
     *
     * @param actionEvent action!
     */
    public void setContinue(ActionEvent actionEvent) {
        String result = userManager.requestNewAccount((String) accountType.getValue());
        Alert.show("New Account Request", result);
        backToMenu(actionEvent);
    }

    /**
     * Enable continue button when user choose an account type.
     */
    public void enableContinue() {
        if (accountType.getValue() != null) continueButton.setDisable(false);
    }

    /**
     * Action when button "Cancel" is hit.
     *
     * @param actionEvent action!
     */
    public void setCancel(ActionEvent actionEvent) {
        backToMenu(actionEvent);
    }

    private void backToMenu(javafx.event.ActionEvent actionEvent) {
        try {
            ((UserController) changeScene("/gui/guiuser/UserUI-ATM.fxml")).initData(userManager);
        } catch (IOException e) {
            e.printStackTrace();
        }
        displayScene(actionEvent);
    }
}


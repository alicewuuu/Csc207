package gui.guiuser;

import gui.guimain.Alert;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import gui.guimain.Controller;
import javafx.fxml.Initializable;

import javafx.scene.control.ComboBox;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.control.*;
import main.accounts.*;
import main.managers.*;

public class PayBillController extends Controller {

    private static UserManager userManager;
    @FXML
    private Button continueButton;
    @FXML
    private ComboBox<String> PayBillAccountBox;
    @FXML
    private TextField payee;
    @FXML
    private TextField payBillAmount;

    /**
     * Initialize data needed to proceed the pay bill transaction.
     * continue button is disabled by default until user has chosen an account.
     *
     * @param userManager user manager helps handle the process
     */
    public void initData(UserManager userManager) {
        this.userManager = userManager;
        PayBillAccountBox.setItems(FXCollections.observableArrayList(
                userManager.getCurrentUser().getAllAccount()));
        continueButton.setDisable(true);
    }

    public void cancelButton(ActionEvent actionEvent) {
        backToMenu(actionEvent);
    }

    public void continueButton(ActionEvent actionEvent) {
        String[] accountID = PayBillAccountBox.getValue().split(":", 2);
        Alert.show("Pay Bill Transaction", userManager.payBill(
                accountID[1], payee.getText(), payBillAmount.getText()));
        backToMenu(actionEvent);
    }

    /**
     * Enable continue button when user choose an account type.
     */
    public void enableContinue() {
        if (PayBillAccountBox.getValue() != null) continueButton.setDisable(false);
    }

    private void backToMenu(ActionEvent actionEvent) {
        try {
            ((UserController) changeScene("/gui/guiuser/UserUI-ATM.fxml")).initData(userManager);
        } catch (IOException e) {
            e.printStackTrace();
        }
        displayScene(actionEvent);
    }

}

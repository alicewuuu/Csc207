package gui.guiuser;

import gui.guimain.Alert;
import gui.guimain.MultipleCheckBoxController;
import gui.guimain.Controller;
import main.managers.UserManager;
import main.accounts.User;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class TransferController extends Controller implements Initializable {
    /**
     * check boxes choose from internal transfer and external transfer
     */
    @FXML
    private CheckBox internal;
    @FXML
    private CheckBox external;


    /**
     * box that lists accounts to choose from
     */
    @FXML
    private ComboBox<String> fromAccount;
    @FXML
    private ComboBox myAccount;

    @FXML
    private TextField otherUsername;
    @FXML
    private TextField otherAccount;
    @FXML
    private TextField amount;

    /**
     * button to continue or cancel the transaction
     */
    @FXML
    private Button continueButton;

    private ObservableList oacc;

    private UserManager userManager;

    private User user;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    /**
     * Initialize data needed to proceed the transaction.
     * <p>
     * Continue action gets limited here. It can only be hit when either but not both of the check boxes is selected.
     *
     * @param userManager manager that helps handel the transfer process.
     */
    public void initData(UserManager userManager) {
        this.userManager = userManager;
        this.user = userManager.getCurrentUser();

        ObservableList<String> acc = FXCollections.observableArrayList(user.getTransferableAccount());
        ObservableList<String> oacc = FXCollections.observableArrayList(user.getAllAccount());
        fromAccount.setItems(acc);
//        this.oacc = FXCollections.observableArrayList(acc);
        myAccount.setItems(oacc);
        otherUsername.setVisible(false);
        otherAccount.setVisible(false);
        myAccount.setVisible(false);
        MultipleCheckBoxController mc = new MultipleCheckBoxController(internal, external);
        mc.initialize();
        continueButton.setDisable(true);
    }

    /**
     * Action when check box "Internal Transfer" is selected
     *
     * @param actionEvent action!
     */
    public void setInternal(javafx.event.ActionEvent actionEvent) {
        if (internal.isSelected()) myAccount.setVisible(true);
        else myAccount.setVisible(false);
        enableContinue(actionEvent);
    }

    /**
     * Action when check box "External Transfer" is selected
     *
     * @param actionEvent action!
     */
    public void setExternal(javafx.event.ActionEvent actionEvent) {
        if (external.isSelected()) {
            otherUsername.setVisible(true);
            otherAccount.setVisible(true);
        } else {
            otherUsername.setVisible(false);
            otherAccount.setVisible(false);
        }
        enableContinue(actionEvent);
    }

    /**
     * Action when button "Continue" is hit.
     *
     * @param actionEvent action!
     */
    public void setContinue(javafx.event.ActionEvent actionEvent) {
        String[] fromAcc = fromAccount.getValue().split(":", 2);
        String payee = (internal.isSelected()) ? user.getLogin() : otherUsername.getText();
        String accValue = (String) myAccount.getValue();
        String toAcc = (internal.isSelected()) ? (accValue).substring(accValue.indexOf(":") + 1) :
                otherAccount.getText();
        String transAmount = amount.getText();
        String result = userManager.transfer(fromAcc[1], payee, toAcc, transAmount);
        Alert.show("Transfer Funds", result);
        backToMenu(actionEvent);
    }

    /**
     * Enable continue button when user choose the account types and one of the boxes is checked.
     */
    public void enableContinue(javafx.event.ActionEvent actionEvent) {
        if ((fromAccount.getValue() != null) &&
                ((internal.isSelected() && myAccount.getValue() != null) || external.isSelected())) {
            continueButton.setDisable(false);
        } else continueButton.setDisable(true);
    }

    /**
     * Action when button "Cancel" is hit.
     *
     * @param actionEvent action!
     */
    public void setCancel(javafx.event.ActionEvent actionEvent) {
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


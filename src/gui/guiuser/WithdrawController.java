package gui.guiuser;

import gui.guimain.Alert;
import gui.guimain.Controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import main.accounts.User;
import main.managers.UserManager;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class WithdrawController extends Controller {

    private UserManager userManager;
    @FXML
    private Button continueButton;
    @FXML
    private ComboBox<String> withdrawAccountBox;
    @FXML
    private TextField withdrawAmount;

    /**
     * Initializes data needed to proceed the withdraw.
     * Continue button is disables by default until user has chosen an account.
     *
     * @param userManager user manager helps handle the process
     */
    public void initData(UserManager userManager) {
        this.userManager = userManager;
        withdrawAccountBox.setItems(FXCollections.observableArrayList(userManager.getCurrentUser().
                getAllAccount()));
        continueButton.setDisable(true);
    }

    public void cancelButton(javafx.event.ActionEvent actionEvent) throws IOException {
        backToMenu(actionEvent);

    }

    public void continueButton(ActionEvent actionEvent) throws IOException {
        String[] accountID = withdrawAccountBox.getValue().split(":", 2);
        Alert.show("Withdraw Transaction", userManager.withdraw(accountID[1], withdrawAmount.getText()));
        backToMenu(actionEvent);
    }

    /**
     * Enable continue button when user choose an account type.
     */
    public void enableContinue() {
        if (withdrawAccountBox.getValue() != null) {
            continueButton.setDisable(false);
        }
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

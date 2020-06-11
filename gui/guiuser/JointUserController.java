package gui.guiuser;

import gui.guimain.Alert;
import gui.guimain.Controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.managers.UserManager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class JointUserController extends Controller implements Initializable {

    /**
     * label the information needed
     */
    @FXML
    private TextField username;

    @FXML
    private ComboBox account;

    @FXML
    private Button continueButton;

    private UserManager userManager;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    /**
     * Initialize data needed to proceed the request.
     * Continue button is disabled by default until user has chosen an account type.
     *
     * @param userManager user manager handles the request process.
     */
    public void initData(UserManager userManager) {
        this.userManager = userManager;
        List acc = new ArrayList();
        for (int i = 0; i < userManager.getCurrentUser().getAccountList().size(); i++) {
            String currAcc = userManager.getCurrentUser().getAccountList().get(i).toStringAccountType();
            acc.add(currAcc);
        }
        ObservableList oacc = FXCollections.observableArrayList(acc);
        account.setItems(oacc);
        continueButton.setDisable(true);
    }

    /**
     * Action when button "Continue" is hit.
     *
     * @param actionEvent action!
     */
    public void setContinue(ActionEvent actionEvent) {
        String accSelected = (String) account.getValue();
        String result = userManager.requestJointUser(accSelected.substring(accSelected.indexOf("-") + 2),
                username.getText());
        Alert.show("Joint User Request", result);
        backToMenu(actionEvent);
    }

    /**
     * Enable continue button when user choose an account type.
     */
    public void enableContinue(ActionEvent actionEvent) {
        if (account.getValue() != null) continueButton.setDisable(false);
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


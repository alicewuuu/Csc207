package gui.guimanager;

import gui.guimain.Controller;
import gui.guimain.Alert;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import main.managers.ManagerManager;

import java.io.IOException;

public class FreezeController extends Controller {

    /** TextField used to get the userId */
    @FXML
    private TextField user;

    /** TextField used to get the accountId */
    @FXML
    private TextField account;

    /** Manager manager handling Bank Manager action */
    private ManagerManager managerManager;

    /**
     * Initialize data needed to set for Bank Manager.
     * @param managerManager ManagerManager handling Freeze action.
     */
    public void initData(ManagerManager managerManager){
        this.managerManager = managerManager;
    }

    /**
     * Action when the event on freeze button is detected.
     * It will freeze the user with matched account id to system and set the scene back to BankManger Scene.
     */
    public void setFreeze(ActionEvent actionEvent){
        Alert.show("Result", this.managerManager.freeze(user.getText(), account.getText(), true));
        try {
            ((BankManagerController) changeScene("/gui/guimanager/BankManager.fxml")).initData(this.managerManager);
        } catch (IOException e) {
            e.printStackTrace();
        }
        displayScene(actionEvent);
    }

    /**
     * Action when the event on unfreeze button is detected.
     * It will unfreeze the user with matched account id to system and set the scene back to BankManger Scene.
     */
    public void setUnfreeze(ActionEvent actionEvent){
        Alert.show("Result", this.managerManager.freeze(user.getText(), account.getText(), false));
        try {
            ((BankManagerController) changeScene("/gui/guimanager/BankManager.fxml")).initData(this.managerManager);
        } catch (IOException e) {
            e.printStackTrace();
        }
        displayScene(actionEvent);
    }
}
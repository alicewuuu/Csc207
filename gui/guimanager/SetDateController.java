package gui.guimanager;

import gui.guimain.Controller;
import gui.guimain.Alert;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import main.managers.ManagerManager;

import java.io.IOException;

public class SetDateController extends Controller {

    /** Manager manager handling Bank Manager action */
    private ManagerManager managerManager;

    /** TextField used to get the system year */
    @FXML
    private TextField year;

    /** TextField used to get the system month */
    @FXML
    private TextField month;

    /** TextField used to get the system day */
    @FXML
    private TextField day;


    /**
     * Initialize data needed to set for Set Date.
     * @param managerManager ManagerManager handling setDate action.
     */
    public void initData(ManagerManager managerManager) {
        this.managerManager = managerManager;

    }

    /**
     * Action when the event on back button is detected.
     * It will back to the Bank Manager scene/
     */
    public void SetBack(javafx.event.ActionEvent actionEvent) {
        try {
            ((BankManagerController) changeScene("/gui/guimanager/BankManager.fxml")).initData(this.managerManager);
        } catch (IOException e) {
            e.printStackTrace();
        }
        displayScene(actionEvent);
    }

    /**
     * Action when the event on setDate button is detected.
     * It will set the date to system and back to the Bank Manager scene/
     */
    public void SetDate(javafx.event.ActionEvent actionEvent) {
        if (this.managerManager != null) {
            Alert.show("Result", this.managerManager.setDate(month.getText(), day.getText(), year.getText()));
            try {
                ((BankManagerController) changeScene("/gui/guimanager/BankManager.fxml")).initData(this.managerManager);
            } catch (IOException e) {
                e.printStackTrace();
            }
            displayScene(actionEvent);
        } else {
            Alert.show("Result", "The date has already been set");
        }
    }
}
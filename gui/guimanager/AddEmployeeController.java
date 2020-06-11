package gui.guimanager;

import gui.guimain.Controller;
import gui.guimain.Alert;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import main.managers.ManagerManager;

import java.io.IOException;

public class AddEmployeeController extends Controller {

    /** ManagerManager that handling information in add employee scene */
    private ManagerManager managerManager;

    /** TextField used to create the user name for employee */
    @FXML
    private TextField username;

    /** TextField used to create the password for employee */
    @FXML
    private TextField password;

    /**
     * Initialize data needed to proceed the deposit.
     * @param managerManager Manager manager handling add employee action.
     */
    public void initData(ManagerManager managerManager){
        this.managerManager = managerManager;
    }

    /**
     * Action when event on approve button is detected.
     * It will add the employee to system and bank to the BankManager Scene.
     */
    public void setAddEmployee(ActionEvent actionEvent){
        Alert.show("Result", this.managerManager.addEmployee(this.username.getText(), this.password.getText()));
        try {
            ((BankManagerController) changeScene("/gui/guimanager/BankManager.fxml")).initData(this.managerManager);
        } catch (IOException e) {
            e.printStackTrace();
        }
        displayScene(actionEvent);
    }

    /**
     * Action when event on back button is detected.
     * It will not process the new employee and back to the Bank Manager Scene.
     */
    public void setBack(ActionEvent actionEvent){
        try {
            ((BankManagerController) changeScene("/gui/guimanager/BankManager.fxml")).initData(this.managerManager);
        } catch (IOException e) {
            e.printStackTrace();
        }
        displayScene(actionEvent);
    }
}
package gui.guiemployee;

import gui.guimain.Controller;
import gui.guimain.Alert;
import gui.guimanager.BankManagerController;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import main.managers.EmployeeManager;
import main.managers.ManagerManager;

import java.io.IOException;

public class RestockController extends Controller {

    /**
     * EmployeeManager that handling information in Restock scene
     */
    private EmployeeManager employeeManager;

    /**
     * TextField used to get the country
     */
    @FXML
    private TextField country;

    /**
     * TextField used to get the type of denomination
     */
    @FXML
    private TextField denomination;

    /**
     * TextField used to get the number of denominations
     */
    @FXML
    private TextField num;

    /**
     * indicate whether the previous scene is from manager menu or employee menu
     */
    private String identity;

    /**
     * Initialize data needed to set for Employee.
     *
     * @param employeeManager EmployeeManager handling restock action.
     */
    public void initData(EmployeeManager employeeManager, String identity) {
        this.employeeManager = employeeManager;
        this.identity = identity;
    }

    /**
     * Action when the event on restock button is detected.
     * It will make the process and show the result
     */
    public void setRestock() {
        Alert.show("Result", employeeManager.restock(country.getText(), denomination.getText(), num.getText()));
    }

    /**
     * Action when the event on back button is detected.
     * It will back to the Bank Manager scene/
     */
    public void setBack(javafx.event.ActionEvent actionEvent) {
        if (identity.equals("manager")) {
            try {
                ((BankManagerController) changeScene("/gui/guimanager/BankManager.fxml")).initData((
                        ManagerManager) this.employeeManager);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                ((EmployeeController) changeScene("/gui/guiemployee/Employee.fxml")).
                        initData(this.employeeManager);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        displayScene(actionEvent);
    }
}
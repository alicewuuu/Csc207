package gui.guiemployee;

import gui.guimain.Controller;
import gui.guimain.Alert;
import gui.guimain.LoginPane;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import javafx.event.ActionEvent;
import javafx.stage.Stage;
import main.managers.EmployeeManager;
import main.managers.MainManager;
import main.managers.ManagerManager;

import java.io.IOException;

public class EmployeeController extends Controller {

    /**
     * Button to be hit to change to check new account request Scene
     */
    @FXML
    private Button new_account;

    /**
     * Button to be hit to change to add stock Scene
     */
    @FXML
    private Button restock;

    /**
     * Button to be hit to change to shut down the system
     */
    @FXML
    private Button shut_down;

    /**
     * Button to be hit to change to back to Login scene
     */
    @FXML
    private Button logOut;

    /**
     * Employee Manager handling Employee Action
     */
    private EmployeeManager employeeManager;

    /**
     * Initialize data needed to set for Employee.
     *
     * @param mm EmployeeManager handling Employee action.
     */
    public void initData(EmployeeManager mm) {
        this.employeeManager = mm;

        if (mm.getSystemInfo().getDate() == null) {
            shut_down.setDisable(true);
            restock.setDisable(true);
            new_account.setDisable(true);
        }
    }

    /**
     * Action when the event on new_account button is detected.
     * It will set the scene to check new account scene.
     */
    public void setAccountRequests(ActionEvent actionEvent) {
        if (this.employeeManager.getSystemInfo().getNumAccount() != 0) {
            try {
                ((ApproveNewAccountController) changeScene("/gui/guiemployee/ApproveNewAccount.fxml")).
                        initData(this.employeeManager, "employee");
            } catch (IOException e) {
                e.printStackTrace();
            }
            displayScene(actionEvent);

        } else {
            Alert.show("Result", "There is no new account request.");
        }
    }

    /**
     * Action when the event on restock button is detected.
     * It will set the scene to add stock scene.
     */
    public void setRestockScene(ActionEvent actionEvent) {
        try {
            ((RestockController) changeScene("/gui/guiemployee/Restock.fxml")).initData(
                    new ManagerManager(this.employeeManager.getSystemInfo()), "employee");
        } catch (IOException e) {
            e.printStackTrace();
        }
        displayScene(actionEvent);

    }

    /**
     * Action when the event on log_out button is detected.
     * It will set the scene back to the Login scene.
     */
    public void logOutAction() throws Exception {
        Stage stage = (Stage) logOut.getScene().getWindow();
        // do what you have to do
        stage.close();
        new LoginPane().start(new MainManager(employeeManager.getSystemInfo()));
    }

    /**
     * Action when the event on shut_down button is detected.
     * It will store information and shut down the system.
     */
    public void closeButtonAction() {
        this.employeeManager.shutDown();
        Stage stage = (Stage) shut_down.getScene().getWindow();
        stage.close();
    }

}

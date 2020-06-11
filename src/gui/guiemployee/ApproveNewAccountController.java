package gui.guiemployee;

import gui.guimain.Controller;
import gui.guimain.Alert;
import gui.guimanager.BankManagerController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Pair;
import main.managers.EmployeeManager;
import main.managers.ManagerManager;

import java.io.IOException;

public class ApproveNewAccountController extends Controller {

    /**
     * Existing user text for the ApproveNewAccount scene
     */
    @FXML
    private Label user;

    /**
     * account text that want to be added for the ApproveNewAccount scene
     */
    @FXML
    private Label account;

    /**
     * Employee manager handling ApproveNewAccount action
     */
    private EmployeeManager employeeManager;

    private String userId;

    private String type;

    /**
     * indicate whether the previous scene is from manager menu or employee menu
     */
    private String identity;


    /**
     * Initialize data needed to set for Employee Manager to process new account request.
     *
     * @param employeeManager EmployeeManager handling ApproveNewAccount action.
     */
    public void initData(EmployeeManager employeeManager, String identity) {
        this.employeeManager = employeeManager;
        this.identity = identity;
        Pair<String, String> pair1 = this.employeeManager.getSystemInfo().getPendingAccs();

        user.setText(pair1.getValue());
        account.setText(pair1.getKey());

        this.userId = pair1.getValue();
        this.type = pair1.getKey();
    }

    /**
     * Action when the event on approve button is detected.
     * It will add the new account to system and set the scene back to check new account scene if there is
     * still new request.
     */
    public void setApproved(ActionEvent actionEvent) {
        Alert.show("Result", this.employeeManager.processAccountReq(this.userId, this.type, "true"));
        if (this.employeeManager.getSystemInfo().getNumAccount() != 0) {
            try {
                ((ApproveNewAccountController) changeScene("/gui/guiemployee/ApproveNewAccount.fxml")).
                        initData(this.employeeManager, identity);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            if (identity.equals("manager")) {
                try {
                    ((BankManagerController) changeScene("/gui/guimanager/BankManager.fxml")).
                            initData(new ManagerManager(this.employeeManager.getSystemInfo()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    ((EmployeeController) changeScene("/gui/guiemployee/Employee.fxml")).
                            initData(new ManagerManager(this.employeeManager.getSystemInfo()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        displayScene(actionEvent);
    }

    /**
     * Action when the event on disapprove button is detected.
     * It will remove the new account request from system and set the scene back to check new account scene if there is
     * still new request.
     */
    public void setDisapproved(ActionEvent actionEvent) {
        Alert.show("Result", this.employeeManager.processAccountReq(this.userId, this.type, "false"));
        try {
            ((BankManagerController) changeScene("/gui/guimanager/BankManager.fxml")).
                    initData(new ManagerManager(this.employeeManager.getSystemInfo()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        displayScene(actionEvent);
    }
}
package gui.guimanager;

import gui.guimain.Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Pair;
import main.managers.ManagerManager;
import main.accounts.Account;
import main.accounts.User;
import gui.guimain.Alert;

import java.io.IOException;

public class ApproveNewJointController extends Controller {

    /** Label for Existing user text for the ApproveNewJoint scene */
    @FXML
    private Label user;

    /** Label for Joint account text for the ApproveNewJoint scene */
    @FXML
    private Label account;

    /** ManagerManager that handling information in add employee scene */
    private ManagerManager managerManager;

    /** Account that store account information */
    private Account acc;

    /** User that store user information */
    private User us;

    /**
     * Initialize data needed to proceed the new joint account request.
     * @param managerManager Manager manager handling joint account request action.
     */
    public void initData(ManagerManager managerManager){
        this.managerManager = managerManager;

        Pair<Account, User> pair1 = this.managerManager.getSystemInfo().getPendingJointUser();

        account.setText(pair1.getKey().getId());
        user.setText(pair1.getValue().getLogin());

        this.acc = pair1.getKey();
        this.us = pair1.getValue();
    }

    /**
     * Action when the event on approve button is detected.
     * It will add the joint account to system and set the scene back to check new joint scene if there is
     * still new request.
     */
    public void setApproved(ActionEvent actionEvent) {
        Alert.show("Result", this.managerManager.processJointUserReq(this.acc, this.us, "true"));
            if ( this.managerManager.getSystemInfo().getNumJoint()!= 0) {
                try {
                    ((ApproveNewJointController) changeScene("/gui/guimanager/ApproveNewJoint.fxml")).initData(this.managerManager);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                displayScene(actionEvent);
            } else {
                Alert.show("Result", "There is no new Joint Requests");
                try {
                    ((BankManagerController) changeScene("/gui/guimanager/BankManager.fxml")).initData(this.managerManager);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                displayScene(actionEvent);
            }
    }

    /**
     * Action when the event on disapprove button is detected.
     * It will remove the joint account request from the system and back to the joint Scene if there still
     * has request.
     */
    public void setDisapproved(ActionEvent actionEvent){
        Alert.show("Result", this.managerManager.processJointUserReq(this.acc, this.us, "false"));
        if ( this.managerManager.getSystemInfo().getNumJoint()!= 0) {
            try {
                ((ApproveNewJointController) changeScene("/gui/guimanager/ApproveNewJoint.fxml")).initData(this.managerManager);
            } catch (IOException e) {
                e.printStackTrace();
            }
            displayScene(actionEvent);
        } else {
            Alert.show("Result", "There is no new Joint Requests");
            try {
                ((BankManagerController) changeScene("/gui/guimanager/BankManager.fxml")).initData(this.managerManager);
            } catch (IOException e) {
                e.printStackTrace();
            }
            displayScene(actionEvent);
        }
    }
}
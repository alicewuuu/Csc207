package gui.guimanager;

import gui.guimain.Controller;
import gui.guimain.Alert;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import main.managers.ManagerManager;

import java.io.IOException;

public class ApproveNewUserController extends Controller {

    /** Label for new user text for the ApproveNewUser scene */
    @FXML
    private Label newUser;

    /** ManagerManager that handling the ApproveNewUser scene */
    private ManagerManager managerManager;

    /** String used to store user information */
    private String User;

    /**
     * Initialize data needed to proceed the new user request.
     * @param managerManager Manager manager handling new user request action.
     */
    public void initData(ManagerManager managerManager){

        this.managerManager = managerManager;

        User = this.managerManager.getSystemInfo().getPendingUser();

        newUser.setText(User + " request to be a new user.");
    }

    /**
     * Action when the event on approve button is detected.
     * It will add the new user to system and set the scene back to check new user scene if there is
     * still new request.
     */
    public void setApproved(ActionEvent actionEvent) {
        Alert.show("Result", this.managerManager.processUserReq(User, "approved"));
            if ( this.managerManager.getSystemInfo().getNumUser() != 0) {
                try {
                    ((ApproveNewUserController) changeScene("/gui/guimanager/ApproveNewUser.fxml")).initData(this.managerManager);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                displayScene(actionEvent);
            } else {
                Alert.show("Result", "There is no new User Requests");
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
     * It will remove the new user request from system and set the scene back to check new user scene if there is
     * still new request.
     */
    public void setDisapproved(ActionEvent actionEvent) {
        Alert.show("Result", this.managerManager.processUserReq(User, "disapproved"));
            if ( this.managerManager.getSystemInfo().getNumUser() != 0) {
                try {
                    ((ApproveNewUserController) changeScene("/gui/guimanager/ApproveNewUser.fxml")).initData(this.managerManager);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                displayScene(actionEvent);
            } else {
                Alert.show("Result", "There is no new User Requests");
                try {
                    ((BankManagerController) changeScene("/gui/guimanager/BankManager.fxml")).initData(this.managerManager);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                displayScene(actionEvent);
            }
    }
}
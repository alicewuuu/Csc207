package gui.guimanager;

import gui.guiemployee.*;
import gui.guimain.Controller;
import gui.guimain.Alert;
import gui.guimain.LoginPane;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;

import main.managers.MainManager;
import main.managers.ManagerManager;

public class BankManagerController extends Controller {

    /** Image of restock icon*/
    public javafx.scene.image.ImageView restockIcon;

    /** image of add employee icon*/
    public ImageView addIcon;

    /** image of freeze icon*/
    public ImageView freezeIcon;

    /** image of shut down icon*/
    public ImageView shutDownIcon;

    /**
     * Button to be hit to change to new_account Scene
     */
    @FXML
    private Button new_account;

    /** Button to be hit to change to check joint user Scene*/
    @FXML
    private Button joint;

    /** Button to be hit to change to freeze user Scene*/
    @FXML
    private Button freeze;

    /** Button to be hit to change to add employee Scene*/
    @FXML
    private Button employee;

    /** Button to be hit to change to check undo transaction Scene*/
    @FXML
    private Button undo;

    /** Button to be hit to change to stock Scene*/
    @FXML
    private Button restock;

    /** Button to be hit to change to check new user Scene*/
    @FXML
    private Button new_user;

    /** Button to be hit to shut down the system and store all information so far*/
    @FXML
    private Button shut_down;

    /** Manager manager handling Bank Manager action */
    private ManagerManager managerManager;

    /**
     * Initialize data needed to set for Bank Manager.
     * @param mm ManagerManager handling BankManager action.
     */
    public void initData(ManagerManager mm) {
        this.managerManager = mm;

        System.out.println();

        if (mm.getSystemInfo().getDate() == null){
            shut_down.setDisable(true);
            new_user.setDisable(true);
            restock.setDisable(true);
            undo.setDisable(true);
            new_account.setDisable(true);
            joint.setDisable(true);
            freeze.setDisable(true);
            employee.setDisable(true);
            freezeIcon.setOpacity(0.3);
            addIcon.setOpacity(0.3);
            restockIcon.setOpacity(0.3);
            shutDownIcon.setOpacity(0.3);

        }
    }

    /**
     * Action when the event on new_user button is detected.
     * It will set the scene to check new user scene.
     */
    public void setUserRequestsScene(ActionEvent actionEvent){
        if (this.managerManager.getSystemInfo().getNumUser() != 0) {
                try {
                    ((ApproveNewUserController) changeScene("/gui/guimanager/ApproveNewUser.fxml")).initData(this.managerManager);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                displayScene(actionEvent);
        }
        else {
            Alert.show("Result", "There is no new User Requests");
        }
    }

    /**
     * Action when the event on new_account button is detected.
     * It will set the scene to check new account scene.
     */
    public void setAccountRequestsScene(ActionEvent actionEvent){
        if (this.managerManager.getSystemInfo().getNumAccount() != 0) {
            try {
                ((ApproveNewAccountController) changeScene("/gui/guiemployee/ApproveNewAccount.fxml")).initData(this.managerManager, "manager");
            } catch (IOException e) {
                e.printStackTrace();
            }
            displayScene(actionEvent);
        } else{
            Alert.show("Result", "There is no new account request.");
        }
    }

    /**
     * Action when the event on undo button is detected.
     * It will set the scene to check undo transaction scene.
     */
    public void setUndoTransactScene(ActionEvent actionEvent) {
        if (this.managerManager.getSystemInfo().getNumUndo() != 0) {
            try {
                ((UndoTransactController) changeScene("/gui/guimanager/Undo.fxml")).initData(this.managerManager);
            } catch (IOException e) {
                e.printStackTrace();
            }
            displayScene(actionEvent);
        } else{
                Alert.show("Result", "There is no transaction can be undo.");
        }
    }

    /**
     * Action when the event on joint button is detected.
     * It will set the scene to check new joint user scene.
     */
    public void setJointScene(ActionEvent actionEvent){
        if (this.managerManager.getSystemInfo().getNumJoint() != 0){
            try {
                ((ApproveNewJointController) changeScene("/gui/guimanager/ApproveNewJoint.fxml")).initData(this.managerManager);
            } catch (IOException e) {
                e.printStackTrace();
            }
            displayScene(actionEvent);
        } else{
            Alert.show("Result", "There is no new Joint request.");
        }
    }

    /**
     * Action when the event on restock button is detected.
     * It will set the scene to add stock scene.
     */
    public void setRestockScene(ActionEvent actionEvent){
        try {
            ((RestockController) changeScene("/gui/guiemployee/Restock.fxml")).initData(this.managerManager, "manager");
        } catch (IOException e) {
            e.printStackTrace();
        }
        displayScene(actionEvent);
    }

    /**
     * Action when the event on system_date button is detected.
     * It will set the scene to set system date scene.
     */
    public void setSetDateScene(ActionEvent actionEvent){
        if (this.managerManager.getSystemInfo().getDate() == null) {
            try {
                ((SetDateController) changeScene("/gui/guimanager/SetDate.fxml")).initData(this.managerManager);
            } catch (IOException e) {
                e.printStackTrace();
            }
            displayScene(actionEvent);
        } else {
            Alert.show("Result", "The date has already been set.");
        }
    }

    /**
     * Action when the event on log_out button is detected.
     * It will set the scene back to the Login scene.
     */
    public void setLogOutAction() throws Exception {
        //MainManager mainManager = this.managerManager.logOut();
        new LoginPane().start(new MainManager(managerManager.getSystemInfo()));
    }

    /**
     * Action when the event on shut_down button is detected.
     * It will store information and shut down the system.
     */
    public void closeButtonAction(){
        Alert.show("result", this.managerManager.shutDown());
        Stage stage = (Stage) shut_down.getScene().getWindow();
        stage.close();
    }

    /**
     * Action when the event on employee button is detected.
     * It will set the scene to add new employee scene.
     */
    public void addEmployeeAction(ActionEvent actionEvent) {
        try {
            ((AddEmployeeController) changeScene("/gui/guimanager/AddEmployee.fxml")).initData(this.managerManager);
        } catch (IOException e) {
            e.printStackTrace();
        }
        displayScene(actionEvent);
    }

    /**
     * Action when the event on freeze button is detected.
     * It will set the scene to freeze user account scene.
     */
    public void setFreezeScene(ActionEvent actionEvent) {
        try {
            ((FreezeController) changeScene("/gui/guimanager/Freeze.fxml")).initData(this.managerManager);
        } catch (IOException e) {
            e.printStackTrace();
        }
        displayScene(actionEvent);
    }
}


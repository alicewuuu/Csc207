package gui.guimanager;

import gui.guimain.Controller;
import gui.guimain.Alert;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import main.accounts.Undoable;
import main.managers.ManagerManager;

import java.io.IOException;

public class UndoTransactController extends Controller {

    /** Label used for showing the transaction */
    @FXML
    private Label transaction;

    /** ManagerManger that handling information in UndoTransaction */
    private ManagerManager managerManager;

    /** Undoable used to store undo transaction */
    private Undoable trans;

    /**
     * Initialize data needed to set for UndoTransaction.
     * @param managerManager ManagerManager handling undoTransaction action.
     */
    public void initData(ManagerManager managerManager){
        this.managerManager = managerManager;
        trans = this.managerManager.getSystemInfo().getPendingUndo();

        transaction.setText(trans.toString());

    }

    /**
     * Action when the event on approve button is detected.
     * It will undo the last transaction to system and set the scene back to check undo transaction scene if there is
     * still new request.
     */
    public void setApproved(ActionEvent actionEvent) {
        Alert.show("Result", this.managerManager.processUndoTransact(this.trans, "true"));
            if ( this.managerManager.getSystemInfo().getNumUndo() != 0) {
                try {
                    ((UndoTransactController) changeScene("/gui/guimanager/Undo.fxml")).initData(this.managerManager);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                displayScene(actionEvent);
            } else {
                Alert.show("Result", "There is no new transaction Requests");
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
     * It will remove the request from system and set the scene back to check new joint scene if there is
     * still new request.
     */
    public void setDisapproved(ActionEvent actionEvent) {
        Alert.show("Result", this.managerManager.processUndoTransact(this.trans, "false"));
            if ( this.managerManager.getSystemInfo().getNumUndo() != 0) {
                try {
                    ((UndoTransactController) changeScene("/gui/guimanager/Undo.fxml")).initData(this.managerManager);
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
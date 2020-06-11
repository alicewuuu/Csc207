//<<<<<<< Updated upstream
package gui.guiuser;

import gui.guimain.Alert;
import gui.guimain.HBoxCell;
import gui.guimain.LoginController;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.accounts.Account;
import main.managers.UserManager;
import main.accounts.User;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import gui.guimain.Controller;

public class UserController extends Controller implements Initializable {
    @FXML
    private Label greeting;

    @FXML
    private ListView accountList;

    @FXML
    private Label balance;

    UserManager userManager;

    User user;

    public void initialize(URL location, ResourceBundle resources) {
    }

    public void initData(UserManager userManager) {
        this.userManager = userManager;
        this.user = userManager.getCurrentUser();
        greeting.setText("Welcome, " + user.getLogin() + "!");
        balance.setText(" Net Balance: \n " + " " + user.getNetBalance());
        setAccountList();
    }

    public void setAccountList() {
        List<HBoxCell> list = new ArrayList<>();
        for (int i = 0; i < userManager.getCurrentUser().getAccountList().size(); i++) {
            Account currAcc = userManager.getCurrentUser().getAccountList().get(i);
            Button history = new Button("View History");
            history.setOnAction(e -> {
                try {
                    ViewHistoryPane.start(currAcc, userManager);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            });
            list.add(new HBoxCell(currAcc.toStringAccountType(), history));
        }
        ObservableList<HBoxCell> myObservableList = FXCollections.observableList(list);
        accountList.setItems(myObservableList);
    }

    public void setPayBillScene(ActionEvent actionEvent) {
        try {
            ((PayBillController) changeScene("/gui/guiuser/PayBill.fxml")).initData(userManager);
        } catch (IOException e) {
            e.printStackTrace();
        }
        displayScene(actionEvent);
    }

    public void setTransferScene(ActionEvent actionEvent) {
        try {
            ((TransferController) changeScene("/gui/guiuser/Transfer.fxml")).initData(userManager);
        } catch (IOException e) {
            e.printStackTrace();
        }
        displayScene(actionEvent);
    }

    public void setDepositScene(ActionEvent actionEvent) {
        try {
            ((DepositController) changeScene("/gui/guiuser/Deposit.fxml")).initData(userManager);
        } catch (IOException e) {
            e.printStackTrace();
        }
        displayScene(actionEvent);
    }

    public void setPasswordScene(ActionEvent actionEvent) {
        try {
            ((ResetPasswordController) changeScene("/gui/guiuser/ResetPassword.fxml")).initData(userManager);
        } catch (IOException e) {
            e.printStackTrace();
        }
        displayScene(actionEvent);
    }

    public void setWithdrawScene(ActionEvent actionEvent) {
        try {
            ((WithdrawController) changeScene("/gui/guiuser/Withdraw.fxml")).initData(userManager);
        } catch (IOException e) {
            e.printStackTrace();
        }
        displayScene(actionEvent);
    }

    public void setGambleScene(ActionEvent actionEvent) {
        try {
            if (this.user.hasGamblingAccount()) {
                ((GambleController) changeScene("/gui/guiuser/Gamble.fxml")).initData(userManager);
                displayScene(actionEvent);
            } else {
                Alert.show(
                        "Warning", "You currently do not have an Gambling Account. " +
                                "Request one for free to play the game now!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // TODO: should be consistent with manager controller
    public void setLogoutScene(ActionEvent actionEvent) {
        Parent tableViewParent = null;
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/guimain/Login.fxml"));
            tableViewParent = loader.load();
            LoginController controller = loader.getController();
            controller.initData(userManager.logOut());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene tableViewScene = new Scene(tableViewParent);
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
    }

    public void setNewAccount(ActionEvent actionEvent) {
        try {
            ((RequestNewAccountController) changeScene("/gui/guiuser/RequestNewAccount.fxml")).
                    initData(userManager);
        } catch (IOException e) {
            e.printStackTrace();
        }
        displayScene(actionEvent);
    }

    public void setJointUser(ActionEvent actionEvent) {
        try {
            ((JointUserController) changeScene("/gui/guiuser/JointUser.fxml")).initData(userManager);
        } catch (IOException e) {
            e.printStackTrace();
        }
        displayScene(actionEvent);
    }
}

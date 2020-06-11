package gui.guiuser;

import gui.guimain.Alert;
import gui.guimain.HBoxCell;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import main.accounts.Account;
import main.accounts.Undoable;
import main.managers.UserManager;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * A class representing scene that shows account's transaction history.
 * Users can view and request for undoing his recent transactions here.
 */
public class ViewHistoryController implements Initializable {

    /**
     * Title of the scene, including account id and type by default
     */
    @FXML
    private Label title;

    @FXML
    private Label acctInfo;

    /**
     * A visualized list of recent transactions
     */
    @FXML
    private ListView transactionHistory;

    /**
     * Button to exit current page and turns back to user menu
     */
    @FXML
    private Button cancelButton;

    /**
     * User manager that handles submitting request for transaction undo
     */
    private UserManager userManager;

    /**
     * Current account
     */
    private Account account;

    /**
     * Start displaying the page.
     * Actions of elements in the scene is being set here.
     * The list view will display transaction history of current account and button of undoing is available to the
     * right.
     * Once the cancel button is hit, it will exit current page and return back to user menu.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        title.setText(account.toStringAccountType());
        acctInfo.setText(account.toStringForShow());

        List<HBoxCell> list = new ArrayList<>();
        for (int i = 0; i < userManager.getRecentTransactions(account.getId()).size(); i++) {
            Button undo = new Button("Undo");
            int finalI = i;
            if (!(userManager.getRecentTransactions(account.getId()).get(i) instanceof Undoable)) {
                undo.setDisable(true);
            }
            undo.setOnAction(e -> {
                        Alert.show("Undo Transaction", "Your request has been submitted!");
                        userManager.requestUndo(finalI);
                        closeButtonAction();
                    }
            );
            list.add(new HBoxCell(userManager.getRecentTransactions(account.getId()).get(i).toString(), undo));
        }

        transactionHistory.setItems(FXCollections.observableList(list));

        cancelButton.setOnAction(e -> closeButtonAction());
    }

    /**
     * Initialize data needed to display and undo the transactions.
     *
     * @param account current account
     * @param userM   user manager that handles transaction undo.
     */
    public void initData(Account account, UserManager userM) {
        this.account = account;
        // set acc
        this.userManager = userM;
    }

    /**
     * Exits the current page.
     */
    private void closeButtonAction() {
        // get a handle to the stage
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        // do what you have to do
        stage.close();
    }
}

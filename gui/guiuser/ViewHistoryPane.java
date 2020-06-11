package gui.guiuser;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.accounts.Account;
import main.managers.UserManager;

/**
 * A class representing a new scene to view current account's transaction history.
 * <p>
 * A new stage will be created when entering into this pane, so that it will be in a new window when displaying.
 */
public class ViewHistoryPane {

    /**
     * Displaying the view history scene.
     * It shows the info in ViewHistory.fxml
     * <p>
     * A new stage is created here so the scene will be displayed in a separated window.
     *
     * @param account     current account that involved in the transactions displayed.
     * @param userManager a manager helps handle requests on undoing history transactions.
     */
    public static void start(Account account, UserManager userManager) throws Exception {
        Stage transfer = new Stage();

        FXMLLoader loader = new FXMLLoader(ViewHistoryPane.class.getResource("ViewHistory.fxml"));

        ViewHistoryController controller = new ViewHistoryController();
        controller.initData(account, userManager);

        loader.setController(controller);

        VBox root = loader.load();

        Scene scene = new Scene(root, 600, 500);

        transfer.setTitle("Account History");
        transfer.setScene(scene);
        transfer.show();
    }
}
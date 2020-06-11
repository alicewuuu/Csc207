package main.system;

import javafx.application.Application;
import javafx.stage.Stage;
import main.managers.AccountManager;
import gui.guimain.LoginPane;
import main.managers.CashManager;
import main.managers.MainManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * the out most system of the machine.
 */
public class ControlSystem extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {
        SerializationProcessor processor = new SerializationProcessor();
        SystemInfo systemInfo = processor.getSystemInfo();
        if (systemInfo == null) {
            // initial an ATM machine
            Map<String, Map<Integer, Integer>> stock = new HashMap<>();
            Map<Integer, Integer> stockInCan = new HashMap<>();
            stockInCan.put(5, 20);
            stockInCan.put(10, 20);
            stockInCan.put(20, 20);
            stockInCan.put(50, 20);
            stock.put("Canadian", stockInCan);
            CashManager cm = new CashManager(stock);

            // add account types
            ArrayList<String> accountTypes = new ArrayList<>();
            accountTypes.add("Credit Card Account");
            accountTypes.add("Line of Credit Account");
            accountTypes.add("Chequing Account");
            accountTypes.add("Savings Account");
            accountTypes.add("Gambling Account");

            AccountManager am = new AccountManager();

            // set up the system
            systemInfo = new SystemInfo(cm, accountTypes, am);
        }
        MainManager mainManager = new MainManager(systemInfo);
        LoginPane loginPane = new LoginPane();
//            Parent tableViewParent = FXMLLoader.load(getClass().getResource("PayBill.fxml"));
        loginPane.start(mainManager);

    }

    public static void main(String[] args) {
        launch(args);
    }
}

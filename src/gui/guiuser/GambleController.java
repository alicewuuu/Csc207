package gui.guiuser;

import gui.guimain.Alert;
import gui.guimain.Controller;
import main.managers.UserManager;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GambleController extends Controller implements Initializable {

    @FXML
    private TextField riskLevel;
    @FXML
    private TextField amount;

    @FXML
    private Button help;

    @FXML
    private Label explanation;

    private UserManager userManager;

    private boolean isShown = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void initData(UserManager userManager) {
        this.userManager = userManager;
    }

    public void setCancel(javafx.event.ActionEvent actionEvent) {
        backToMenu(actionEvent);
    }

    public void setContinue(javafx.event.ActionEvent actionEvent) {
        String result = userManager.gamble(riskLevel.getText(), amount.getText());
        Alert.show("Result", result);
        backToMenu(actionEvent);
    }

    private void backToMenu(javafx.event.ActionEvent actionEvent) {
        try {
            ((UserController) changeScene("/gui/guiuser/UserUI-ATM.fxml")).initData(userManager);
        } catch (IOException e) {
            e.printStackTrace();
        }
        displayScene(actionEvent);
    }

    public void showExplanation() {
        if (!isShown) {
            explanation.setText("This is a slot machine. In each round,\n" +
                    "3 numbers will be generated randomly; \n" +
                    "if all the numbers match, you can win \n" +
                    "some money! Start by selecting a risk level,\n" +
                    "the amount of money you can win \n" +
                    "increases as the risk level increases.\n" +
                    "(risk level 1 means that you have a chance \n " +
                    "to win 20% of your entered amount. Every \n" +
                    "increase in risk level by one will result in the \n" +
                    "winning percentage increasing by 5% as\n" +
                    " well.)");
            help.setText("Hide Instruction");
            isShown = true;
        } else {
            explanation.setText("");
            help.setText("Show Instruction");
            isShown = false;
        }
    }
}

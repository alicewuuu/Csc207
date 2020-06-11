package gui.guimain;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.Button;

import javafx.scene.control.TextField;

import main.managers.MainManager;

import java.io.IOException;

public class RegisterController extends Controller {

    @FXML
    private TextField userId;

    MainManager mainManager;

    public void initData(MainManager mainManager) {
        this.mainManager = mainManager;
    }

    public void cancelButton(javafx.event.ActionEvent actionEvent) throws IOException {
        try {
            ((LoginController) changeScene("/gui/guimain/Login.fxml")).initData(mainManager);
        } catch (IOException e) {
            e.printStackTrace();
        }
        displayScene(actionEvent);
    }

    public void continueButton(ActionEvent actionEvent) throws Exception {
        Alert.show("Register New User", this.mainManager.newUserRequest(userId.getText()));
        try {
            ((LoginController) changeScene("/gui/guimain/Login.fxml")).initData(mainManager);
        } catch (IOException e) {
            e.printStackTrace();
        }
        displayScene(actionEvent);
    }
}

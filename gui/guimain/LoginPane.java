package gui.guimain;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import main.managers.MainManager;

public class LoginPane {
    private static Stage stage = new Stage();

    public void start(MainManager mainManager) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
        Pane pane = loader.load();
        LoginController controller = loader.getController();
        controller.initData(mainManager);

        Scene scene = new Scene(pane, 600, 500);

        stage.setTitle("Bank Machine");
        stage.setScene(scene);
        stage.show();
    }

    public static void close() {
        stage.close();
    }
}


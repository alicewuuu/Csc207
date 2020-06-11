package gui.guimain;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javafx.event.ActionEvent;

import java.io.IOException;

abstract public class Controller {

    private Parent tableViewParent;

    public Object changeScene(String resource) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(resource));
        tableViewParent = loader.load();
        return loader.getController();
    }

    public void displayScene(ActionEvent actionEvent) {
        Scene tableViewScene = new Scene(getTableViewParent());
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
    }

    private Parent getTableViewParent() {
        return tableViewParent;
    }

}

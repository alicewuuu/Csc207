package gui.guimain;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Alert {

    /**
     * show a AlertBox, display information of the related events.
     *
     * @param title the title of the AlertBox stage
     * @param info  information we want to display to the user
     */
    public static void show(String title, String info) {


        Stage alertBox = new Stage();
        alertBox.setTitle(title);
        alertBox.setMinWidth(500);
        alertBox.setMinHeight(200);
        alertBox.initModality(Modality.APPLICATION_MODAL);

        // message to user
        Label message = new Label();
        message.setText(info);

        // Button for check noticed
        Button back = new Button();
        back.setText("OK");
        back.setOnAction(e -> alertBox.close());

        // initiate the layout with VBox
        VBox layout = new VBox(20);
        layout.setPadding(new Insets(20, 10, 20, 20));
        layout.getChildren().addAll(message, back);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);

        alertBox.setScene(scene);
        alertBox.showAndWait();
    }
}

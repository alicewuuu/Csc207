package gui.guimain;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class HBoxCell extends HBox {
    private Label label = new Label();
    private Button button;

    public HBoxCell(String labelText, Button button) {
        super();

        label.setText(labelText);
        label.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(label, Priority.ALWAYS);

        this.button = button;

        this.getChildren().addAll(label, button);
    }
}
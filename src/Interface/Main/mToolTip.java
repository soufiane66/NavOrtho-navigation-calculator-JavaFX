package Interface.Main;

import javafx.application.Application;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.PopupWindow;
import javafx.stage.Stage;

public class mToolTip extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    private static final String SQUARE_BUBBLE =
            "M24 1h-24v16.981h4v5.019l7-5.019h13z";

    @Override
    public void start(Stage stage) {
        Label label = new Label("hello,");
        label.setStyle("-fx-font-size: 16px;");

        label.setTooltip(makeBubble(new Tooltip(" world")));

        Circle circle = new Circle(20, Color.AQUA);
        Tooltip.install(circle, makeBubble(new Tooltip("circle")));

        VBox layout = new VBox(10, label, circle);
        layout.setPadding(new Insets(20));

        stage.setScene(new Scene(layout));
        stage.show();
    }

    private Tooltip makeBubble(Tooltip tooltip) {
        tooltip.setStyle("-fx-font-size: 16px; -fx-shape: \"" + SQUARE_BUBBLE + "\";");
        tooltip.setAnchorLocation(PopupWindow.AnchorLocation.WINDOW_BOTTOM_LEFT);

        return tooltip;
    }
}


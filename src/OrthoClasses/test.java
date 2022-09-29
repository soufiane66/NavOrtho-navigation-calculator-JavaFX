package OrthoClasses;

import Interface.Main.DraggableNode;
import Interface.Main.MainActivity;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Objects;

public class test extends Application {


    public static void main(String[] args) {

        launch(args);

    }

    @Override
    public void start(Stage stage) throws Exception {

        double w = 800,h = 600;

        Pane pane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("testView.fxml")));
        Circle center = new Circle(2.5);
        center.setTranslateX(400);
        center.setTranslateY(300);

        Circle circle = new Circle(20);
        circle.setFill(Color.GREEN);
        circle.setOpacity(0.5);
//        circle.setTranslateX(400);
//        circle.setTranslateY(300);
        new DraggableNode(circle);


        Button button = new Button("Click me");
        button.setTranslateX(200);
        button.setTranslateY(500);

        button.setOnAction(event -> {

            circle.relocate(400-20 , 300 - 20);
            System.out.println(circle.getLayoutX() + ":" + circle.getLayoutY());

        });

        Button button2 = new Button("Click me");
        button2.setTranslateX(400);
        button2.setTranslateY(500);

        button2.setOnAction(event -> {

            circle.relocate(600-20 , 300 - 20);
            System.out.println(circle.getLayoutX() + ":" + circle.getLayoutY());

        });


        circle.layoutXProperty().addListener( (e) -> {
            System.out.println(circle.getLayoutX() + ":" + circle.getLayoutY());
        });



        pane.getChildren().addAll(center,circle,button,button2);




        Scene scene = new Scene(pane,w,h);//1400,800
        stage.setScene(scene);
        stage.setResizable(true);
        stage.show();

    }
}

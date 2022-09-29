package Interface.Main;

import OrthoClasses.Orthodromie;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import pannableAndZoomable.SceneGestures;


public class MainActivity extends Application {

    static Scene scene;
    static Stage stage;



    @Override
    public void start(Stage primaryStage) throws Exception{

        stage = primaryStage;

        stage.setTitle("navortho");
        stage.getIcons().add(new Image("icons/anchor.png"));
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(MainActivity.class.getResource("orthodromie.fxml"));
        AnchorPane mainLayout = fxmlLoader.load();
        scene = new Scene(mainLayout,1650,800);//1400,800

        stage.setScene(scene);
        stage.setResizable(true);
        stage.show();
    }



    public static void main(String[] args) {
        launch(args);
    }


}

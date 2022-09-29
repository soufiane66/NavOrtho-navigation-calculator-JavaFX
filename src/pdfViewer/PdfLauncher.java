package pdfViewer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class PdfLauncher extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {

        PDFFXMLController.pdfPath = "src\\PdfViewer\\pdfviewer.pdf";

        BorderPane root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("PdfViewer.fxml")));
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

    }
}

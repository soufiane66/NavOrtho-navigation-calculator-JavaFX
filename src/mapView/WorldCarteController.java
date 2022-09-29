package mapView;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import pannableAndZoomable.NodeGestures;
import pannableAndZoomable.PannableCanvas;
import pannableAndZoomable.SceneGestures;

import java.net.URL;
import java.util.ResourceBundle;

public class WorldCarteController implements Initializable {

    @FXML
    Group  group;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        // create canvas
        PannableCanvas canvas = new PannableCanvas();



        // we don't want the canvas on the top/left in this example => just
        // translate it a bit
        canvas.setTranslateX(100);
        canvas.setTranslateY(100);

        // create sample nodes which can be dragged
        NodeGestures nodeGestures = new NodeGestures( canvas);

        Label label1 = new Label("Draggable node 1");
        label1.setTranslateX(10);
        label1.setTranslateY(10);
        label1.addEventFilter( MouseEvent.MOUSE_PRESSED, nodeGestures.getOnMousePressedEventHandler());
        label1.addEventFilter( MouseEvent.MOUSE_DRAGGED, nodeGestures.getOnMouseDraggedEventHandler());

        Label label2 = new Label("Draggable node 2");
        label2.setTranslateX(100);
        label2.setTranslateY(100);
        label2.addEventFilter( MouseEvent.MOUSE_PRESSED, nodeGestures.getOnMousePressedEventHandler());
        label2.addEventFilter( MouseEvent.MOUSE_DRAGGED, nodeGestures.getOnMouseDraggedEventHandler());

        Label label3 = new Label("Draggable node 3");
        label3.setTranslateX(200);
        label3.setTranslateY(200);
        label3.addEventFilter( MouseEvent.MOUSE_PRESSED, nodeGestures.getOnMousePressedEventHandler());
        label3.addEventFilter( MouseEvent.MOUSE_DRAGGED, nodeGestures.getOnMouseDraggedEventHandler());

        Circle circle1 = new Circle( 300, 300, 50);
        circle1.setStroke(Color.ORANGE);
        circle1.setFill(Color.ORANGE.deriveColor(1, 1, 1, 0.5));
        circle1.addEventFilter( MouseEvent.MOUSE_PRESSED, nodeGestures.getOnMousePressedEventHandler());
        circle1.addEventFilter( MouseEvent.MOUSE_DRAGGED, nodeGestures.getOnMouseDraggedEventHandler());

        Rectangle rect1 = new Rectangle(100,100);
        rect1.setTranslateX(450);
        rect1.setTranslateY(450);
        rect1.setStroke(Color.BLUE);
        rect1.setFill(Color.BLUE.deriveColor(1, 1, 1, 0.5));
        rect1.addEventFilter( MouseEvent.MOUSE_PRESSED, nodeGestures.getOnMousePressedEventHandler());
        rect1.addEventFilter( MouseEvent.MOUSE_DRAGGED, nodeGestures.getOnMouseDraggedEventHandler());

        canvas.getChildren().addAll(label1, label2, label3, circle1, rect1);

        group.getChildren().add(canvas);


        // create scene which can be dragged and zoomed




        canvas.addGrid();

    }



}

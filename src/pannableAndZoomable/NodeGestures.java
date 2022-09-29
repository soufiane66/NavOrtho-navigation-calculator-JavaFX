package pannableAndZoomable;


import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

/**
 * Listeners for making the nodes draggable via left mouse button. Considers if parent is zoomed.
 */
public class NodeGestures {

    private final DragContext nodeDragContext = new DragContext();

    PannableCanvas canvas;

    public NodeGestures( PannableCanvas canvas) {
        this.canvas = canvas;

    }

    public EventHandler<MouseEvent> getOnMousePressedEventHandler() {
        return onMousePressedEventHandler;
    }

    public EventHandler<MouseEvent> getOnMouseDraggedEventHandler() {
        return onMouseDraggedEventHandler;
    }

    private final EventHandler<MouseEvent> onMousePressedEventHandler = event -> {

        // left mouse button => dragging
        if( !event.isPrimaryButtonDown())
            return;

        /**
        get mouse coordinates relative to th scene by event when the mouse is pressed;
         */
        nodeDragContext.mouseAnchorX = event.getSceneX();// mouse X coordinate
        nodeDragContext.mouseAnchorY = event.getSceneY();// mouse Y coordinate

        Node node = (Node) event.getSource();// define which node is pressed

        //get current translation X and Y
        nodeDragContext.translateAnchorX = node.getTranslateX();
        nodeDragContext.translateAnchorY = node.getTranslateY();

    };

    private final EventHandler<MouseEvent> onMouseDraggedEventHandler = new EventHandler<MouseEvent>() {
        public void handle(MouseEvent event) {

            // left mouse button => dragging
            if( !event.isPrimaryButtonDown())
                return;

            System.out.println(canvas.getScale());
            double scale = canvas.getScale();

            Node node = (Node) event.getSource();

            node.setTranslateX(nodeDragContext.translateAnchorX + (( event.getSceneX() - nodeDragContext.mouseAnchorX) / scale));
            node.setTranslateY(nodeDragContext.translateAnchorY + (( event.getSceneY() - nodeDragContext.mouseAnchorY) / scale));

            event.consume();



        }
    };
}

package Interface.Main;

import OrthoClasses.LATITUDE;
import OrthoClasses.LONGITUDE;
import OrthoClasses.Position;
import javafx.geometry.Bounds;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

public class DraggableNode {




    public DraggableNode(Node node)
    {



        final Delta dragDelta = new Delta();

        node.setOnMouseEntered(me -> {
            if (!me.isPrimaryButtonDown()) {
                node.getScene().setCursor(Cursor.HAND);
                node.setOpacity(1);

            }
        });
        node.setOnMouseExited(me -> {
            if (!me.isPrimaryButtonDown()) {
                node.getScene().setCursor(Cursor.DEFAULT);
                node.setOpacity(0);

            }
        });
        node.setOnMousePressed(me -> {
            if (me.isPrimaryButtonDown()) {
                node.getScene().setCursor(Cursor.DEFAULT);
            }
            dragDelta.x = me.getX();
            dragDelta.y = me.getY();
            node.getScene().setCursor(Cursor.MOVE);




        });
        node.setOnMouseReleased(me -> {
            if (!me.isPrimaryButtonDown()) {
                node.getScene().setCursor(Cursor.DEFAULT);

            }
        });


        node.setOnMouseDragged(me -> {

            node.setLayoutX(node.getLayoutX() + me.getX() - dragDelta.x);
            node.setLayoutY(node.getLayoutY() + me.getY() - dragDelta.y);
            me.consume();


            //System.out.println(getNodePosition());




//            System.out.println("getLayoutX() = "+node.getLayoutX());
//            System.out.println("getTranslateX() = "+ node.getTranslateX());
//            System.out.println("getTranslateY() = "+node.getTranslateY());


            //System.out.println(getNodePosition());


        });




    }

    private static class Delta {
        public double x;
        public double y;
    }

    public LONGITUDE pixelToLongitude(double x)
    {
        double lon =(x - 503)/2.535625;
        return LONGITUDE.toLongitude(lon);
    }

    public LATITUDE pixelToLatitude(double y)
    {
        double lat = (251.2 - y)/2.5383;
        return LATITUDE.toLatitude(lat);
    }

    public Position getNodePosition(Node node)
    {

            Bounds boundsInScene = node.localToParent(node.getBoundsInLocal());
//            System.out.println(boundsInScene.getMinX());
//            System.out.println(boundsInScene.getMinY()+10);

            LATITUDE latitude = pixelToLatitude(boundsInScene.getMinY()+10);
            LONGITUDE longitude = pixelToLongitude(boundsInScene.getMinX());

        return new Position(latitude,longitude);


    }
}

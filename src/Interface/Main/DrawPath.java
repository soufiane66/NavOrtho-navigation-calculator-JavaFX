package Interface.Main;

import OrthoClasses.LATITUDE;
import OrthoClasses.LONGITUDE;
import OrthoClasses.Orthodromie;
import OrthoClasses.Position;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class DrawPath
{
    Canvas canvas;
    Position start;
    Position arrive;


    final double X_ref =1024/2.+ 1;
    final double Y_ref =478/2.+ 12.2;
    final double X_Unit = 2.535625; // 2.84 px/1degree
    final double Y_Unit = 2.5383; // 2.65 px/1degree

    public  double startX;
    public double startY;
    double arriveX;
    double arriveY;

    double vertexX;
    double vertexY;

    double controlX;
    double controlY;

    double deltaX;
    double deltaY;
    //the height of arc
    double h;
    double alpha;


    public double getStartX() {
        return startX;
    }

    public double getStartY() {
        return startY;
    }

    public double getArriveX() {
        return arriveX;
    }

    public double getArriveY() {
        return arriveY;
    }

    public DrawPath(Canvas canvas, Position start, Position arrive, Position vertex) {
        this.canvas = canvas;
        this.start = start;
        this.arrive = arrive;




        startX = longitudeToPixel(LONGITUDE.toDouble(start));
        startY = latitudeToPixel(LATITUDE.toDouble(start));
        arriveX = longitudeToPixel(LONGITUDE.toDouble(arrive));
        arriveY = latitudeToPixel(LATITUDE.toDouble(arrive));
        vertexX = longitudeToPixel(LONGITUDE.toDouble(vertex));
        vertexY = latitudeToPixel(LATITUDE.toDouble(vertex));


        deltaX = Math.abs(arriveX - startX);
        deltaY = Math.abs(arriveY - startY);
        h = deltaX * 0.2;

        alpha = Math.toDegrees(Math.atan(deltaY/deltaX));//.??

        controlX = getControlX();
        controlY = getControlY();









    }




    public void draw()
    {
        GraphicsContext g = this.canvas.getGraphicsContext2D();
        g.clearRect(0,0,canvas.getWidth(),canvas.getHeight());

        g.beginPath();
        g.moveTo(startX,startY);
        g.setStroke(Color.RED);
        g.setLineWidth(3);
        g.quadraticCurveTo(controlX,controlY,arriveX,arriveY);
        //g.closePath();
        g.stroke();

        g.beginPath();
        g.moveTo(startX,startY);
        g.setStroke(Color.GREENYELLOW);
        g.setLineWidth(3);
        g.lineTo(arriveX,arriveY);
        //g.closePath();
        g.stroke();

        g.setFill(Color.RED);
        g.fillOval(startX-5, startY-5,10, 10);

        //set A litter D for the start point
        g.setFill(Color.WHITE);
        g.setFont(Font.font("Tahoma", FontWeight.BOLD,18));
        g.fillText("A", startX, startY+30);

        g.setFill(Color.RED);
        g.fillOval(arriveX-5, arriveY-5,10, 10);

        //set A litter A for the arrive  point
        g.setFill(Color.WHITE);
        g.setFont(Font.font("Tahoma", FontWeight.BOLD,18));
        g.fillText("B", arriveX, arriveY+30);

        //draw vertex
        g.setFill(Color.YELLOW);
        g.fillOval(vertexX-5, vertexY-5,10, 10);
        g.setFill(Color.BLACK);
        g.setFont(Font.font("Tahoma",14));
        g.fillText("vertex", vertexX-15, vertexY+15);



        //create circle for curve Control point
//        g.setFill(Color.CYAN);
//        g.fillOval(controlX-5, controlY-5,10, 10);







    }

    public double getControlX()
    {
        double b = Math.tan(Math.toRadians(alpha)) * h;
        double a = Math.sqrt(Math.pow(deltaX,2)+Math.pow(deltaY,2))-((Math.sqrt(Math.pow(deltaX,2)+Math.pow(deltaY,2))*0.5)+b);
        double d = Math.cos(Math.toRadians(alpha))*a;
        double controlX = 100;

        //System.out.println("b="+b+" a="+a+" d="+d+" Vx="+(startX+d));

        if(startX <= arriveX && arriveY <= startY)
        {
            controlX = startX + d;
        }else
        if(startX <= arriveX && startY <= arriveY)  {
            controlX = arriveX - d;
        }else  if(startX >= arriveX && arriveY <= startY)
        {
            controlX = startX - d;
        }else
        if(startX >= arriveX && startY <= arriveY)  {
            controlX = arriveX + d;
        }

        return controlX;
    }

    public double getControlY()
    {

        double b = Math.tan(Math.toRadians(alpha)) * h;
        double a = deltaX - (b+deltaX/2);
        double f = Math.sqrt(Math.pow(h,2)+Math.pow(b,2));
        double e = Math.sin(Math.toRadians(alpha))*a;
        double Vy = f + e;
        double controlY;

        if(arrive.getLati().getSign()<0)
        {
            controlY = startY + Vy;
        }else {
            controlY = startY - Vy;
        }
        //System.out.println("f="+f+" e="+e+" Vy="+Vy);
        return controlY;
    }

    public double latitudeToPixel(double latitude)
    {
        return Y_ref - latitude*Y_Unit;

    }

    public double longitudeToPixel(double longitude)
    {
        return  X_ref + longitude*X_Unit;

    }

    @Override
    public String toString() {
        return "DrawPath{" +
                "startX=" + startX +
                ", startY=" + startY +
                ", arriveX=" + arriveX +
                ", arriveY=" + arriveY +
                "\ncontrolX=" + controlX +
                ", controlY=" + controlY +
                ", deltaX=" + deltaX +
                ", deltaY=" + deltaY +
                ", h=" + h +
                ", alpha=" + alpha +
                '}';
    }
}

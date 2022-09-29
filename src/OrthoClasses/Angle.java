package OrthoClasses;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class Angle {
    private int degree;
    private double minute;
    private static DecimalFormat df;


    public Angle(int degree, double minute) {
        this.degree = degree;
        this.minute = minute;

    }



    public int getDegree() {
        return degree;
    }

    public double getMinute() {
        return minute;
    }


    public Angle() {

    }

    @Override
    public String toString() {
        return degree+"°"+minute+"'";
    }

    public Angle(int degree) {
        this.degree = degree;
    }

    public Angle(int degree, int minute) {
        this.degree = degree;
        this.minute = minute;
    }

    public double toAngle( )
    {
        int degree = this.degree;
        double minute = this.minute;
        return degree+(minute/60.);

    }

    public static Angle toAngle(double angle )
    {
        df = new DecimalFormat("0.00");
        df.setRoundingMode(RoundingMode.HALF_UP);
        angle = Math.abs(angle);
        int degree =(int) angle;
        double minute =  ((angle-degree)*60);
        minute = Double.parseDouble(df.format(minute));

        return new Angle(degree,minute);

    }

    public double toRadian()
    {
        return Math.toRadians(toAngle());
    }

    public static double toMinute(Angle angle)
    {
        int degree = angle.degree;
        double minute = angle.minute;

        return (degree*60)+minute;

    }

    public static void main(String[] args) {

        Angle angle = new Angle();

        angle = Angle.toAngle(22.51);
        System.out.println(angle);

    }
}

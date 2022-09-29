package OrthoClasses;

public class Trigonometry {

    Trigonometry()
    {

    }

    public static double cos(double angle)
    {
        return Math.cos(Math.toRadians(angle));
    }

    public static double aCos(double cosVal)
    {
        return Math.toDegrees(Math.acos(cosVal));
    }

    public static double sin(double angle)
    {
        return Math.sin(Math.toRadians(angle));
    }

    public double aSin(double sinVal)
    {
        return Math.toDegrees(Math.asin(sinVal));
    }

    public double tan(double angle)
    {
        return Math.tan(Math.toRadians(angle));
    }

    public double aTan(double tanVal)
    {
        return Math.toDegrees(Math.atan(tanVal));
    }


    public double cot(double angle)
    {
        return 1/Math.tan(Math.toRadians(angle));
    }

    public double aCot(double cotVal)
    {
        return Math.toDegrees(Math.atan(1/cotVal));
    }



}

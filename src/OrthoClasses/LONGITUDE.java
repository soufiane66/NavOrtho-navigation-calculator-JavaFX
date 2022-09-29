package OrthoClasses;

import javafx.geometry.Pos;

public class LONGITUDE extends Angle {
    public static final int EST = 1;
    public static final int WEST = -1;
    private Angle angle;
    private int sign;
    private char lon_char;

    public LONGITUDE(Angle angle, int sign) {
        this.angle = angle;
        this.sign = sign;
        if (sign == EST) lon_char = 'E';
        if (sign == WEST) lon_char = 'W';
    }

    @Override
    public String toString() {
        return angle  +""+ lon_char;
    }

    public Angle getAngle() {
        return angle;
    }

    public int getSign() {
        return sign;
    }

    public char getLon_char() {
        return lon_char;
    }

    public static LONGITUDE toLongitude(double longitude)
    {
        LONGITUDE longi = null;
        if (longitude<=0){
            longi = new LONGITUDE(toAngle(longitude),LONGITUDE.WEST);
        }else if (longitude>0){
            longi = new LONGITUDE(toAngle(longitude),LONGITUDE.EST);
        }

        return longi;
    }

    public static double toDouble(Position position)
    {
        return  position.getLongi().getAngle().toAngle()*position.getLongi().getSign();
    }
}

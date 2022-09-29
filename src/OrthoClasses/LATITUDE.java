package OrthoClasses;

public class LATITUDE extends Angle {
    public static final int NORTH = 1;
    public static final int SOUTH = -1;
    private Angle angle;
    private int sign;
    private char lat_char;

    public LATITUDE(Angle angle, int sign) {

        this.angle = angle;
        this.sign = sign;
        if (sign == NORTH) lat_char = 'N';
        if (sign == SOUTH) lat_char = 'S';
    }

    @Override
    public String toString() {
        return angle +"" + lat_char ;
    }

    public Angle getAngle() {
        return angle;
    }

    public int getSign() {
        return sign;
    }

    public char getLat_char() {
        return lat_char;
    }

    public static LATITUDE toLatitude(double latitude)
    {
        LATITUDE lati = null;
        if (latitude<0){
            lati = new LATITUDE(Angle.toAngle(latitude),LATITUDE.SOUTH);
        }else
        if (latitude>=0){
            lati = new LATITUDE(Angle.toAngle(latitude),LATITUDE.NORTH);
        }

        return lati;
    }

    public static double toDouble(Position position)
    {
        return position.getLati().getAngle().toAngle()*position.getLati().getSign();
    }
}

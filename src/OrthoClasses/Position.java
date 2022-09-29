package OrthoClasses;

public class Position  {

    private LATITUDE Lati;
    private LONGITUDE Longi;


    public Position(LATITUDE lati, LONGITUDE longi) {
        Lati = lati;
        Longi = longi;
    }




    public LATITUDE getLati() {
        return Lati;
    }

    @Override
    public String toString() {
        return  ""+Lati +"   "+ Longi ;
    }


    public String latitudeToString() {
        return this.Lati.toString();
    }

    public String longitudeToString() {
        return this.Longi.toString();
    }

    public LONGITUDE getLongi() {
        return this.Longi;
    }


}


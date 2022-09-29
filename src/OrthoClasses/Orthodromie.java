package OrthoClasses;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Calendar;

public class Orthodromie extends Trigonometry {
    public double fiD, fiA;
    public double e, g, l;
    public double M, m, gain;
    public Position vertex;
    DecimalFormat df;
    double DM1 = 0;
    double angle_loxo = 0;
    String equation = "";
    double Duree = 0;
    private Angle angle;
    private boolean NE, NW, SE, SW;
    private char latSign, lonSign;
    private Position D;
    private Position A;
    private Time depart_date = null;
    private double Vs;
    private double V_loxo;
    private double GD, GA;
    private double lat_moyen;
    private double CotVi, CosVf;
    private double Vi, V_trig, Vf;
    private double fiV, GV, gv;
    private double Rloxo;
    private int Ecart_loxo;
    private int Ecart_Ortho;
    private double Ri, Rf;
    private String Vi_direction;
    private  String Vf_direction;
    private char GV_sign = 0;
    Position M1;

    public Orthodromie() {

    }


//    public Orthodromie(Position D, Position A) {
//        this.D = D;
//        this.A = A;
//        angle = new Angle();
//
//        initialValues();
//        initialSigns();
//        System.out.println(this);
//        angleInitial();
//        angleFinale();
//        coordonnesDeVertex();
//    }


    public Orthodromie(Time depart_date, double Vs, Position D, Position A) {
        this.depart_date = depart_date;
        this.Vs = Vs;
        this.D = D;
        this.A = A;
        angle = new Angle();
        initialValues();
        initialSigns();
//        angleInitial();
//        angleFinale();
//        coordonnesDeVertex();
        System.out.println(this);
    }



    public String finalResult() {
        return this +
                "\nRoute initiale Vi : " +
                "\nCalcul de la Route initiale : \n" +
                "Vi Cotg V = (tan fiA . cos fiD - sin fiD . cos g)/ sin g\n" +
                "Cotg V = " + CotVi + "\n" +
                "Vi = " + Math.abs(Vi) + "°" + Vi_direction + " = " + Ri + "°" +
                "\n============================================================================================" +
                "\nRoute finale Vf :" +
                "\nOn a Sin Vf / Cos ϕD = Sin V / Cos ϕA\n" +
                "SinVf = cos(ϕD)*sin(V)/cos(ϕA)\n" +
                "Vf = " + Math.abs(Vf) + "° vers " + Vf_direction + "\n" +
                "Route finale du navire est " + Rf + "°\n" +
                "============================================================================================\n" +
                "Coordonnées des vertex :\n" +
                "\uD835\uDF11v = ?\n" +
                "Cos \uD835\uDF11v = sin V. cos \uD835\uDF11D \uD835\uDF11v = " + Angle.toAngle(fiV) + "\n" +
                "Gv = ?\n" +
                "tg gv = cotgV / sin \uD835\uDF11D\n" +
                "gV = " + gv + " et GD = " + D.getLongi().getAngle().toString() + D.getLongi().getLon_char() + "\n" +
                "donc Gv = GD + gv = " + D.getLongi().getAngle().toString() + " + (" + gv + ") = " + Angle.toAngle(GV) + GV_sign +
                "\n============================================================================================" +
                "\nCalcul de la Rloxo: \n" +
                "Cotg V = [tg (ϕA).cos(ϕD) – sin(ϕD).cos g ]/ Sin g\n" +
                "V = " + Ri + " " + Vi_direction + " Donc R loxo = V – ϕ\n" +
                "ϕ = m/120 x tg ϕD x sinV" +
                "\nOn a m= V. Δt = " + V_loxo + " x " + Duree + "h = " + DM1 + "'\n" +
                "On a donc ϕ = " + angle_loxo + "° Donc " + equation +
                "\nDM1 = " + DM1 + "' angle_loxo = " + angle_loxo + "° Rloxo = " + Double.parseDouble(df.format(Rloxo)) + "°"+
                "\n============================================================================================"+
                "\nCoordonnées de point de jalonnement j1 : "+
                "\nl = m cos Rv = " + l +
                "\ng = m sin Rv / cos ϕm = " + g +
                "\n==> M1 { ϕM1 = " + M1.getLati() + ", GM1 = " + M1.getLongi() + " }";
    }

    public void initialValues() {
        df = new DecimalFormat("0.00");
        df.setRoundingMode(RoundingMode.HALF_EVEN);
        fiD = Double.parseDouble(df.format(LATITUDE.toDouble(D)));
        fiA = Double.parseDouble(df.format(LATITUDE.toDouble(A)));
        l = Double.parseDouble(df.format(fiA - fiD));
        GD = Double.parseDouble(df.format(LONGITUDE.toDouble(D)));
        GA = Double.parseDouble(df.format(LONGITUDE.toDouble(A)));
        g = Double.parseDouble(df.format(GA - GD));
        if (Math.abs(g) > 180) {
            g = 360 - Math.abs(g);
        }
        lat_moyen = (fiD + fiA) / 2;
        e = g * Math.cos(Math.toRadians(lat_moyen));
        e = Double.parseDouble(df.format(e));
        m = Double.parseDouble(df.format(distanceLoxo()));
        M = Double.parseDouble(df.format(distanceOrtho()));
        gain = m - M;
        gain = Double.parseDouble(df.format(gain));

        Ecart_loxo = (int) ((m / Vs) * 60);
        Ecart_Ortho = (int) ((M / Vs) * 60);


    }

    public double distanceLoxo() {
        return Double.parseDouble(df.format(Math.sqrt(Math.pow(e * 60, 2) + Math.pow(l * 60, 2))));
    }

    public double distanceOrtho() {

        double cosM = sin(fiD) * sin(fiA) + cos(fiD) * cos(fiA) * cos(g);
        return Double.parseDouble(df.format(Math.toDegrees(Math.acos(cosM)) * 60));

    }

    public double getGain() {
        return gain;
    }

    public String angleInitial() {
        if(g == 0 && l > 0){
            System.out.println(" Rout initiale est N ");
            Vi_direction = "";
            Ri = 0;
            Vi = 0;
            V_trig = 0;
        }else if(g == 0 && l < 0){
            System.out.println(" Rout initiale est S ");
            Vi_direction = "";
            Ri = 180;
            Vi = 180;
            V_trig = 180;
        }


        if (g != 0) {

            CotVi = (tan(fiA) * cos(fiD) - sin(fiD) * cos(Math.abs(g))) / sin(Math.abs(g));
            Vi = aCot(CotVi);
            Vi = Double.parseDouble(df.format(Vi));
            System.out.println(CotVi);

            if (CotVi == 0 && g < 0) {
                System.out.println(" Rout initiale est NW ");
                Vi_direction = "NW";
                NW = true;
                Ri = 360 - Math.abs(Vi);
                V_trig = Vi;


            } else if (CotVi == 0 && g > 0) {
                System.out.println(" Rout initiale est NE ");
                Vi_direction = "NE";
                NE = true;
                Ri = Math.abs(Vi);
                V_trig = Ri;


            } else if (CotVi > 0 && g >= 0) {
                System.out.println(" Rout initiale est NE ");
                Vi_direction = "NE";
                NE = true;
                Ri = Math.abs(Vi);
                V_trig = Ri;


            } else if (CotVi > 0 && g < 0) {
                System.out.println(" Rout initiale est NW ");
                Vi_direction = "NW";
                NW = true;
                Ri = 360 - Math.abs(Vi);
                V_trig = Vi;


            } else if (CotVi <= 0 && g > 0) {
                System.out.println(" Rout initiale est SE ");
                Vi_direction = "SE";
                SE = true;
                Ri = 180 - Math.abs(Vi);
                V_trig = Ri;


            } else if (CotVi <= 0 && g < 0) {
                System.out.println(" Rout initiale est SW ");
                Vi_direction = "SW";
                SW = true;
                Ri = 180 + Math.abs(Vi);
                V_trig = 360 - Ri;

            }
        }
//        if (fiD < 0){
//            if (CotVi > 0 && g > 0) {
//                SW = true;
//                Ri = 180 + Math.abs(Vi);
//
//
//            } else if (CotVi > 0 && g < 0) {
//                SE = true;
//                Ri = 180 - Math.abs(Vi);
//
//
//            } else if (CotVi < 0 && g > 0) {
//                NW = true;
//                Ri = 360 - Math.abs(Vi);
//
//
//            } else if (CotVi < 0 && g < 0) {
//                NE = true;
//                Ri = Math.abs(Vi);
//
//            }
//        }

        Ri = Double.parseDouble(df.format(Ri));
        System.out.println("Calcule de la Route initiale : \n" +
                " Vi Cotg V = (tan fiA . cos fiD - sin fiD . cos g)/ sin g\n" +
                "Cotg V = " + CotVi + "\n" +
                "Vi = " + Math.abs(Vi) + "°" + Vi_direction + " = " + Ri + "°");

        System.out.println("============================================================================================");

        return  Ri + "°";

    }

    public String angleFinale() {
        if (fiD >= 0) {
            CosVf = (tan(fiD) * cos(fiA) - sin(fiA) * cos(Math.abs(g))) / sin(Math.abs(g));
            Vf = aCot(CosVf);
            Vf = Double.parseDouble(df.format(Vf));
            if(g == 0 && l > 0){
                Vf_direction = "";
                Rf = 0;
            }else if( g == 0 && l < 0){
                Rf = 180;
                Vf_direction = "";
            }else if (g > 0) {
                Rf = 180 - Vf;
                Vf_direction = "NE";

            }else if (g < 0) {
                Rf = Vf + 180;
                Vf_direction = "NW";

            }

            System.out.println("Calcul de l’angle de route finale Vf :\n" +
                    "cos(90°-ϕD) = Cos M.cos(90°-ϕA) + sin(90°-ϕA). sinM. cosVf\n" +
                    "\uF0F0 cosVf = [cos(90°-ϕD) - Cos M.cos(90°-ϕA)] / sin(90°-ϕA). sinM\n" +
                    "\uF0F0 Vf = " + Math.abs(Vf) + "° vers " + Vf_direction + "\n" +
                    "Route finale du navire est " + Double.parseDouble(df.format(Rf)) + "°");

        } else if (fiD < 0) {
            double SinVf = cos(fiD) * sin(V_trig) / cos(fiA);
            Vf = aSin(SinVf);
            Vf = Double.parseDouble(df.format(Vf));
            if (g > 0) {
                Rf = Vf;
            }
            if (g < 0) {
                Rf = 360 - Vf;

            }

            System.out.println("Calcul de l’angle de route finale Vf :\n" +
                    "On a Sin Vf / Cos ϕD = Sin V / Cos ϕA\n" +
                    "\uF0F0  SinVf = cos(ϕD)*sin(V)/cos(ϕA)\n" +
                    "\uF0F0 Vf = " + Math.abs(Vf) + "° vers " + latSign + "\n" +
                    "Route finale du navire est " + Rf + "°");
        }

        System.out.println("============================================================================================");

        return Double.parseDouble(df.format(Rf)) + "°";


    }

    public String coordonnesDeVertex() {

        if(l == 0) {
            fiV = 0;
             GV = 0;
            vertex = new Position(new LATITUDE(Angle.toAngle(fiV), D.getLati().getSign()), new LONGITUDE(Angle.toAngle(GV),1));
            return "rien..!";

        }

        double cosfiV = cos(fiD) * sin(V_trig);
        fiV = aCos(cosfiV);
        fiV = Double.parseDouble(df.format(fiV));
        gv = Math.toDegrees(Math.atan(CotVi / Math.sin(Math.toRadians(fiD))));
        if (Double.isNaN(gv)) {
            gv = 0;
        }
        gv = Double.parseDouble(df.format(gv));


        if (g >= 0 && GD >= 0) {
            GV = Math.abs(GD) + gv;
            System.out.println("g> 0 && GD > 0");
        }
        if (g >= 0 && GD <= 0) {
            GV = Math.abs(GD) - gv;
            System.out.println("(g> 0 && GD < 0");
        }
        if (g <= 0 && GD <= 0) {
            GV = Math.abs(GD) + gv;
            System.out.println("g < 0 && GD < 0");
        }
        if (g <= 0 && GD >= 0) {
            GV = Math.abs(GD) - gv;
            System.out.println("g < 0 && GD > 0");
        }

        GV = Double.parseDouble(df.format(GV));

        if (GV >= 0 && GV <= 180) {
            GV_sign = D.getLongi().getLon_char();
        } else if (GV <= 0 && GV <= 180) {
            if (D.getLongi().getLon_char() == 'E') {
                GV_sign = 'W';
            } else if (D.getLongi().getLon_char() == 'W') {
                GV_sign = 'E';
            }
        } else if (GV > 180) {
            GV = 360 - GV;
            if (D.getLongi().getLon_char() == 'E') {
                GV_sign = 'W';
            } else if (D.getLongi().getLon_char() == 'W') {
                GV_sign = 'E';
            }
        }

        System.out.println("Coordonnées des vertex :\n" +
                "\uD835\uDF11v = ?\n" +
                "Cos \uD835\uDF11v = sin V. cos \uD835\uDF11D \uD835\uDF11v = " + Angle.toAngle(fiV) + "\n" +
                "Gv = ?\n" +
                "tg gv = cotgV / sin \uD835\uDF11D\n" +
                "gV = " + gv + " et GD = " + D.getLongi().getAngle().toString() + D.getLongi().getLon_char() + "\n" +
                "donc Gv = GD + gv = " + D.getLongi().getAngle().toString() + " + (" + gv + ") = " + Angle.toAngle(GV) + GV_sign);
        System.out.println(
                "Conclusion : le vertex a pour coordonnées : \n" +
                        "ϕv = " + Angle.toAngle(fiV) + D.getLati().getLat_char() + "\n" +
                        "Gv = " + Angle.toAngle(GV) + GV_sign);

        System.out.println("============================================================================================");

        int g_sing = 0;
        if (GV_sign == 'W') {
            g_sing = -1;
        } else if (GV_sign == 'E') {
            g_sing = 1;
        }

        vertex = new Position(new LATITUDE(Angle.toAngle(fiV), D.getLati().getSign()), new LONGITUDE(Angle.toAngle(GV), g_sing));

        return "ϕv = " + Angle.toAngle(fiV) + D.getLati().getLat_char() + "\nGv = " + Angle.toAngle(GV) + GV_sign;


    }

    public String CalculeRloxo(double duree, double V_loxo) {
        this.V_loxo = V_loxo;
        Duree = duree;
        DM1 = duree * V_loxo;
        angle_loxo = (DM1 / 120) * Math.tan(Math.toRadians(fiD)) * Math.sin(Math.toRadians(V_trig));

        angle_loxo = Math.abs(angle_loxo);
        angle_loxo = Double.parseDouble(df.format(angle_loxo));

        if (fiD > 0) {
            if (g > 0) {
                Rloxo = Ri + angle_loxo;
                equation = "R loxo= V + ϕ";
            }
            if (g < 0) {
                Rloxo = Ri - angle_loxo;
                equation = "R loxo= V - ϕ";

            }
        }

        if (fiD < 0) {
            if (g > 0) {
                Rloxo = Ri - angle_loxo;
                equation = "R loxo= V - ϕ";

            }
            if (g < 0) {
                Rloxo = Ri + angle_loxo;
                equation = "R loxo= V + ϕ";

            }
        }

        System.out.println("Calcule de la Rloxo: ");
        System.out.println("Cotg V = [tg (ϕA).cos(ϕD) – sin(ϕD).cos g ]/ Sin g\n" +
                "V = " + Ri + " " + Vi_direction + " Donc R loxo = V – ϕ\n" +
                "ϕ = m/120 x tg ϕD x sinV On a m= V. Δt = " + V_loxo + " x " + duree + "h = " + DM1 + "'\n" +
                "On a donc ϕ = " + angle_loxo + "° Donc " + equation + "");
        System.out.println("DM1 = " + DM1 + "' angle_loxo = " + angle_loxo + "° Rloxo = " + Double.parseDouble(df.format(Rloxo)) + "°");
        System.out.println("============================================================================================");

        return Double.parseDouble(df.format(Rloxo)) + "°";


    }

    public void initialSigns() {

        if (l > 0 && g == 0) {
            latSign = 'N';

        } else if (l < 0 && g == 0) {

            latSign = 'S';

        } else if (l > 0 && g > 0) {


            latSign = 'N';
            lonSign = 'E';


        } else if (l > 0 && g < 0) {

            latSign = 'N';
            lonSign = 'W';

        } else if (l < 0 && g > 0) {
            latSign = 'S';
            lonSign = 'E';

        } else if (l < 0 && g < 0) {
            latSign = 'S';
            lonSign = 'W';

        }
    }

    public String pointDeJalonnement(double duree, double V) {
        if(l == 0)
        {
            return "rien..!";
        }
        double m = duree * V;
        double l = (m * cos(Rloxo)) / 60;
        double g = (m * sin(Rloxo) / cos(lat_moyen)) / 60;
        double fiM1 = l + fiD;
        double GM1 = g + GD;
         M1 = new Position(LATITUDE.toLatitude(fiM1), LONGITUDE.toLongitude(GM1));
        System.out.println("Coordonnées de point de jalonnement j1 : ");
        System.out.println( "l = m cos Rv = " + l + "\n" +
                            "g = m sin Rv / cos ϕm = " + g + "\n" +
                            "==> M1 { ϕM1 = " + M1.getLati() + ", GM1 = " + M1.getLongi() + " }");


        return "ϕM1 = " + M1.getLati() + "\nGM1 = " + M1.getLongi();

    }

    public void CoordonnesDeM1(double distance) {
        //Coordonnées du points de jalonnement j2 :
        double CotgM1 = (cot(distance / 60) * cos(fiD) - sin(fiD) * cos(V_trig)) / sin(V_trig);
        double gM1 = aCot(CotgM1);
        double GM1 = Math.abs(GD) + Math.abs(gM1);
        char GM1_sign = 0;
        if (GM1 <= 180) {
            GM1_sign = D.getLongi().getLon_char();
        } else {
            GM1 = 360 - GM1;
            if (D.getLongi().getLon_char() == 'E') {
                GM1_sign = 'W';
            } else if (D.getLongi().getLon_char() == 'W') {
                GM1_sign = 'E';
            }
        }
        double SinFiM1 = cos(distance / 60) * sin(fiD) + sin(distance / 60) * cos(fiD) * cos(V_trig);
        double fiM1 = aSin(SinFiM1);
        System.out.println("Les coordonnées du point M1 situé à une distance de " + distance + " miles du point du départ: \n" +
                //Calcule de GM1
                "GM1 = ?\n" +
                "On a DM1 = " + distance + "’ = " + distance / 60 + "°\n" +
                "Cotg gM1.SinV = [cotg(" + distance / 60 + "°).sin(90- ϕD) ] - [Cos (90- ϕD) . cos V]\n" +
                "Ce qui implique :\n" +
                "Cotg gM1 = [ [cotg(" + distance / 60 + "°).sin(90- ϕD) ] - [Cos (90- ϕD) . cos V] ] / SinV\n" +
                "gM1 = " + Angle.toAngle(gM1) + lonSign + "\n" +
                "\uF0F0 GM1 = GD + gM1\n" +
                "\uF0F0 = " + Angle.toAngle(GD) + " + " + Angle.toAngle(gM1) + " \uF0F0 GM1 = " + Angle.toAngle(GM1) + GM1_sign);
        //Calcule de ϕM1
        System.out.println("ϕM1= ?\n" +
                "Cos (90- ϕM1) = Cos" + distance / 60 + "° . Sin ϕD + sin " + distance / 60 + "°.cos ϕD. cos V\n" +
                "\uF0F0 Sin ϕM1= cos" + distance / 60 + "°. Sin ϕD + sin" + distance / 60 + "°.cos ϕD. cos V \uF0F0 fiM1 = " + Angle.toAngle(fiM1) + latSign);
        System.out.println("M1{fiM1 = " + Angle.toAngle(fiM1) + latSign + ",GM1 = " + Angle.toAngle(GM1) + lonSign + "}");
        System.out.println("============================================================================================");

        //Coordonnées du points de jalonnement j2 :

//        double CotgM2 = (cot(distance/60)*cos(fiM1)-sin(fiM1)*cos(V_trig))/sin(V_trig);
//        double gM2 = aCot(CotgM2);
//        double GM2 = Math.abs(GM1) + Math.abs(gM2);
//        char GM2_sign = 0;
//        if (GM2 <= 180)
//        {
//            GM2_sign = D.getLongi().getLon_char();
//        }else {
//            GM2 = 360 - GM2;
//            if(D.getLongi().getLon_char()=='E'){
//                GM2_sign = 'W';
//            }else if(D.getLongi().getLon_char()=='W'){
//                GM2_sign = 'E';
//            }
//        }
//        double SinFiM2 = cos(distance/60)*sin(fiM1)+sin(distance/60)*cos(fiM1)*cos(V_trig);
//        double fiM2 = aSin(SinFiM2);
//        System.out.println("Les coordonnées du point M2 situé à une distance de "+distance+" miles du point du départ: \n"+
//                //Calcule de GM2
//                "GM1 = ?\n"+
//                "On a M1M2 = "+distance+"’ = "+distance/60+"°\n" +
//                "Cotg gM2.SinV = [cotg("+distance/60+"°).sin(90- ϕM1) ] - [Cos (90- ϕM1) . cos V]\n" +
//                "Ce qui implique :\n" +
//                "Cotg gM2 = [ [cotg("+distance/60+"°).sin(90- ϕM1) ] - [Cos (90- ϕM1) . cos V] ] / SinV\n"+
//                "gM2 = "+angle.toAngle(gM2)+lonSign+"\n" +
//                "\uF0F0 GM2 = GM1 + gM2\n" +
//                "\uF0F0 = "+angle.toAngle(GM1)+" + "+angle.toAngle(gM2) +" \uF0F0 GM1 = "+angle.toAngle(GM2)+GM1_sign);
//        //Calcule de ϕM1
//        System.out.println("ϕM2= ?\n" +
//                "Cos (90- ϕM2) = Cos"+distance/60+"° . Sin ϕM1 + sin "+distance/60+"°.cos ϕM1. cos V\n" +
//                "\uF0F0 Sin ϕM2= cos"+distance/60+"°. Sin ϕM1 + sin"+distance/60+"°.cos ϕM1. cos V \uF0F0 ϕM2 = "+angle.toAngle(fiM2)+latSign);
//        System.out.println("M1{ϕM2 = "+angle.toAngle(fiM2)+latSign+",GM2 = "+angle.toAngle(GM2)+lonSign+"}");


    }

    @Override
    public String toString() {
        return
                "\nDate de départ = " + depart_date.getTime() +
                        "\nPoint de départ = " + D +
                        "\nPoint d’arrivé = " + A +
                        "\nfiD = " + fiD +
                        ", GD = " + GD +
                        "\nfiA = " + fiA +
                        ", GA = " + GA +
                        "\ne=" + e +
                        ", g=" + g +
                        ", l=" + l +
                        ", latiM=" + lat_moyen +
                        "\nM=" + M +
                        ", m=" + m +
                        ", gain=" + gain +
                        "\nEcart Ortho =" + df.format(Ecart_Ortho / 1440.) + " Jours" +
                        ", Ecart Loxo=" + df.format(Ecart_loxo / 1440.) + " Jours" +
                        "\nDate et l'heure d’arrivée à la destination en suivant l’ortho : " + getArrivalTimeOrtho() +
                        "\nDate et l'heure d’arrivée à la destination en suivant la loxo : " + getArrivalTimeLoxo() +
                        "\n============================================================================================";
    }

    public String getArrivalTimeOrtho() {
        return "" + depart_date.addTime(Ecart_Ortho);
    }

    public String getArrivalTimeLoxo() {
        return "" + depart_date.addTime(Ecart_loxo);
    }

//    public static void main(String[] args) {
//        Time date = new Time(2021, Calendar.APRIL, 30, 10, 0);
//
////Td 2 Premiere partie :
////
////        LATITUDE fiD = new LATITUDE(new Angle(20,30),LATITUDE.NORTH);
////        LONGITUDE GD = new LONGITUDE(new Angle(105,30),LONGITUDE.EST);
////        LATITUDE fiA = new LATITUDE(new Angle(40,0),LATITUDE.NORTH);
////        LONGITUDE GA = new LONGITUDE(new Angle(10,30),LONGITUDE.EST);
//
////Td 3 2 partie :
//        LATITUDE fiD = new LATITUDE(new Angle(22, 30), LATITUDE.NORTH);
//        LONGITUDE GD = new LONGITUDE(new Angle(16, 30), LONGITUDE.WEST);
//        LATITUDE fiA = new LATITUDE(new Angle(0), LATITUDE.NORTH);
//        LONGITUDE GA = new LONGITUDE(new Angle(96), LONGITUDE.WEST);
//
////        //Td 4  :
////        LATITUDE fiD = new LATITUDE(new Angle(40,20),LATITUDE.NORTH);
////        LONGITUDE GD = new LONGITUDE(new Angle(150,48),LONGITUDE.EST);
////        LATITUDE fiA = new LATITUDE(new Angle(37,52),LATITUDE.NORTH);
////        LONGITUDE GA = new LONGITUDE(new Angle(122,8),LONGITUDE.WEST);
//
//        //Td 5 Exercise 1  :
////        LATITUDE fiD = new LATITUDE(new Angle(56,12),LATITUDE.SOUTH);
////        LONGITUDE GD = new LONGITUDE(new Angle(66,54),LONGITUDE.WEST);
////        LATITUDE fiA = new LATITUDE(new Angle(34,22),LATITUDE.SOUTH);
////        LONGITUDE GA = new LONGITUDE(new Angle(18,23),LONGITUDE.EST);
//
//
//        //Td 5 Exercise 2  :
////        LATITUDE fiD = new LATITUDE(new Angle(33,59.9),LATITUDE.NORTH);
////        LONGITUDE GD = new LONGITUDE(new Angle(137),LONGITUDE.EST);
////        LATITUDE fiA = new LATITUDE(new Angle(48),LATITUDE.NORTH);
////        LONGITUDE GA = new LONGITUDE(new Angle(125),LONGITUDE.WEST);
//
//
//        Position D = new Position(fiD, GD);
//        Position A = new Position(fiA, GA);
//
//        Orthodromie orthodromie = new Orthodromie(date, 15, D, A);
//        orthodromie.angleInitial();
//        orthodromie.angleFinale();
//        orthodromie.coordonnesDeVertex();
//        orthodromie.CalculeRloxo(24, 15);
//        System.out.println("=========================================================================================");
//        orthodromie.CoordonnesDeM1(360);
//        orthodromie.pointDeJalonnement(24, 15);
//
//
//        System.out.println("=========================================================================================");
//
//
////Calcule le point de jalonnement M2
////        LATITUDE fiM1 = new LATITUDE(new Angle(39,29.5),LATITUDE.NORTH);
////        LONGITUDE GM1 = new LONGITUDE(new Angle(144,15.7),LONGITUDE.EST);
////        Position M1 = new Position(fiM1,GM1);
////        Orthodromie orthodromie2 = new Orthodromie(date, 15, M1,A);
////        orthodromie2.angleInitiale();
////        orthodromie2.angleFinale();
////        orthodromie2.CalculeRloxo(24,13);
////        System.out.println("=========================================================================================");
//////        orthodromie2.CoordonnesDeM1(24*20);
////        orthodromie2.pointDeJalonnement(24,20);
////
////
////       System.out.println("=========================================================================================");
//
//
//    }
}

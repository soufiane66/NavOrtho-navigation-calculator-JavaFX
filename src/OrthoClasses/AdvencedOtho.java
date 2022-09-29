package OrthoClasses;

public class AdvencedOtho extends Orthodromie {

    private double angle_Ort_Equator;
    private LONGITUDE GV;


    /**
     * Ce constructor est fait pour Une orthodromie fait un angle de x avec l équateur,
     * son vertex Nord/Sud a pour longitude x.
     */
    public AdvencedOtho(double angle_Ort_Equator,LONGITUDE GV)
    {

        this.angle_Ort_Equator = angle_Ort_Equator;
        this.GV = GV;

    }

    public void VertexCoordonnes ()
    {

    }

    public void NoeudsCoordonnes ()
    {

    }

    public void CoordonnesDeM1(double temps,double vitesse) {
        double distance = temps*vitesse;
        CoordonnesDeM1(distance);
    }

    public static void main(String[] args) {

    }
}

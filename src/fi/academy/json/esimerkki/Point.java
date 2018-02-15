package fi.academy.json.esimerkki;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Point {

    //Tämän luokan avulla saadaan purettua GeoJson-dataa joka on tyyppiä Point
    //Luokka on luotu postgis Point luokkaa pohjana käyttäen, mutta luotu omana luokkana
    //jotta vältytään ylimääräiseltä kirjastojen käyttöönotolta
    //-Paula-

    private String type;
    private double x;
    private double y;

    @Override
    public String toString() {
        return "Koordinaatit: " +
                "pituuspiiri = " + x +
                ", leveyspiiri = " + y ;
    }

    public double getX() {
        return x;
    }
    public void setX(double x) {
        this.x = x;
    }
    public double getY() {
        return y;
    }
    public void setY(double y) {
        this.y = y;
    }

    public double[] getCoordinates(){
        return new double[] {this.getX(), this.getY()};
    }

    public void setCoordinates(double[] x){
        if (x.length == 2){
            this.setX(x[0]);
            this.setY(x[1]);
        }
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}

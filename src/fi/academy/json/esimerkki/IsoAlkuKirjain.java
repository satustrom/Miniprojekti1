package fi.academy.json.esimerkki;

public class IsoAlkuKirjain {

    public static String alkukirjainMuotoilu(String sana){
        String uusiSana = sana.toLowerCase();

        String alkuKirjain = uusiSana.substring(0, 1);

        int pituus = uusiSana.length();

        String valmis = alkuKirjain.toUpperCase()+uusiSana.substring(1,pituus);

        return valmis;

}
}
//metodi, joka muuttaa sanan alkamaan isolla kirjaimella ja muuten jatkumaan pienellä -Olli
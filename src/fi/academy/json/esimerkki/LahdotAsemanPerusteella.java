package fi.academy.json.esimerkki;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class LahdotAsemanPerusteella {

    private static List<Asematieto> kaikkiAsemat;

    public static void LahtevatJunatAsemalta(String lahtoAsema, int minuutit) {
        //eli laitetaan lähtöasema ja miltä ajalta haluaa tietää lähtevät junat, esim 15 min.

        String baseurl = "https://rata.digitraffic.fi/api/v1";
        String hakuehdot = "include_nonstopping=false";
        String hakuAsema = lahtoAsema;
        int minuutitLahtoon = minuutit;

        try {
            URL url = new URL(baseurl + "/live-trains/station/" + hakuAsema + "?minutes_before_departure="+minuutitLahtoon+"?"+hakuehdot);
            ObjectMapper mapperi = new ObjectMapper();
            CollectionType TarkempiListanTyyppi = mapperi.getTypeFactory().constructCollectionType(ArrayList.class, Juna.class);
            List<TimeTableRow> junaTiedot = mapperi.readValue(url, TarkempiListanTyyppi);

            System.out.println("\n\n");

            System.out.println("Lähtevät junat asemalta: " +hakuAsema + ", ja seuraavan " + minuutitLahtoon + " minuutin aikana:");

        } catch (Exception ex) {
            System.out.println(ex);
        }

    }

    public static void main(String[] args) {

    }
}
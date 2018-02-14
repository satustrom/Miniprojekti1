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

            int i = 0;
            System.out.printf("Juna %s - %s \n\t Lähtee: %s\n\t Liikkeellä: %s\n\t Junan tyyppi: %s\n %s\n"
                    ,junaTiedot.get(i).getTrainType()
                    ,junaTiedot.get(i).getTrainNumber()
                    ,junat.get(i).getDepartureDate()
                    ,junat.get(i).isRunningCurrently()
                    ,junat.get(i).getTrainCategory()
                    ,junat.get(i).getTimeTableRows());
            System.out.println("----------------------------------------");

        } catch (Exception ex) {
            System.out.println(ex);
        }

    }
}
package fi.academy.json.esimerkki;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SeuraavaJuna {


    public static void kahdenKaupunginVali(String lahtoasema, String maaraasema) {

//Muutetaan käyttäjältä saatu kaupunki sitä vastaavaan lyhytkoodiin ja tallennetaan se muuttujaan.
        String lAsema = Asemat.palautaLyhytkoodi(lahtoasema);
        String kAsema = Asemat.palautaLyhytkoodi(maaraasema);

        String baseurl = "https://rata.digitraffic.fi/api/v1";
        String hakuehdot = "include_nonstopping=false";

        try {
//Syötetään hakuehdot URLiin
            URL url = new URL(baseurl + "/live-trains/station/" + lAsema + "/" + kAsema + "?" + hakuehdot);
            ObjectMapper mapper = new ObjectMapper();
            CollectionType tarkempiListanTyyppi = mapper.getTypeFactory().constructCollectionType(ArrayList.class, Juna.class);
            List<Juna> junat = mapper.readValue(url, tarkempiListanTyyppi);  // pelkkä List.class ei riitä tyypiksi

            System.out.println("Seuraava juna välillä: " + lAsema + " - " + kAsema);

            int i = 0;
            System.out.printf("Juna %s - %s \n\t Lähtee: %s\n\t Liikkeellä: %s\n\t Junan tyyppi: %s\n %s\n"
                    , junat.get(i).getTrainType()
                    , junat.get(i).getTrainNumber()
                    , junat.get(i).getDepartureDate()
                    , junat.get(i).isRunningCurrently()
                    , junat.get(i).getTrainCategory()
                    , junat.get(i).getTimeTableRows());
            System.out.println("----------------------------------------");

        } catch (Exception ex) {
            System.out.println(ex);
        }
    }


    public static void tietyltaAsemalta(String lahtoasema) {

//Muutetaan käyttäjältä saatu kaupunki sitä vastaavaan lyhytkoodiin ja tallennetaan se muuttujaan.
        String lAsema = Asemat.palautaLyhytkoodi(lahtoasema);


        String baseurl = "https://rata.digitraffic.fi/api/v1";
        String hakuehdot = "include_nonstopping=false";

        try {
//Syötetään hakuehdot URLiin
            URL url = new URL(baseurl + "/live-trains/station/" + lAsema + "?" + hakuehdot);
            ObjectMapper mapper = new ObjectMapper();
            CollectionType tarkempiListanTyyppi = mapper.getTypeFactory().constructCollectionType(ArrayList.class, Juna.class);
            List<Juna> junat = mapper.readValue(url, tarkempiListanTyyppi);  // pelkkä List.class ei riitä tyypiksi

            System.out.println("Seuraavat 5 junaa asemalta: " + lAsema);

            for (int i = 0; i < 5; i++) {
                List<TimeTableRow> lista = junat.get(i).timeTableRows;

                System.out.printf("Juna %s - %s \n\t Lähtee: %s\n\t Junan tyyppi: %s\n"
                        , junat.get(i).getCommuterLineID()
                        , junat.get(i).getTrainNumber()
                        , lista.get(i).haeAikaStringina()
                        , junat.get(i).getTrainCategory());
                System.out.println("----------------------------------------");
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
    
}

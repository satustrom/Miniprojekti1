package fi.academy.json.esimerkki;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import java.net.URI;
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

            System.out.println("Seuraava juna välillä: " + Asemat.palautaKaupunki(lAsema) + " - " + Asemat.palautaKaupunki(kAsema));

            int i = 0;
// Luodaan timetablerow lista
            List<TimeTableRow> lista = junat.get(i).timeTableRows;
// Luodaan apumuuttujat joilla saadaan aloitettua määrättyä minkä aseman aika tulostetaan ja miltä asemalta matka aloitetaan välipysäkkien tulostus
            int alkuID = 0;
            int loppuID = 0;
            for (int j = 0; j < lista.size(); j++) {
                if (lista.get(j).getStationShortCode().equals(lAsema))
                    alkuID = j;
                if (lista.get(j).getStationShortCode().equals(kAsema)) {
                    loppuID = j;
                    continue;
                }
            }
            System.out.println("----------------------------------------");
            System.out.printf("Juna %s - %s \n\t Lähtee: %s\n"
                    , junat.get(i).getCommuterLineID()
                    , junat.get(i).getTrainNumber()
                    , lista.get(alkuID).haeAikaStringina());


            for (int j = alkuID; j <= loppuID; j++) {
               if (j%2==1) {
                   System.out.println("\t\t\t\t\t - " + lista.get(j).haeKellonAikaStringina() + " " + Asemat.palautaKaupunki(lista.get(j).getStationShortCode()));
               }
            }

            System.out.println("----------------------------------------");


        } catch (Exception ex) {
            System.out.println(ex);
        }
    }


    public static void tietyltaAsemalta(String lahtoasema) {

//Muutetaan käyttäjältä saatu kaupunki sitä vastaavaan lyhytkoodiin ja tallennetaan se muuttujaan.
        String lAsema = Asemat.palautaLyhytkoodi(lahtoasema);


        String baseurl = "https://rata.digitraffic.fi/api/v1";
        String hakuehdot = "?arrived_trains=0&arriving_trains=0&departed_trains=0&departing_trains=5&include_nonstopping=false";

        try {
//Syötetään hakuehdot URLiin
            URL url = new URL(URI.create(baseurl + "/live-trains/station/" + lAsema + hakuehdot).toASCIIString());
            ObjectMapper mapper = new ObjectMapper();
            CollectionType tarkempiListanTyyppi = mapper.getTypeFactory().constructCollectionType(ArrayList.class, Juna.class);
            List<Juna> junat = mapper.readValue(url, tarkempiListanTyyppi);  // pelkkä List.class ei riitä tyypiksi
            System.out.println("Haetaan 5 lähtevää junaa asemalta: " + Asemat.palautaKaupunki(lAsema) + ".");

            for (int i = 0; i < junat.size(); i++) {
                List<TimeTableRow> lista = junat.get(i).timeTableRows;

                System.out.printf("Juna %s - %s \n\t Lähtee: %s\n\t Määränpää: %s\n"
                        , junat.get(i).getCommuterLineID()
                        , junat.get(i).getTrainNumber()
                        , lista.get(i).haeAikaStringina()
                        , Asemat.palautaKaupunki(lista.get(lista.size()-1).getStationShortCode()));
                System.out.println("----------------------------------------");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println(ex);
        }
    }

}

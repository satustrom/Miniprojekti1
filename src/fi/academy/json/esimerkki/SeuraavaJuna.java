package fi.academy.json.esimerkki;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.sql.Time;
import java.util.*;

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
// Tulostetaan Junan tiedot
            System.out.println("----------------------------------------");
            System.out.printf("Juna %s - %s \n\t Lähtee: %s\n"
                    , junat.get(i).getCommuterLineID()
                    , junat.get(i).getTrainNumber()
                    , lista.get(alkuID).haeAikaStringina());

// Tulostetaan välipysäkit
            for (int j = alkuID; j <= loppuID; j++) {
                if (j % 2 == 1) {
                    System.out.println("\t\t\t\t\t - " + lista.get(j).haeKellonAikaStringina() + " " + Asemat.palautaKaupunki(lista.get(j).getStationShortCode()));
                }
            }

            System.out.println("----------------------------------------");


        } catch (NullPointerException ex) {
            System.out.println("Ei löytynyt");
        } catch (JsonParseException e) {
            System.out.println("Ei löytynyt");
        } catch (JsonMappingException e) {
            System.out.println("Ei löytynyt");
        } catch (MalformedURLException e) {
            System.out.println("Ei löytynyt");
        } catch (IOException e) {
            System.out.println("Ei löytynyt");
        }
    }


    public static void tietyltaAsemalta(String lahtoasema) throws IOException {

//Muutetaan käyttäjältä saatu kaupunki sitä vastaavaan lyhytkoodiin ja tallennetaan se muuttujaan.
        String lAsema = Asemat.palautaLyhytkoodi(lahtoasema);
        List<TimeTableRow> lista;
        List<Juna> junat;

        String baseurl = "https://rata.digitraffic.fi/api/v1";
        String hakuehdot = "?arrived_trains=0&arriving_trains=0&departed_trains=0&departing_trains=5&include_nonstopping=false";


//Syötetään hakuehdot URLiin
        URL url = null;

            url = new URL(URI.create(baseurl + "/live-trains/station/" + lAsema + hakuehdot).toASCIIString());

            ObjectMapper mapper = new ObjectMapper();
            CollectionType tarkempiListanTyyppi = mapper.getTypeFactory().constructCollectionType(ArrayList.class, Juna.class);
            junat = mapper.readValue(url, tarkempiListanTyyppi);  // pelkkä List.class ei riitä tyypiksi
            // System.out.println("Haetaan 5 seuraavaksi lähtevää junaa asemalta: " + Asemat.palautaKaupunki(lAsema) + ".");

            Collections.sort(junat, (eka, toka) -> {
                Date ekaaika = null, tokaaika = null;
                for (TimeTableRow rivi : eka.getTimeTableRows()) {
                    if (rivi.getStationShortCode().equals(lAsema)) {
                        ekaaika = rivi.getScheduledTime();
                        break;
                    }
                }
                for (TimeTableRow rivi : toka.getTimeTableRows()) {
                    if (rivi.getStationShortCode().equals(lAsema)) {
                        tokaaika = rivi.getScheduledTime();
                        break;
                    }
                }
                if (ekaaika.equals(tokaaika)) return 0;
                return ekaaika.after(tokaaika) ? 1 : -1;
            });
// Tulostetaan junat
            System.out.println("-------------------------------------------");
            for (int i = 0; i < junat.size(); i++) {
                lista = junat.get(i).timeTableRows;
                TimeTableRow lAsemanRivi = null;
                for (TimeTableRow rivi : lista) {
                    if (rivi.getStationShortCode().equals(lAsema) && rivi.getType().equals("DEPARTURE")) {
                        lAsemanRivi = rivi;
                        break;
                    }
                }
                System.out.println((i + 1) + ".");
                //Collections.sort(lista, new Junavertailija());
                System.out.printf("Juna %s - %s \n\t Lähtee: %s\n\t Määränpää: %s\n"
                        , junat.get(i).getCommuterLineID()
                        , junat.get(i).getTrainNumber()
                        , lAsemanRivi.haeAikaStringina()
                        , Asemat.palautaKaupunki(lista.get(lista.size() - 1).getStationShortCode()));
                System.out.println("-------------------------------------------");

            }
        if (junat.size() < 5) {
            System.out.println("Valitettavasti tänään ei lähde enempää junia.");
            System.out.println("-------------------------------------------");
        }


    }

}

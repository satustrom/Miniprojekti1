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
//Syötetään hakuehdot URLiin/Sami
            URL url = new URL(URI.create(baseurl + "/live-trains/station/" + lAsema + "/" + kAsema + "?" + hakuehdot).toASCIIString());
            ObjectMapper mapper = new ObjectMapper();
            CollectionType tarkempiListanTyyppi = mapper.getTypeFactory().constructCollectionType(ArrayList.class, Juna.class);
            List<Juna> junat = mapper.readValue(url, tarkempiListanTyyppi);  // pelkkä List.class ei riitä tyypiksi

            System.out.println("Seuraava juna välillä: " + Asemat.palautaKaupunki(lAsema) + " - " + Asemat.palautaKaupunki(kAsema));

            int i = 0;
// Luodaan timetablerow lista/Sami
            List<TimeTableRow> lista = junat.get(i).timeTableRows;
// Luodaan apumuuttujat joilla saadaan aloitettua määrättyä minkä aseman aika tulostetaan ja miltä asemalta matka aloitetaan välipysäkkien tulostus
//Sami
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
// Tulostetaan Junan tiedot/Sami
            System.out.println("----------------------------------------");
            System.out.printf("Juna %s - %s \n\t Lähtee: %s\n"
                    , junat.get(i).getCommuterLineID()
                    , junat.get(i).getTrainNumber()
                    , lista.get(alkuID).haeAikaStringina());

// Tulostetaan välipysäkit/Sami
            for (int j = alkuID; j <= loppuID; j++) {
                if (j % 2 == 1) {
                    System.out.println("\t\t\t\t\t - " + lista.get(j).haeKellonAikaStringina() + " " + Asemat.palautaKaupunki(lista.get(j).getStationShortCode()));
                }
            }

            System.out.println("----------------------------------------");

// Poikkeusten määrittely/Sami
        } catch (NullPointerException ex) {
            System.out.println("Valitettavasti junaa ei löytynyt.");
        } catch (JsonParseException e) {
            System.out.println("Valitettavasti junaa ei löytynyt.");
        } catch (JsonMappingException e) {
            System.out.println("Valitettavasti junaa ei löytynyt.");
        } catch (MalformedURLException e) {
            System.out.println("Valitettavasti junaa ei löytynyt.");
        } catch (IOException e) {
            System.out.println("Valitettavasti junaa ei löytynyt.");
        }
    }


    public static void tietyltaAsemalta(String lahtoasema) throws IOException {

//Muutetaan käyttäjältä saatu kaupunki sitä vastaavaan lyhytkoodiin ja tallennetaan se muuttujaan.
//Tässä käytetään Asemat-luokassa luotua metodia
//Sami
        String lAsema = Asemat.palautaLyhytkoodi(lahtoasema);
        List<TimeTableRow> lista;
        List<Juna> junat;

        String baseurl = "https://rata.digitraffic.fi/api/v1";
        String hakuehdot = "?arrived_trains=0&arriving_trains=0&departed_trains=0&departing_trains=5&include_nonstopping=false";


//Syötetään hakuehdot URLiin
//laitettu perään "toASCIIString", jotta saadaan URLiin mukaan ääkköset
//Sami ja Satu
        URL url = null;

            url = new URL(URI.create(baseurl + "/live-trains/station/" + lAsema + hakuehdot).toASCIIString());

            ObjectMapper mapper = new ObjectMapper();
            CollectionType tarkempiListanTyyppi = mapper.getTypeFactory().constructCollectionType(ArrayList.class, Juna.class);
            junat = mapper.readValue(url, tarkempiListanTyyppi);  // pelkkä List.class ei riitä tyypiksi
            // System.out.println("Haetaan 5 seuraavaksi lähtevää junaa asemalta: " + Asemat.palautaKaupunki(lAsema) + ".");


// Vertaillaan junia ja sortataaan ne lähtöajan mukaan ja haetaan oikea lähtöaseman aika
//Satu
            Collections.sort(junat, (eka, toka) -> {
                Date ekaaika = null, tokaaika = null;
                for (TimeTableRow rivi : eka.getTimeTableRows()) {
                    //Eli tässä katsotaan, että aikataulusta lähtöasema ja sen lyhytkoodi vastaavat,
                    //jotta saadaan oikea lähtöaika, eikä esim koko junavuoron lähtöasemaa/Satu
                    if (rivi.getStationShortCode().equals(lAsema)) {
                        ekaaika = rivi.getScheduledTime();
                        break;
                    }
                }
                //Tässä tehdään sama juttu kuin edellä toiselle junalle, että niitä voi vertailla/Satu
                for (TimeTableRow rivi : toka.getTimeTableRows()) {
                    if (rivi.getStationShortCode().equals(lAsema)) {
                        tokaaika = rivi.getScheduledTime();
                        break;
                    }
                }
                //Täällä vertaillaan kahta juna-asemaa ja järjestetään ne ajan mukaan/Satu
                if (ekaaika.equals(tokaaika)) return 0;
                return ekaaika.after(tokaaika) ? 1 : -1;
            });

// Tulostetaan junat/Sami
            System.out.println("-------------------------------------------");
            for (int i = 0; i < junat.size(); i++) {
                lista = junat.get(i).timeTableRows;
                //Tässä vielä katsotaan, että aikataulut tulee vain lähtöaikana, eli kun tyyppi on "DEPARTURE"
                //Satu
                TimeTableRow lAsemanRivi = null;
                for (TimeTableRow rivi : lista) {
                    if (rivi.getStationShortCode().equals(lAsema) && rivi.getType().equals("DEPARTURE")) {
                        lAsemanRivi = rivi;
                        break;
                    }
                }
                //Kun junat on sortattu, tulostetaan täällä niille vielä tarvittavat tiedot/Sami
                System.out.println((i + 1) + ".");
                System.out.printf("Juna %s - %s \n\t Lähtee: %s\n\t Määränpää: %s\n"
                        , junat.get(i).getCommuterLineID()
                        , junat.get(i).getTrainNumber()
                        , lAsemanRivi.haeAikaStringina()
                        , Asemat.palautaKaupunki(lista.get(lista.size() - 1).getStationShortCode()));
                System.out.println("-------------------------------------------");

            }
// Jos lähteviä junia on alle 5 niin tulostetaan viesti perään/Satu
        if (junat.size() < 5) {
            System.out.println("Valitettavasti tänään ei lähde enempää junia.");
            System.out.println("-------------------------------------------");
        }


    }

}

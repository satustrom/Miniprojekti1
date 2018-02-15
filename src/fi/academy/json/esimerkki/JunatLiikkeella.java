// Sami Nykänen

package fi.academy.json.esimerkki;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JunatLiikkeella {
    public static void liikkeella(String lahtoasema, String maaraasema) {


//Muutetaan käyttäjältä saatu kaupunki sitä vastaavaan lyhytkoodiin ja tallennetaan se muuttujaan.
        String lAsema = Asemat.palautaLyhytkoodi(lahtoasema);
        String kAsema = Asemat.palautaLyhytkoodi(maaraasema);

        String baseurl = "https://rata.digitraffic.fi/api/v1";
        String hakuehdot = "";

        try {
//Syötetään hakuehdot URLiin
            URL url = new URL(baseurl + "/live-trains/station/" + lAsema + "/" + kAsema + "?" + hakuehdot);
            ObjectMapper mapper = new ObjectMapper();
            CollectionType tarkempiListanTyyppi = mapper.getTypeFactory().constructCollectionType(ArrayList.class, Juna.class);
            List<Juna> junat = mapper.readValue(url, tarkempiListanTyyppi);  // pelkkä List.class ei riitä tyypiksi

            System.out.println("Tulostetaan junat välillä: " + Asemat.palautaKaupunki(lAsema) + " - " + Asemat.palautaKaupunki(kAsema));

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
            for (i = alkuID; i < loppuID; i++) {
            //    List<TimeTableRow> lista2 = junat.get(l).timeTableRows;
             //  if (junat.get(i).isRunningCurrently()) {
                    System.out.println("----------------------------------------");
                    System.out.printf("Juna %s - %s \n\t Lähtenyt:\t %s\n\t Asemalta:\t %s\n\t Määränpää:\t %s\n\t Juuri nyt:\t %s\n"
                            , junat.get(i).getCommuterLineID()
                            , junat.get(i).getTrainNumber()
                            , lista.get(0).haeAikaStringina()
                            , Asemat.palautaKaupunki(lista.get(0).getStationShortCode())
                            , Asemat.palautaKaupunki(lista.get(lista.size()-1).getStationShortCode())
                            , Asemat.palautaKaupunki(lista.get(i).getStationShortCode()));
                    System.out.println("----------------------------------------");
          //  }
            }

        } catch (Exception ex) {
            System.out.println(ex);
        }
    }


    public static void main(String[] args) {
        liikkeella("Helsinki", "Pasila");
    }

}

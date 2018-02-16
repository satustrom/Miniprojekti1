// Sami Nykänen

package fi.academy.json.esimerkki;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import java.io.IOException;
import java.net.MalformedURLException;
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


//Syötetään hakuehdot URLiin
        URL url = null;
        try {
            url = new URL(baseurl + "/live-trains/station/" + lAsema + "/" + kAsema + "" + hakuehdot);
        } catch (MalformedURLException e) {
            System.out.println("Virheellinen URL-haku");
        }
        ObjectMapper mapper = new ObjectMapper();
        CollectionType tarkempiListanTyyppi = mapper.getTypeFactory().constructCollectionType(ArrayList.class, Juna.class);
        List<Juna> junat = null;
        try {
            junat = mapper.readValue(url, tarkempiListanTyyppi);
        } catch (NullPointerException ex) {
            System.out.println("Haulla ei löytynyt yhtään junaa");
        } catch (JsonParseException e) {
            System.out.println("Haulla ei löytynyt yhtään junaa");
        } catch (JsonMappingException e) {
            System.out.println("Haulla ei löytynyt yhtään junaa");
        } catch (IOException e) {
            System.out.println("Haulla ei löytynyt yhtään junaa");
        }

        System.out.println("Tulostetaan junat välillä: " + Asemat.palautaKaupunki(lAsema) + " - " + Asemat.palautaKaupunki(kAsema));
// Tulostetaan junat jotka ovat APIn mukaan tällä hetkellä liikkeellä
        int printatut = 0;
        for (int i = 0; i < junat.size(); i++) {
           if (junat.get(i).isRunningCurrently()) {
                List<TimeTableRow> lista = junat.get(i).timeTableRows;
                System.out.printf("Juna %s - %s \n\t Lähtee: %s\n\t Asemalta: %s\n\t Juuri nyt: %s\n\t Määränpää: %s\n"
                        , junat.get(i).getCommuterLineID()
                        , junat.get(i).getTrainNumber()
                        , lista.get(i).haeAikaStringina()
                        , Asemat.palautaKaupunki(lista.get(0).getStationShortCode())
                        , Asemat.palautaKaupunki(lista.get(i).getStationShortCode())
                        , Asemat.palautaKaupunki(lista.get(lista.size() - 1).getStationShortCode()));
                System.out.println("-------------------------------------");
                printatut += 1;

//Jos yhdelläkään junalla ei ole isRunningCurrently=True niin tulostetaan ilmoitus siitä.
            } else if (printatut == 0) {
                System.out.println("-------------------------------------");
                System.out.println("Valitettavasti VR lakkoilee joten yhtään junaa ei ole ajossa.");
                break;
            }

        }


   }


}


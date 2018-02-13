// Sami Nykänen

package fi.academy.json.esimerkki;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JunatLiikkeella {
    public static void liikkeella() {
        String baseurl = "https://rata.digitraffic.fi/api/v1";
        String hakuehdot = "";
        String hakuKaupunki = "";

        try {
            URL url = new URL(baseurl+"/live-trains/"+hakuKaupunki+""+hakuehdot);
            ObjectMapper mapper = new ObjectMapper();
            CollectionType tarkempiListanTyyppi = mapper.getTypeFactory().constructCollectionType(ArrayList.class, Juna.class);
            List<Juna> junat = mapper.readValue(url, tarkempiListanTyyppi);  // pelkkä List.class ei riitä tyypiksi
            //(System.out.println(junat.get(0).getTrainNumber());
            // Seuraavaa varten on toteutettava TimeTableRow luokka:
            //System.out.println(junat.get(0).getTimeTableRows().get(0).getScheduledTime());
            System.out.println("\n\n");

          //  int tulostustenMaara = lkm;
            for (int i = 0; i < junat.size() ; i++) {
                String tulostus = "";
                if (junat.get(i).isRunningCurrently()) {
                    System.out.printf("Juna %s - %s \n\t Lähtenyt: %s\n\t Liikkeellä: %s\n\t Junan tyyppi: %s\n"
                            ,junat.get(i).getTrainType()
                            ,junat.get(i).getTrainNumber()
                            ,junat.get(i).getDepartureDate()
                            ,junat.get(i).isRunningCurrently()
                            ,junat.get(i).getTrainCategory());
                    System.out.println("----------------------------------------");
                }

            }

        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
}

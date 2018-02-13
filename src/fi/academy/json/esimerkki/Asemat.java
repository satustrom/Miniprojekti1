package fi.academy.json.esimerkki;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Asemat {

    Asematieto asematieto = new Asematieto();

    public static void main(String[] args) { lueDataa(); }

    private static void lueDataa() {
        String baseurl = "https://rata.digitraffic.fi/api/v1";
        try {
            URL url = new URL(baseurl+"/metadata/stations");
            ObjectMapper mapper = new ObjectMapper();
            CollectionType tarkempiListanTyyppi = mapper.getTypeFactory().constructCollectionType(ArrayList.class, Asematieto.class);
            List<Asematieto> asemat = mapper.readValue(url, tarkempiListanTyyppi);  // pelkkä List.class ei riitä tyypiksi
            //System.out.println(junat.get(0).perustietoja());
            // Seuraavaa varten on toteutettava TimeTableRow luokka:
            //System.out.println(junat.get(0).getTimeTableRows().get(0).getScheduledTime());
            System.out.println("\n\n");

            //System.out.println(asemat.get(0));



            for (int i = 0; i <asemat.size() ; i++) {
                System.out.println(asemat.get(i).perustietoja());
            }



        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

}

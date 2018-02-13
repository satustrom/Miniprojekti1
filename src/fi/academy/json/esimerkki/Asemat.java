package fi.academy.json.esimerkki;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Asemat {

    Asematieto asematieto = new Asematieto();

    //täällä tulostetaan asemakaupunkeja
    //voit kutsua tätä siältöä mainissa: Asemat.asemaData();
    public static void asemaData() {
        String baseurl = "https://rata.digitraffic.fi/api/v1";
        try {
            URL url = new URL(baseurl + "/metadata/stations");
            ObjectMapper mapper = new ObjectMapper();
            CollectionType tarkempiListanTyyppi = mapper.getTypeFactory().constructCollectionType(ArrayList.class, Asematieto.class);
            //Tässä listassa on kaikki asemat
            List<Asematieto> kaikkiAsemat = mapper.readValue(url, tarkempiListanTyyppi);  // pelkkä List.class ei riitä tyypiksi

            //sortattuna kaikista asemista hlöasemat ja tulostetaan ne:
            List<Asematieto> henkiloAsemat = kaikkiAsemat.stream().filter(a->a.isPassengerTraffic()).collect(Collectors.toList());
            for(Asematieto asematieto : henkiloAsemat)
                System.out.println(asematieto.perustietoja());

            /*Täältä löytyy vielä tulostus, jos haluaa tulostaa kaikki asemat
            for (int i = 0; i < kaikkiAsemat.size(); i++) {
                System.out.println(kaikkiAsemat.get(i).perustietoja());
            }*/


        } catch (Exception ex) {
            System.out.println(ex);
        }


    }

}

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

    private static List<Asematieto> kaikkiAsemat;
    //täällä tulostetaan asemakaupunkeja
    //voit kutsua tätä siältöä mainissa: Asemat.asemaData();
    public static void asemaData() {

        String baseurl = "https://rata.digitraffic.fi/api/v1";

        try {
            URL url = new URL(baseurl + "/metadata/stations");
            ObjectMapper mapper = new ObjectMapper();
            CollectionType tarkempiListanTyyppi = mapper.getTypeFactory().constructCollectionType(ArrayList.class, Asematieto.class);
            //Tässä listassa on kaikki asemat
            kaikkiAsemat = mapper.readValue(url, tarkempiListanTyyppi);  // pelkkä List.class ei riitä tyypiksi

            //sortattuna kaikista asemista hlöasemat ja tulostetaan ne:
            List<Asematieto> henkiloAsemat = kaikkiAsemat.stream().filter(a->a.isPassengerTraffic()).collect(Collectors.toList());
            for(Asematieto asematieto : henkiloAsemat)
                System.out.println(asematieto.perustietoja());

           /*Kaikki asemat
            List<Asematieto> muutAsemat = kaikkiAsemat.stream().filter(a->!a.isPassengerTraffic()).collect(Collectors.toList());
            for(Asematieto asematieto : muutAsemat)
                System.out.println(asematieto.perustietoja());*/

//            for (int i = 0; i < asemat.size(); i++) {
//                System.out.println(asemat.get(i).perustietoja());
//            }

        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public static String palautaLyhytkoodi(String asemaKaupunki) {
        //Asematieto asematieto = new Asematieto();
        //String haeKaupunki = lAsema;
        if (kaikkiAsemat == null)
            asemaData();
        for (int i = 0; i < kaikkiAsemat.size(); i++) {
            if (asemaKaupunki.equalsIgnoreCase(kaikkiAsemat.get(i).getStationName())) {
                return kaikkiAsemat.get(i).getStationShortCode();
            }
        }

        return null;
    }
}


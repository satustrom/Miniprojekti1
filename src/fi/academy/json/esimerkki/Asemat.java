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
    private static List<Asematieto> henkiloAsemat;
    private static List<Asematieto> kaupungit;
    //täällä tulostetaan asemakaupunkeja
    //voit kutsua tätä siältöä mainissa: Asemat.asemaData();
    public static void asemaData() {

        String baseurl = "https://rata.digitraffic.fi/api/v1";

        try {
            URL url = new URL(baseurl + "/metadata/stations");
            ObjectMapper mapper = new ObjectMapper();
            CollectionType tarkempiListanTyyppi = mapper.getTypeFactory().constructCollectionType(ArrayList.class, Asematieto.class);
            //Tässä listassa on kaikki asemat
            kaikkiAsemat = mapper.readValue(url, tarkempiListanTyyppi);

            //sortattuna kaikista asemista hlöasemat ja tulostetaan ne:
            henkiloAsemat = kaikkiAsemat.stream().filter(a->a.isPassengerTraffic()).collect(Collectors.toList());

            kaupungit = new ArrayList<>(kaikkiAsemat);
            kaupungit
                    .stream()
                    .forEach(s->{
                        if (s.getStationName().endsWith(" asema"))
                                    s.setStationName(s.getStationName().substring(0,s.getStationName().lastIndexOf(" asema")));
                        });
                    //.collect(Collectors.toList());

           // for(Asematieto asematieto : henkiloAsemat)
             // System.out.println(asematieto.getStationName());


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

        for(Asematieto tieto : kaikkiAsemat) {
            if (tieto.getStationName().startsWith(asemaKaupunki)) {
                return tieto.getStationShortCode();
            }
        }
        return null;
    }

    public static String palautaKaupunki (String asemaKoodi) {
        if (kaupungit == null)
            asemaData();

        for (int i = 0; i < kaupungit.size(); i++) {
            if (kaupungit.get(i).isPassengerTraffic() && asemaKoodi.equalsIgnoreCase(kaupungit.get(i).getStationShortCode()))
                return kaupungit.get(i).getStationName();
            else if (asemaKoodi.equalsIgnoreCase(kaupungit.get(i).getStationShortCode()))
                return kaupungit.get(i).getStationName() + " (ei matkustusliikenteen käytössä)";
        }

/*
        if (kaikkiAsemat == null)
            asemaData();
        for (int i = 0; i < kaikkiAsemat.size() ; i++) {
            if(asemaKoodi.equalsIgnoreCase(kaikkiAsemat.get(i).getStationShortCode())) {
                return kaikkiAsemat.get(i).getStationName() + " (ei matkustusliikenteen käytössä)";
            }
        }
*/
        return null;
    }

    public static double palautaKoordinaatit (String asemaKaupunki) {
        if (kaikkiAsemat == null)
            asemaData();
        for (Asematieto tiedot : kaikkiAsemat) {
            if(tiedot.getStationName().startsWith(asemaKaupunki)) {
                return tiedot.getLatitude() + tiedot.getLongitude();
            }
        }
        return 0;
    }
}


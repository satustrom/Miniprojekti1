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
    private static List<Asematieto> kaupungit;

    //Asemat-luokka kerää metadatasta kaikki Suomen asemat.
    //Tätä luokkaa käytetään, kun mainissa tai muissa luokissa halutaan vaihtaa lyhennetty koodi pidennetyksi koodiksi
    //-Satu

    public static void asemaData() {

        String baseurl = "https://rata.digitraffic.fi/api/v1";

        try {
            URL url = new URL(baseurl + "/metadata/stations");
            ObjectMapper mapper = new ObjectMapper();
            CollectionType tarkempiListanTyyppi = mapper.getTypeFactory().constructCollectionType(ArrayList.class, Asematieto.class);
            //Tässä listassa on kaikki asemat
            kaikkiAsemat = mapper.readValue(url, tarkempiListanTyyppi);

            kaupungit = new ArrayList<>(kaikkiAsemat);
            kaupungit
                    .stream()
                    .forEach(s->{
                        if (s.getStationName().endsWith(" asema"))
                                    s.setStationName(s.getStationName().substring(0,s.getStationName().lastIndexOf(" asema")));
                        });

        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
    //täällä palautetaan lyhyt koodi, kun syötetään kaupunki.
    //Esimerkiksi käyttäjän syöttäessa kaupungin nimen, hakuun palautuu sen koodi, jolla tietoa haetaan.
    //-Satu
    public static String palautaLyhytkoodi(String asemaKaupunki) {
        if (kaikkiAsemat == null)
            asemaData();

        for(Asematieto tieto : kaikkiAsemat) {
            if (tieto.getStationName().startsWith(asemaKaupunki)) {
                return tieto.getStationShortCode();
            }
        }
        return null;
    }

    //täällä palautetaan kaupungin nimi, kun hakuun on kirjoitettu asemakoodi.
    //jos asema ei ole matkustusliikenteen käytössä, palautetaan kaupungin lisäksi muistutus siitä
    //Joissakin junissa on asema- sana aseman nimen perässä. Alla oleva koodi poistaa myös ne.
    //-Satu

    public static String palautaKaupunki (String asemaKoodi) {
        if (kaupungit == null)
            asemaData();

        for (int i = 0; i < kaupungit.size(); i++) {
            if (kaupungit.get(i).isPassengerTraffic() && asemaKoodi.equalsIgnoreCase(kaupungit.get(i).getStationShortCode()))
                return kaupungit.get(i).getStationName();
            else if (asemaKoodi.equalsIgnoreCase(kaupungit.get(i).getStationShortCode()))
                return kaupungit.get(i).getStationName() + " (ei matkustusliikenteen käytössä)";
        }

        return null;
    }

    //täällä palautetaan aseman koordinaatit kaupungin perusteella.
    //-Satu
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


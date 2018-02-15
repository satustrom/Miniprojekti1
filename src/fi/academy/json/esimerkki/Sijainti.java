package fi.academy.json.esimerkki;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Sijainti {

    public static void main(String[] args) {
        //JunatLiikkeella.liikkeella();
        haeJunanKoordinaatit(3);
    }

    public static void haeJunanKoordinaatit (int junaNumero) {
        List<JunaGPS> junat = lueJunanJSONData();

        for (int i = 0; i <junat.size() ; i++) {
            if (junat.get(i).getTrainNumber()==junaNumero){
                JunaGPS haettava = junat.get(i);
                System.out.println("\nHakemasi junan sijainti klo " +haettava.aikaLeimaStringina() + "\n\n" + haettava.getLocation());
            }
        }
    }


    private static List<JunaGPS> lueJunanJSONData() {

        String baseurl = "https://rata.digitraffic.fi/api/v1";
        try {
            URL url = new URL(baseurl+"/train-locations/latest/");
            ObjectMapper mapper = new ObjectMapper();
            CollectionType tarkempiListanTyyppi = mapper.getTypeFactory().constructCollectionType(ArrayList.class, JunaGPS.class);
            List<JunaGPS> junat = mapper.readValue(url, tarkempiListanTyyppi);  // pelkkä List.class ei riitä tyypiksi

            return junat;

        } catch (Exception ex) {
            System.out.println(ex);
        }
        return null;
    }
}

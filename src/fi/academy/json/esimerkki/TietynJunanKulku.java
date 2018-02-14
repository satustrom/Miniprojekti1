package fi.academy.json.esimerkki;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class TietynJunanKulku {

    public static void main(String[] args) {
        haeKaukojuna("IC", 266);
    }


    //metodi joka hakee avointa dataa, luo ja palauttaa listan Junia -Paula-
    private static List<Juna> lueJunanJSONData() {

        String baseurl = "https://rata.digitraffic.fi/api/v1";
        try {
            URL url = new URL(baseurl + "/live-trains/");
            ObjectMapper mapper = new ObjectMapper();
            CollectionType tarkempiListanTyyppi = mapper.getTypeFactory().constructCollectionType(ArrayList.class, Juna.class);
            List<Juna> junat = mapper.readValue(url, tarkempiListanTyyppi);
            return junat;

        } catch (Exception ex) {
            System.out.println(ex);

        }
        return null;
    }

    //metodi joka käy junalistaa läpi, hakee parametreina annetun tyypin ja numeron mukaisen junan
    // ja tulostaa sen pysähtymisasemat sekä aikataulun mukaisen ajan jolloin asemalla -Paula-

    public static void haeKaukojuna(String kaukojunaTyyppi, int kaukojunaNumero) {

        List<Juna> junat = lueJunanJSONData();

        for (int i = 0; i < junat.size(); i++) {
            if (junat.get(i).getTrainType().equals(kaukojunaTyyppi) && junat.get(i).getTrainNumber() == kaukojunaNumero) {
                Juna haettava = junat.get(i);
                List<TimeTableRow> lista = junat.get(i).timeTableRows;
                for (int j = 0; j < lista.size(); j++) {
                    System.out.println(lista.get(j).getStationShortCode() + ", aika: " + lista.get(j).getScheduledTime());
                }
            }
        }

    }
}
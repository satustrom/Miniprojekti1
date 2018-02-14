package fi.academy.json.esimerkki;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;



public class LahijunanKirjainHaku {

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

    public static void haeJuna(String junaKirjain) {

        List<Juna> junat = lueJunanJSONData();


        for (int i = 0; i < junat.size(); i++) {
            if (junat.get(i).getCommuterLineID() == junaKirjain) {
                Juna haettava = junat.get(i);
                List<TimeTableRow> lista = junat.get(i).timeTableRows;
                System.out.println("Hakemasi junan " + junaKirjain + " matkatiedot: \n \nLähtöpäivä: \t" + lista.get(0).haePVMStringina() + "\n");
                for (int j = 0; j < lista.size(); j++) {
                    if (lista.get(j).isTrainStopping())
                        if (j % 2 == 0) {
                            System.out.print(Asemat.palautaKaupunki(lista.get(j).getStationShortCode()) + " " + lista.get(j).haeKellonAikaStringina() + "  -  ");
                        } else {
                            System.out.println(lista.get(j).haeKellonAikaStringina() + " " + Asemat.palautaKaupunki(lista.get(j).getStationShortCode()));
                        }
                }
            }
        }

    }

}


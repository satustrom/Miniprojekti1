package fi.academy.json.esimerkki;

        import com.fasterxml.jackson.databind.ObjectMapper;
        import com.fasterxml.jackson.databind.type.CollectionType;

        import java.net.URL;
        import java.util.ArrayList;
        import java.util.Date;
        import java.util.List;



public class LahijunanKirjainHaku {

    //haetaan junat listaksi JSON datasta - Olli
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

    //Käydään läpi junalista ja poimitaan käyttäjän valitseman tunnuksen mukaiset. If ehdossa oleva metodi: "tuleva" rajaa
    // osumat tällä hetkellä ajossa oleviin ja tulevaisuudessa lähteviin. -Olli
    public static void haeJuna(String lahijunaKirjain) {

        int k = 0;

        List<Juna> junat = lueJunanJSONData();


        for (int i = 0; i < junat.size(); i++) {
            if (junat.get(i).getCommuterLineID().equals(lahijunaKirjain)
                    && tuleva(junat, i)) {

                //rajaa tulosteen kymmeneen aikatauluun -Olli
                if (k == 10) {
                    break;
                    }
                k++;

                List<TimeTableRow> lista = junat.get(i).timeTableRows;
                System.out.println("");
                System.out.println(k + ". " + lahijunaKirjain + "-junan aikataulu: \n");
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

    //Metodi, joka tarkistaa, että haetun junan scheduled time on ajanhetkeä vastaava. -Olli
    private static boolean tuleva(List<Juna> junat, int i) {
        for (int j = 0; j < junat.get(i).timeTableRows.size();j++) {
            if (junat.get(i).timeTableRows.get(j).getScheduledTime().after(new Date())) {
                return true;
            } else {
                continue;
            }
        }
        return false;
    }

}


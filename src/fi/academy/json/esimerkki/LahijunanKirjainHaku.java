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

    //Käydään läpi junalista ja poimitaan käyttäjän valitseman tunnuksen mukaiset junat. If ehdossa oleva metodi: "tuleva" rajaa
    // osumat tällä hetkellä ajossa oleviin ja tulevaisuudessa lähteviin. Käytetään Paulan Stringbuilder metodia, jolla
    //saadaan ulos samaa muotoa oleva tulostus. -Olli
    public static void haeJuna(String lahijunaKirjain) {

        int k = 0;

        List<Juna> junat = lueJunanJSONData();
        String asema = "";
        String aika;

        for (int i = 0; i < junat.size(); i++) {
            if (junat.get(i).getCommuterLineID().equals(lahijunaKirjain)
                    && tuleva(junat, i)) {

                //laskuri, joka keskeyttää haun kymmenen ensimmäisen aikataulun löytymisen jälkeen -Olli
                if (k == 10) {
                    break;
                }
                k++;


                List<TimeTableRow> lista = junat.get(i).timeTableRows;
                System.out.println("");
                System.out.println(k + ". " + lahijunaKirjain + "-junan aikataulu: \n");
                TietynJunanKulku.kokosatsi = new StringBuilder();
                for (int j = 0; j < lista.size(); j++) {
                    if (lista.get(j).isTrainStopping())

                        if (j == 0 || j % 2 == 0) {
                            asema = Asemat.palautaKaupunki(lista.get(j).getStationShortCode());
                            aika = lista.get(j).haeKellonAikaStringina();
                            TietynJunanKulku.muotoileParillinen(asema, aika);
                        } else {
                            asema = Asemat.palautaKaupunki(lista.get(j).getStationShortCode());
                            aika = lista.get(j).haeKellonAikaStringina();
                            TietynJunanKulku.muotoilePariton(aika, asema);
                        }


                }
            //Stringbuilderin sisällön tulostus ja tyhjennys seuraavan junan aikatauluja varten
            System.out.println(TietynJunanKulku.kokosatsi.toString());
            TietynJunanKulku.kokosatsi = new StringBuilder();

            }

        }



        //tyhjän haun palauttama teksti -Olli
        if (k == 0) {
                System.out.println("");
                System.out.println("Valitsemallasi hakuehdolla ei löytynyt aikatauluja.");
        }
    }

    //Metodi, joka tarkistaa, että haetun junan scheduled time on ajanhetkeä vastaava. -Olli
    public static boolean tuleva (List < Juna > junat,int i){
            for (int j = 0; j < junat.get(i).timeTableRows.size(); j++) {
                if (junat.get(i).timeTableRows.get(j).getScheduledTime().after(new Date())) {
                    return true;
                } else {
                    continue;
                }
            }
    return false;
    }
}



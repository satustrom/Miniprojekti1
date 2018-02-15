package fi.academy.json.esimerkki;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class TietynJunanKulku {

    public static void main(String[] args) {
        //JunatLiikkeella.liikkeella();
        haeJuna(274);
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

    public static void haeJuna(int junaNumero) {

        List<Juna> junat = lueJunanJSONData();
        String asema = "";
        String aika;
        String aikaTaulunAika;
        String toteutunutAika;


        for (int i = 0; i < junat.size(); i++) {
            if ( junat.get(i).getTrainNumber() == junaNumero) {
                Juna haettava = junat.get(i);
                List<TimeTableRow> lista = junat.get(i).timeTableRows;
                System.out.println("Hakemasi junan " + junaNumero+ " matkatiedot: \n \nLähtöpäivä: \t" + lista.get(0).haePVMStringina() + "\n");
                if (!haettava.isRunningCurrently()){
                    for (int j = 0; j < lista.size(); j++) {
                        if (lista.get(j).isTrainStopping())
                            if (j==0 || j%2==0){
                                asema = Asemat.palautaKaupunki(lista.get(j).getStationShortCode());
                                aika = lista.get(j).haeKellonAikaStringina();
                                tulostaParillinen(asema, aika);
                            } else {
                                asema = Asemat.palautaKaupunki(lista.get(j).getStationShortCode());
                                aika = lista.get(j).haeKellonAikaStringina();
                                tulostaPariton(aika, asema);
                        }
                    }
                } else {
                    for (int j = 0; j <lista.size() ; j++) {
                        if (lista.get(j).isTrainStopping()){
                            asema= Asemat.palautaKaupunki(lista.get(j).getStationShortCode());
                            aikaTaulunAika= lista.get(j).haeAikataulunAika();
                            toteutunutAika = lista.get(j).haeToteutunutAika();
                            tulostaKulussaOleva(asema, aikaTaulunAika, toteutunutAika);
                        }
                    }
                }
            }
        }
    }
    //Tulostuksien muotoilua StringBuildereilla -Paula
    private static void tulostaKulussaOleva(String asema, String aikaTaulunAika, String toteutunutAika) {
        StringBuilder muotoiltu = new StringBuilder();
        String a = asema;
        String b = aikaTaulunAika;
        String c = toteutunutAika;
        muotoiltu.append(a);
        if (a.length()<20){
            for (int i = 0; i <(20-(a.length())); i++) {
                muotoiltu.append(" ");
            }
            muotoiltu.append(b + "\t");
        }
        muotoiltu.append(c);
        System.out.println(muotoiltu);

    }

    private static void tulostaParillinen (String asema, String aika) {
        StringBuilder muotoiltu = new StringBuilder();
        muotoiltu.append(asema);
        if (asema.length()<20){
            for (int i = 0; i <(20-(asema.length())); i++) {
                muotoiltu.append(" ");
            }
            muotoiltu.append(aika);
        }
        System.out.print(muotoiltu);

    }

    public static void tulostaPariton(String aika, String asema) {
        StringBuilder muotoiltu = new StringBuilder();
        muotoiltu.append(" - " + aika + "\t " );
        muotoiltu.append(asema);
        System.out.println(muotoiltu);

    }

}
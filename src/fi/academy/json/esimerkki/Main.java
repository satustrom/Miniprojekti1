package fi.academy.json.esimerkki;

import com.sun.org.apache.xpath.internal.SourceTree;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import java.util.Date;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        System.out.println(new Date());
        Scanner lukija = new Scanner(System.in);

        System.out.println("Tervetuloa käyttämään junahakupalvelua. Valitse hakutoiminto jota haluaisit käyttää?");
        System.out.println("");

        while (true) {

            try {
                // Päävalikko, josta navigoidaan numerosyöttein eri hakutoimintoihin, väärä numerosyöte aloittaa luupin alusta -Olli
                System.out.println("");
                System.out.println("Valitse toiminnon numero ja paina Enter");
                System.out.println("1: Seuraavan junan haku lähtö- ja määräaseman perusteella.");
                System.out.println("2: Tietyn junan tietojen haku.");
                System.out.println("3: Lähijunien aikataulujen haku kirjaimen perusteella.");
                System.out.println("4: Lähtevien junien haku aseman perusteella.");
                System.out.println("9: Sulje ohjelma.");
                int hakuvalinta = Integer.parseInt(lukija.nextLine());

                // Antaa käyttäjältä hakumetodille: String lahtoasema ja String maaraesema -Olli
                if (hakuvalinta == 1) {
                    System.out.println("Seuraavan junan haku lähtö- ja määräaseman perusteella:");
                    System.out.println("");
                    System.out.println("Syötä lähtöasema:");
                    String lahtoAsema = lukija.nextLine();
                    System.out.println("Syötä määräasema:");
                    String maaraAsema = lukija.nextLine();
                    System.out.println("");
                    System.out.println("Kiitos. Haetaan seuraavaa junaa hakuehdoin lähtöasema: " + lahtoAsema + ", määräasema: " + maaraAsema + ".");
                    SeuraavaJuna.kahdenKaupunginVali(lahtoAsema, maaraAsema);


                    // Antaa käyttäjältä hakumetodille: int junaNumero -Olli
                } else if (hakuvalinta == 2) try {

                    System.out.println("Tietyn junan tietojen haku:");
                    System.out.println("");
                    System.out.println("Syötä junan numero:");
                    int junaNumero = Integer.parseInt(lukija.nextLine());
                    System.out.println("");
                    System.out.println("Kiitos. Haetaan junaa numerolla " + junaNumero + ".");
                    TietynJunanKulku.haeJuna(junaNumero);
                } catch (java.lang.NumberFormatException ex) {
                    System.out.println("Syötettävän tiedon tulee olla numero, palataan päävalikkoon.");

                } else if (hakuvalinta == 3) {
                    System.out.println("Lähijunien aikataulujen haku kirjaimen perusteella.");
                    System.out.println("Syötä lähijunan kirjaintunnus:");
                    String lahijunanKirjain = lukija.nextLine().toUpperCase();
                    System.out.println("");
                    System.out.println("Kiitos. Haetaan 10 aikataulua lähijunan tunnuksella " + lahijunanKirjain + ".");
                    LahijunanKirjainHaku.haeJuna(lahijunanKirjain);

                    // Antaa käyttäjältä hakumetodille: String lahtevienJunienAsema -Olli
                } else if (hakuvalinta == 4) {
                    System.out.println("Lähtevien junien haku aseman perusteella:");
                    System.out.println("Syötä lähtöasema.");
                    String lahtevienJunienAsema = lukija.nextLine();
                    System.out.println("");
                    System.out.println("Kiitos. Haetaan 5 lähtevää junaa paikasta " + lahtevienJunienAsema + ".");
                    SeuraavaJuna.tietyltaAsemalta(lahtevienJunienAsema);



                    // Lopettaa ohjelman -Olli
                } else if (hakuvalinta == 9) {
                    System.out.println("Kiitos kun käytit junahakupalvelua. Tervetuloa uudelleen");
                    break;
                } else {
                    System.out.println("Valintaasi ei löytynyt, ole hyvä ja valitse joku esitetyistä vaihtoehdoista");

                }
                }catch(java.lang.NumberFormatException ex){
                System.out.println("Valintaasi ei löytynyt, ole hyvä ja valitse joku esitetyistä vaihtoehdoista.");
            }
        }
    }
}
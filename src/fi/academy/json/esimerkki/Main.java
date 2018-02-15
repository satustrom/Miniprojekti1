package fi.academy.json.esimerkki;

import com.sun.org.apache.xpath.internal.SourceTree;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import java.util.Date;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner lukija = new Scanner(System.in);

        System.out.println("Tervetuloa käyttämään junahakupalvelua. Valitse päävalikosta hakutoiminto, jota haluaisit käyttää.");
        System.out.println("");

        while (true) {

            try {
                // Päävalikko, josta navigoidaan numerosyöttein eri hakutoimintoihin, väärä numerosyöte aloittaa luupin alusta -Olli
                System.out.println("");
                System.out.println("Päävalikko\nNäppäile haluamasi numero ja klikkaa 'Enter':\n");
                System.out.println("1: Informaatio tietystä junasta");
                System.out.println("2: Lähtö- ja määräaseman perusteella seuraavaksi lähtevä juna");
                System.out.println("3: Lähtöaseman perusteella lähtevien junien listaus");
                System.out.println("4: Aikataulut lähijunista");
                System.out.println("9: Sulje ohjelma");
                int hakuvalinta = Integer.parseInt(lukija.nextLine());

                // Antaa käyttäjältä hakumetodille: String lahtoasema ja String maaraesema -Olli
                if (hakuvalinta == 2) {
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
                } else if (hakuvalinta == 1)
                    try {

                        System.out.println("Tietyn junan tietojen haku:");
                        System.out.println("");
                        System.out.println("Syötä junan numero:");
                        int junaNumero = Integer.parseInt(lukija.nextLine());
                        System.out.println("");
                        System.out.println("Kiitos. Haetaan junaa numerolla " + junaNumero + ".");
                        TietynJunanKulku.haeJuna(junaNumero);
                    } catch (java.lang.NumberFormatException ex) {
                        System.out.println("Syötettävän tiedon tulee olla numero, palataan päävalikkoon.");

                    }
                else if (hakuvalinta == 4) {
                    System.out.println("Lähijunien aikataulujen haku kirjaimen perusteella.");
                    System.out.println("Syötä lähijunan kirjaintunnus:");
                    String lahijunanKirjain = lukija.nextLine().toUpperCase();
                    System.out.println("");
                    System.out.println("Kiitos. Haetaan 10 aikataulua lähijunan kirjaintunnuksella " + lahijunanKirjain + ".");
                    LahijunanKirjainHaku.haeJuna(lahijunanKirjain);

                    // Antaa käyttäjältä hakumetodille: String lahtevienJunienAsema -Olli
                } else if (hakuvalinta == 3) {
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
                    System.out.println("Valintaasi ei löytynyt.\n");

                }

            } catch (java.lang.NumberFormatException ex) {
                System.out.println("Valintaasi ei löytynyt.\n");
            }
            System.out.println("Haluatko palata Päävalikkoon?");
            System.out.println("1: Kyllä, kiitos");
            System.out.println("2: Ei, tämä riittää");
            int vastaus = Integer.parseInt(lukija.nextLine());
            if (vastaus == 1) {
                continue;
            } else if (vastaus == 2) {
                System.out.println("Kiitos, ohjelma suljetaan.");
                break;
            } else if(vastaus != 1 && vastaus !=2) {
                System.out.println("Anteeksi, vastauksesi meni ohi. Ohjelma suljetaan.");
                break;
            }

        }
    }
}
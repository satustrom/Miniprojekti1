package fi.academy.json.esimerkki;

import com.sun.org.apache.xpath.internal.SourceTree;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import java.io.IOException;
import java.util.Date;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {

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
                System.out.println("5: Liikkeellä olevat junat");
                System.out.println("6: Sulje ohjelma");
                System.out.println("Vastaus:");
                int hakuvalinta = Integer.parseInt(lukija.nextLine());
                System.out.println("-------------------------------------");

                // Antaa käyttäjältä hakumetodille: String lahtoasema ja String maaraesema -Olli
                if (hakuvalinta == 2) {
                    System.out.println("Seuraavan junan haku lähtö- ja määräaseman perusteella:");
                    System.out.println("");
                    System.out.println("Syötä lähtöasema:");
                    String lahtoAsema = lukija.nextLine();
                    String korjattuLahto = IsoAlkuKirjain.alkukirjainMuotoilu(lahtoAsema);
                    System.out.println("Syötä määräasema:");
                    String maaraAsema = lukija.nextLine();
                    String korjattuMaara = IsoAlkuKirjain.alkukirjainMuotoilu(maaraAsema);
                    System.out.println("");
                    System.out.println("Kiitos. Haetaan seuraavaa junaa hakuehdoin lähtöasema: " + korjattuLahto + ", määräasema: " + korjattuMaara + ".");
                    SeuraavaJuna.kahdenKaupunginVali(korjattuLahto,korjattuMaara);


                    // Antaa käyttäjältä hakumetodille: int junaNumero -Olli
                } else if (hakuvalinta == 1)
                    try {
                        System.out.println("Syötä vielä junan numero:");
                        int junaNumero = Integer.parseInt(lukija.nextLine());
                        System.out.println("\nValitse seuraavista, mitä informaatiota haluat saada: \n");
                        System.out.println("1: Junan aikataulu ja asemat");
                        System.out.println("2: Liikkeellä olevan junan sijainti koordinaatteina");
                        System.out.println("3: Liikkeellä olevan junan edellinen ja seuraava asema");
                        System.out.println("Vastaus: ");
                        int vastaus = Integer.parseInt(lukija.nextLine());

                        if (vastaus == 1) {
                            System.out.println("Valitsit aikataulun ja asemat. Haetaan junaa numerolla " + junaNumero + ".\n");
                            TietynJunanKulku.haeJuna(junaNumero);
                        }
                        if(vastaus == 2 ) {
                            System.out.println("Valitsit junan koordinaatit. Haetaan junaa numerolla " + junaNumero+ ".\n");
                            Sijainti.haeJunanKoordinaatit(junaNumero);
                        }
                        if(vastaus == 3) {
                            System.out.println("Valitsit etsiä kulussa olevan junan lähimmät asemat. Haetaan junaa numerolla " + junaNumero + ".\n");
                            String uusiJunaNumero = Integer.toString(junaNumero);
                            KulkutietoViestit.haeAsema(uusiJunaNumero);
                        }

                    } catch (java.lang.NumberFormatException ex) {
                        System.out.println("Syötettävän tiedon tulee olla numero, palataan päävalikkoon.\n");

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
                    System.out.println("Lähtevien junien haku aseman perusteella.");
                    System.out.println("Syötä lähtöasema:");
                    String lahtevienJunienAsema = lukija.nextLine();
                    String korjattuLahtevat = IsoAlkuKirjain.alkukirjainMuotoilu(lahtevienJunienAsema);
                    System.out.println("\nKiitos. Haetaan 5 lähtevää junaa paikasta " + korjattuLahtevat + ".");
                    SeuraavaJuna.tietyltaAsemalta(korjattuLahtevat);

                } else if(hakuvalinta == 5) {
                    System.out.println("Liikkeellä olevat junat:\n");
                    System.out.println("Syötä lähtöasema: ");
                    String lahteva = lukija.nextLine();
                    String korjattuLahteva = IsoAlkuKirjain.alkukirjainMuotoilu(lahteva);
                    System.out.println("Syötä määräasema:");
                    String maara = lukija.nextLine();
                    String korjattuMaara = IsoAlkuKirjain.alkukirjainMuotoilu(maara);
                    System.out.println("\nKiitos. Haetaan junia välillä: "+ korjattuLahteva+ " - " + korjattuMaara+ "\n");
                    JunatLiikkeella.liikkeella(korjattuLahteva,korjattuMaara);

                    // Lopettaa ohjelman -Olli
                } else if (hakuvalinta == 6) {
                    System.out.println("Kiitos kun käytit junahakupalvelua. Tervetuloa uudelleen");
                    break;
                } else {
                    System.out.println("Valintaasi ei löytynyt.\n");

                }

            } catch (java.lang.NumberFormatException ex) {
                System.out.println("Valintaasi ei löytynyt.\n");
            }
            System.out.println("\n------------------------------------------");
            System.out.println("Haku päättynyt. Haluatko palata Päävalikkoon?");
            System.out.println("1: Kyllä, kiitos");
            System.out.println("2: Ei, tämä riittää");
            System.out.println("Vastaus:");
            int vastaus = Integer.parseInt(lukija.nextLine());
            if (vastaus == 1) {
                continue;
            } else if (vastaus == 2) {
                System.out.println("Kiitos, ohjelma suljetaan.");
                break;
            } else if(vastaus != 1 && vastaus !=2) {
                System.out.println("Anteeksi, vastauksesi meni ohi. Palataan Päävalikkoon.");
                continue;
            }

        }
    }
}
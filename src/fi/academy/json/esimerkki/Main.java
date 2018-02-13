package fi.academy.json.esimerkki;

import com.sun.org.apache.xpath.internal.SourceTree;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner lukija = new Scanner(System.in);

        System.out.println("Tervetuloa käyttämään junahakupalvelua. Valitse hakutoiminto jota haluaisit käyttää?");
        System.out.println("");

        while (true) {

            // Päävalikko, josta navigoidaan numerosyöttein eri hakutoimintoihin, väärä numerosyöte aloittaa luupin alusta -Olli
            System.out.println("Valitse toiminnon numero ja paina Enter");
            System.out.println("1: Seuraavan junan haku lähtö- ja määräaseman perusteella.");
            System.out.println("2: Tietyn kaukojunan tietojen haku.");
            System.out.println("3: Tietyn lähijunan tietojen haku.");
            System.out.println("9: Sulje ohjelma.");
            int hakuvalinta = Integer.parseInt(lukija.nextLine());

            // Antaa käyttäjältä hakumetodille: String lahtoasema ja String maaraesema -Olli
            if (hakuvalinta == 1) {
                System.out.println("Seuraavan junan haku lähtö- ja määräaseman perusteella:");
                System.out.println("");
                System.out.println("Syötä lähtöasema:");
                String lahtoasema = lukija.nextLine();
                System.out.println("Syötä määräasema:");
                String maaraasema = lukija.nextLine();
                System.out.println("");
                System.out.println("Kiitos. Haetaan seuraavaa junaa hakuehdoin lähtöasema: " + lahtoasema + ", määräasema: " + maaraasema + ".");

                // Antaa käyttäjältä hakumetodille: int kaukojunaNumero ja String kaukojunaTyyppi -Olli
            } else if (hakuvalinta == 2) {
                System.out.println("Tietyn kaukojunan tietojen haku:");
                System.out.println("");
                System.out.println("Syötä kaukojunan tyyppi(IC = Intercity, P=Pendolino):");
                String kaukojunaTyyppi = lukija.nextLine();
                System.out.println("Syötä kaukojunan numero:");
                int kaukojunaNumero = Integer.parseInt(lukija.nextLine());
                System.out.println("");
                System.out.println("Kiitos. Haetaan kaukojunaa " + kaukojunaTyyppi + kaukojunaNumero + ".");

                // Antaa käyttäjältä hakumetodille: int lahijunaNumero ja String lahijunaTyyppi -Olli
            } else if (hakuvalinta == 3) {
                System.out.println("Tietyn lähijunan tietojen haku:");
                System.out.println("Syötä junan kirjainkoodi(Y,X,U,L,E,A,P,I,N,K,R,T,D,Z):");
                String lahijunaTyyppi = lukija.nextLine();
                System.out.println("Syötä junan numero");
                int lahijunaNumero = Integer.parseInt(lukija.nextLine());
                System.out.println("");
                System.out.println("Kiitos. Haetaan lähijunaa " + lahijunaTyyppi + lahijunaNumero + ".");

                // Lopettaa ohjelman -Olli
            } else if (hakuvalinta == 9 ) {
                System.out.println("Kiitos kun käytit junahakupalvelua. Tervetuloa uudelleen");
                break;
            }


        }
    }
}

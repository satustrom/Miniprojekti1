import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class JunatLiikkeella {
    public static void liikkeella() {
        String baseurl = "https://rata.digitraffic.fi/api/v1";
        try {
            URL url = new URL(baseurl+"/live-trains/station/KÄP");
            ObjectMapper mapper = new ObjectMapper();
            CollectionType tarkempiListanTyyppi = mapper.getTypeFactory().constructCollectionType(ArrayList.class, Juna.class);
            List<Juna> junat = mapper.readValue(url, tarkempiListanTyyppi);  // pelkkä List.class ei riitä tyypiksi
            System.out.println(junat.get(0).getTrainNumber());
            // Seuraavaa varten on toteutettava TimeTableRow luokka:
            //System.out.println(junat.get(0).getTimeTableRows().get(0).getScheduledTime());
            System.out.println("\n\n");
            System.out.println(junat.get(0));

        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
}

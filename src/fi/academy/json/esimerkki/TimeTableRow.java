package fi.academy.json.esimerkki;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TimeTableRow {

    private String stationShortCode;
    private int stationUICCode;
    private String countryCode;
    private String type;
    private boolean trainStopping;
    private boolean commercialStop;
    //private int commercialTrack;
    private boolean cancelled;
    @JsonFormat(pattern = "yyyy-MM-dd'T'hh:mm:ss.SSS'Z'")
    private Date scheduledTime;
    private String actualTime;
    private int differenceInMinutes;
    //private String [] causes;
    //private List<String> trainReady;

    public int getStationUICCode() {
        return stationUICCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getType() {
        return type;
    }

    public boolean isTrainStopping() {
        return trainStopping;
    }

    public boolean isCommercialStop() {
        return commercialStop;
    }

    /*public int getCommercialTrack() {
        return commercialTrack;
    }*/

    public boolean isCancelled() {
        return cancelled;
    }

    public String getActualtime() {
        return actualTime;
    }

    public int getDifferenceInMinutes() {
        return differenceInMinutes;
    }

    /* public String[] getCauses() {
        return causes;
    }

    public List<String> getTrainReady() {
        return trainReady;
    } */

    public String getStationShortCode() {
        return stationShortCode;
    }

    public Date getScheduledTime() {
        return scheduledTime;
    }
}

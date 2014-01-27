package com.krawetko.dominikanie;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

public class MeetingsScheduleRetriever implements Serializable {
    public MeetingsScheduleRetriever() {
    }

    private DatesGenerator datesGenerator = new DatesGenerator();

    public Map<String, String> getMeetingsForGivenWeaksAhead(int weaksAhead) throws IOException {
        List<String> fridayDates = datesGenerator.getFridayDates(weaksAhead);
        Map<String, String> scheduleByDay = new LinkedHashMap<>();

        for (String fridayDate : fridayDates) {
            String meetingsForSpecificDay = getMeetingsForSpecificDay(fridayDate);
            scheduleByDay.put(fridayDate, meetingsForSpecificDay);
        }

        return scheduleByDay;
    }

    public String getMeetingsForSpecificDay(String date) throws IOException {
        String urlString = "http://www.krakow.dominikanie.pl/php_inc/callendar-pull.php?date=" + date;
        URL url = new URL(urlString);
        URLConnection conn = url.openConnection();
        try (InputStream is = conn.getInputStream()) {
            String webPage = IOUtils.toString(is, "iso-8859-2");
            return webPage;
        }
    }

}
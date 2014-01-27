package com.krawetko.dominikanie;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;

import java.util.LinkedList;
import java.util.List;

import static com.google.appengine.repackaged.org.joda.time.DateTimeConstants.*;

/**
 * User: Kuba K
 * Date: 1/19/14
 * Time: 4:30 PM
 */
public class DatesGenerator {

    public List<String> getFridayDates(int weeksAhead) {
        List<String> dates = new LinkedList<>();
        LocalDate now = LocalDate.now();
        LocalDate nearestFriday = now.withDayOfWeek(FRIDAY);

        if (now.isAfter(nearestFriday)) {
            nearestFriday = nearestFriday.plusDays(DAYS_PER_WEEK);
        }

        for (int i = 0; i < weeksAhead; i++) {
            dates.add(nearestFriday.plusDays(i * DAYS_PER_WEEK).toString(DateTimeFormat.forPattern("YYYY-MM-dd")));
        }
        return dates;
    }
}

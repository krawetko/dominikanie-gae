package com.krawetko.dominikanie;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;

import java.util.LinkedList;
import java.util.List;

import static com.google.appengine.repackaged.org.joda.time.DateTimeConstants.DAYS_PER_WEEK;
import static com.google.appengine.repackaged.org.joda.time.DateTimeConstants.FRIDAY;
import static org.joda.time.DateTimeConstants.APRIL;

/**
 * User: Kuba K
 * Date: 1/19/14
 * Time: 4:30 PM
 */
public class DatesGenerator {

    public List<String> getFridayDates() {
        List<String> dates = new LinkedList<>();
        LocalDate now = LocalDate.now();
        LocalDate nextFriday = now.withDayOfWeek(FRIDAY);
        LocalDate lastFriday = new LocalDate(2014, APRIL, 18);

        if (now.isAfter(nextFriday)) {
            nextFriday = nextFriday.plusDays(DAYS_PER_WEEK);
        }

        while (nextFriday.isBefore(lastFriday)) {
            dates.add(nextFriday.toString(DateTimeFormat.forPattern("YYYY-MM-dd")));
            nextFriday = nextFriday.plusDays(DAYS_PER_WEEK);
        }

        return dates;
    }
}

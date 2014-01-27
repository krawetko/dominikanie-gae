package com.krawetko.dominikanie;

import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

public class ReportCreator {

    private MeetingsScheduleRetriever meetingsScheduleRetriever = new MeetingsScheduleRetriever();

    public String createReport(int weeksAhead) throws IOException {
        StringBuilder report = new StringBuilder();


        Map<String, String> meetingsForGivenWeaksAhead = meetingsScheduleRetriever.getMeetingsForGivenWeaksAhead(weeksAhead);
        for (String scheduleForWeek : meetingsForGivenWeaksAhead.values()) {
            report.append(scheduleForWeek);
            report.append("<br/>");
        }
        return report.toString();
    }
}
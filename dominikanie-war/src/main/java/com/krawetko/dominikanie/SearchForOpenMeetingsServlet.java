package com.krawetko.dominikanie;

import org.joda.time.LocalTime;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.io.StringReader;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * User: Kuba K
 * Date: 1/19/14
 * Time: 6:05 PM
 */
public class SearchForOpenMeetingsServlet extends HttpServlet {

    private MeetingsScheduleRetriever meetingsScheduleRetriever = new MeetingsScheduleRetriever();
    private EmailSender emailSender = new EmailSender();

    private Logger logger = Logger.getLogger(SearchForOpenMeetingsServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=UTF-8");

        Map<String, String> meetings = meetingsScheduleRetriever.getMeetingsForGivenWeaksAhead();

        try {
            Map<String, String> openedMeetingsByWeeks = getOpenDates(meetings);
            if (!openedMeetingsByWeeks.isEmpty()) {
                StringBuilder openMeetingsRaport = new StringBuilder();
                for (Map.Entry<String, String> openedMeetingsByWeek : openedMeetingsByWeeks.entrySet()) {
                    openMeetingsRaport.append(String.format("Wolne terminy w dniu <b>%s</b>:<br/>", openedMeetingsByWeek.getKey()));
                    openMeetingsRaport.append(openedMeetingsByWeek.getValue());
                    openMeetingsRaport.append("<br/>");
                    openMeetingsRaport.append(createReservationLink(openedMeetingsByWeek.getKey()));
                    openMeetingsRaport.append("<br/><br/>");
                }
                emailSender.sendDominikanskiEmail("Wolne terminy!!!", openMeetingsRaport.toString(), "j.kubow@gmail.com", "dowlika@gmail.com");
            } else {
                if (new LocalTime().getHourOfDay() % 10 == 0) {
                    emailSender.sendDominikanskiEmail("Dzialam", "dzialam", "krawetko@gmail.com");
                }
            }
        } catch (XPathExpressionException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new ServletException(e);
        }

    }

    private String createReservationLink(String date) {
        return String.format("Zarezerwuj na dzien <b>%s: <a href='http://www.krakow.dominikanie.pl/step,2,date,%s,rezerwacja_wizyty.html'>http://www.krakow.dominikanie.pl/step,2,date,%s,rezerwacja_wizyty.html</a></b>", date, date, date);
    }


    private Map<String, String> getOpenDates(Map<String, String> meetingsSchedule) throws XPathExpressionException {
        Map<String, String> openedMeetingsByDate = new LinkedHashMap<>();

        for (Map.Entry<String, String> weekSchedule : meetingsSchedule.entrySet()) {
            String weekScheduleValue = sanitizeXml(weekSchedule.getValue());
            InputSource inputSource = new InputSource(new StringReader(weekScheduleValue));

            XPathFactory xPathfactory = XPathFactory.newInstance();
            XPath xpath = xPathfactory.newXPath();
            NodeList nl = (NodeList) xpath.evaluate("//div[contains(@class, 'open')]", inputSource, XPathConstants.NODESET);

            String openedMeetings = extractOpenMeetings(nl);
            if (openedMeetings != null) {
                openedMeetingsByDate.put(weekSchedule.getKey(), openedMeetings);
            }


        }

        return openedMeetingsByDate;

    }

    private String extractOpenMeetings(NodeList nl) {
        if (nl.getLength() == 0) return null;

        StringBuilder openedMeetings = new StringBuilder();
        for (int i = 0; i < nl.getLength(); i++) {
            Node openedMeeting = nl.item(i);
            openedMeetings.append(openedMeeting.getTextContent());
            openedMeetings.append("<br/>");
        }
        return openedMeetings.toString();
    }

    private String sanitizeXml(String meetingsSchedule) {
        return "<root>" + meetingsSchedule + "</root>";
    }

}

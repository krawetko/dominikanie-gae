package com.krawetko.dominikanie;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * User: Kuba K
 * Date: 1/19/14
 * Time: 8:22 PM
 */
public class DailyRaport extends HttpServlet {

    private final ReportCreator reportCreator = new ReportCreator();
    private EmailSender emailSender = new EmailSender();

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        resp.setContentType("text/html; charset=UTF-8");

        String report = reportCreator.createReport();

        emailSender.sendDominikanskiEmail("Raport dnia", report, "krawetko@gmail.com", "kolasinska.magda@gmail.com");
    }

}

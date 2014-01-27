package com.krawetko.dominikanie;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * User: Kuba K
 * Date: 1/19/14
 * Time: 3:47 PM
 */
public class WelcomeServlet extends HttpServlet {

    public static final int WEEKS_AHEAD = 13;

    private ReportCreator reportCreator = new ReportCreator();

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        resp.setContentType("text/html; charset=UTF-8");

        try (PrintWriter out = resp.getWriter()) {
            String report = reportCreator.createReport(WEEKS_AHEAD);
            out.write(report);
        }
    }

}

package com.krawetko.dominikanie;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * User: Kuba K
 * Date: 1/19/14
 * Time: 5:02 PM
 */
public class EmailSender {

    private static Logger logger = Logger.getLogger(EmailSender.class.getName());

    public void sendDominikanskiEmail(String subject, String message, String... recipients) throws UnsupportedEncodingException {
        sendEmail("krawetko@gmail.com", subject, message, recipients);
    }

    public void sendEmail(String from, String subject, String message, String... recipients) {
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);

        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(from));
            for (String recipient : recipients) {
                msg.addRecipient(Message.RecipientType.CC,
                        new InternetAddress(recipient));
            }
            msg.setSubject(subject);
            msg.setContent(message, "text/html");

            Transport.send(msg);
            logger.info("Email sent to: " + Arrays.toString(recipients));

        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
    }
}

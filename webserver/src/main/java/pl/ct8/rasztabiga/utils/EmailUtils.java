package pl.ct8.rasztabiga.utils;


import org.hibernate.validator.constraints.Email;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Properties;


public class EmailUtils {

    private static final Properties properties = System.getProperties();

    private final static String username = "admin@klasa1a.ct8.pl";
    private final static String password = "Gallendors5";

    static {
        properties.setProperty("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "s1.ct8.pl");
        properties.put("mail.smtp.port", "587");
    }

    public static void sendEmail(String address, String message) {
        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress("admin@klasa1a.ct8.pl", "Klasa1a admin"));
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(address));
            msg.setSubject("Your private Api key");
            msg.setText(message);
            Transport.send(msg);
        } catch (UnsupportedEncodingException | MessagingException e) {
            e.printStackTrace();
        }

    }

}

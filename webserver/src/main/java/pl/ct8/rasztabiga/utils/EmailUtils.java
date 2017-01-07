package pl.ct8.rasztabiga.utils;


import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Properties;
import java.util.logging.Logger;


public class EmailUtils {

    private static final Properties properties = System.getProperties();
    private final static String username = "admin@klasa1a.ct8.pl";
    private final static String password = "Gallendors5";
    private static Logger logger = Logger.getLogger(SecurityUtils.class.getSimpleName());

    static {
        LoggerUtils.setUpLogger(logger);
    }

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
            msg.setSubject("Twój klucz dostępu");
            msg.setText(message);
            Transport.send(msg);
        } catch (UnsupportedEncodingException | MessagingException e) {
            logger.warning(e.getMessage());
        }

    }

}

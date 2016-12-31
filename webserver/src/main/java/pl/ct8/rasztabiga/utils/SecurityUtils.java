package pl.ct8.rasztabiga.utils;

import pl.ct8.rasztabiga.DatabaseController;
import pl.ct8.rasztabiga.models.User;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class SecurityUtils {

    static Logger logger = Logger.getLogger("Main");
    static FileHandler fh;
    static ConsoleHandler ch;

    static {
        try {

            // This block configure the logger with handler and formatter
            fh = new FileHandler("LogFile.log");
            ch = new ConsoleHandler();
            logger.addHandler(fh);
            logger.addHandler(ch);
            ch.setEncoding("UTF8");
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);
            fh.setEncoding("UTF8");

        } catch (SecurityException | IOException e) {
            e.printStackTrace();
        }
    }


    public static boolean authenticate(String apiKey, Role role) throws SQLException, ApiKeyNotFoundException, NoPermissionsException {
        User user = DatabaseController.getUser(apiKey);
        if (user == null) {
            throw new ApiKeyNotFoundException("Nie znaleziono takiego klucza api. Skontaktuj się z administratorem, jeżeli uważasz, że wystąpił błąd.");
        }
        /*if (user.getRole().getVal() < role.getVal()) {
            throw new NoPermissionsException("Masz za małe uprawnienia!");
        }*/

        if (!user.getRoleList().contains(role)) {
            throw new NoPermissionsException("Masz za małe uprawnienia!");
        }

        writeUserToLog(user);

        return true;
    }

    private static void writeUserToLog(User user) {
        logger.info(user.getName() + " " + user.getSurname() + " " + user.getApiKey());
    }

    public enum Role {
        USER("user") {
            @Override
            public String toString() {
                return "user";
            }
        },

        ADMIN("admin") {
            @Override
            public String toString() {
                return "admin";
            }
        };

        private final String val;
        private Role(String v) { val = v; }

        public String getVal() {
            return val;
        }
    }

    public static Role resolveRole(String role) {
        switch (role) {
            case "user":
                return Role.USER;
            case "admin":
                return Role.ADMIN;
            default:
                return null;
        }
    }
}

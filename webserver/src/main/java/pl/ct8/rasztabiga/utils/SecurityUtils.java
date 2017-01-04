package pl.ct8.rasztabiga.utils;

import pl.ct8.rasztabiga.Analytics;
import pl.ct8.rasztabiga.DatabaseController;
import pl.ct8.rasztabiga.models.User;

import java.sql.SQLException;
import java.util.logging.Logger;

public class SecurityUtils {

    private static Logger logger = LoggerUtils.getLogger();


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
        //TODO add external db for analytics
        //Analytics.updateUserRequestsAmountByOne(user);
        new Thread(new UpdateUserRequestsAmountByOneRunnable(user)).start();

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

        Role(String v) {
            val = v;
        }

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

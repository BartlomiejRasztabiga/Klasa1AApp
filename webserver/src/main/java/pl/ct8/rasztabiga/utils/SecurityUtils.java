package pl.ct8.rasztabiga.utils;

import pl.ct8.rasztabiga.DatabaseController;
import pl.ct8.rasztabiga.models.User;

import java.sql.SQLException;

public class SecurityUtils {

    public static boolean authenticate(String apiKey, Role role) throws SQLException, ApiKeyNotFoundException, NoPermissionsException {
        User user = DatabaseController.getUser(apiKey);
        if (user == null) {
            throw new ApiKeyNotFoundException("Nie znaleziono takiego klucza api. Skontaktuj się z administratorem, jeżeli uważasz, że wystąpił błąd.");
        }
        if (user.getRole().getVal() < role.getVal()) {
            throw new NoPermissionsException("Masz za małe uprawnienia!");
        }

        return true;
    }

    public enum Role {
        USER(1) {
            @Override
            public String toString() {
                return "user";
            }
        },

        ADMIN(10) {
            @Override
            public String toString() {
                return "admin";
            }
        };

        private final int val;
        private Role(int v) { val = v; }

        public int getVal() {
            return val;
        }
    }
}

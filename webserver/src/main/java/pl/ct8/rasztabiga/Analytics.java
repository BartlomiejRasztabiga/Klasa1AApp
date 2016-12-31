package pl.ct8.rasztabiga;


import pl.ct8.rasztabiga.models.User;

import java.sql.SQLException;

public class Analytics {

    public static void updateUserRequestsAmountByOne(User user) {
        try {
            DatabaseController.bumpUserAnalitycsField(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

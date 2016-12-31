package pl.ct8.rasztabiga;


import pl.ct8.rasztabiga.models.User;
import pl.ct8.rasztabiga.utils.LoggerUtils;
import pl.ct8.rasztabiga.utils.SecurityUtils;

import java.sql.SQLException;
import java.util.logging.Logger;

public class Analytics {

    private static Logger logger = LoggerUtils.getLogger();

    public static void updateUserRequestsAmountByOne(User user) {
        try {
            DatabaseController.bumpUserAnalitycsField(user);
        } catch (SQLException e) {
            logger.warning(e.getMessage());
        }
    }
}

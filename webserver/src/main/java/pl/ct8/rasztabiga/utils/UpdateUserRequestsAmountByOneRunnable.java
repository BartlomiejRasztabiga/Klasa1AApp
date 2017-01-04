package pl.ct8.rasztabiga.utils;

import pl.ct8.rasztabiga.Analytics;
import pl.ct8.rasztabiga.models.User;


public class UpdateUserRequestsAmountByOneRunnable implements Runnable {

    private User user;

    public UpdateUserRequestsAmountByOneRunnable(User user) {
        this.user = user;
    }

    @Override
    public void run() {
        Analytics.updateUserRequestsAmountByOne(user);
    }
}

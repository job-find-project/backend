package org.project.job.utility;

import java.util.Calendar;
import java.util.Date;

public class TokenExpirationTimeCalculus {
    private static int EXPIRATION_TIME = 10;

    public static Date getExpirationTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        calendar.add(Calendar.MINUTE, EXPIRATION_TIME);
        return new Date(calendar.getTime().getTime());
    }
}

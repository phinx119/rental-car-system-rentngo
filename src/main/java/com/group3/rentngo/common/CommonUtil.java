package com.group3.rentngo.common;

import org.springframework.stereotype.Component;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * @author phinx
 * @description class contain common method
 */
@Component
public class CommonUtil {
    /**
     * @author phinx
     * @description parse string to date
     */
    public Date parseDate(String dateString) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date utilDate = formatter.parse(dateString);
        return new Date(utilDate.getTime());
    }

    /**
     * @author phinx
     * @description parse string to date include time
     */
    public Date parseDateTime(String dateString, String timeString) {
        String dateTime = dateString.concat(" ").concat(timeString);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        try {
            java.util.Date utilDate = sdf.parse(dateTime);
            return new Date(utilDate.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @author phinx
     * @description parse string to date include time
     */
    public Date parseDateTimeForVnPay(String dateString) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        java.util.Date utilDate = formatter.parse(dateString);
        return new Date(utilDate.getTime());
    }

    /**
     * @author phinx
     * @description get days difference between 2 day
     */
    public long getDaysDifference(Date date1, Date date2) {
        long millisecondsPerDay = 24 * 60 * 60 * 1000; // Milliseconds in a day
        long timeDifference = date2.getTime() - date1.getTime();
        return Math.abs(timeDifference / millisecondsPerDay);
    }
}

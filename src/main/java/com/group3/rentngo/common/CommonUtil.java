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
    public Date parseDateTime(String dateString) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        java.util.Date utilDate = formatter.parse(dateString);
        return new Date(utilDate.getTime());
    }
}

package com.group3.rentngo.common;

import org.springframework.stereotype.Component;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@Component
public class CommonUtil {
    public Date parseDateTime(String dateString) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        java.util.Date utilDate = formatter.parse(dateString);
        return new Date(utilDate.getTime());
    }

    public Date parseDate(String dateString) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date utilDate = formatter.parse(dateString);
        return new Date(utilDate.getTime());
    }
}

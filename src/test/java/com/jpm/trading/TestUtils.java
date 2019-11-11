package com.jpm.trading;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static java.lang.System.out;

public class TestUtils {

    public Date parseDate(String dateString) {
        DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);

        Date date = new Date();

        try {
            date = dateFormat.parse(dateString);
        } catch (ParseException ex) {
            out.println(ex.getMessage());
        }

        return date;
    }
}

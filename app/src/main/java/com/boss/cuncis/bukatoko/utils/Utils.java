package com.boss.cuncis.bukatoko.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Utils {
    public static String getDateEvent(String inputDate) {
        Date parsed = null;
        String outputDated = "";

        SimpleDateFormat df_input = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        SimpleDateFormat df_output = new SimpleDateFormat("dd MMM yyyy", Locale.US);

        try {
            parsed = df_input.parse(inputDate);
            outputDated = df_output.format(parsed);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return outputDated;
    }

}

package model;

import java.text.DecimalFormat;

public class Money {
    public static String format(String s){
        DecimalFormat df = new DecimalFormat("#,###");
        return df.format(Double.parseDouble(s.trim())).replace(',','.');
    }

    public static Double unFormat(String s){
        return Double.parseDouble(s.trim().replace(".",""));
    }
}

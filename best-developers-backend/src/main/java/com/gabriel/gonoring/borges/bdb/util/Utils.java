package com.gabriel.gonoring.borges.bdb.util;

public class Utils {

    public static boolean isStringNullOrBlack(String s){
        return s==null || s.isBlank();
    }

    public static boolean isStringNotNullAndNotBlack(String s){
        return s!=null && !s.isBlank();
    }
}

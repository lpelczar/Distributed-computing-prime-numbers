package utils;

import java.math.BigInteger;

public class NumericChecker {

    public static boolean isNumeric(String s) {
        try {
            new BigInteger(s);
        } catch(NumberFormatException | NullPointerException e) {
            return false;
        }
        return true;
    }
}

package pl.ct8.rasztabiga.utils;


import java.math.BigInteger;
import java.security.SecureRandom;

public class ApiCodeGenerator {
    private static SecureRandom random = new SecureRandom();

    public static String nextApiCode() {
        return new BigInteger(130, random).toString(32);
    }
}

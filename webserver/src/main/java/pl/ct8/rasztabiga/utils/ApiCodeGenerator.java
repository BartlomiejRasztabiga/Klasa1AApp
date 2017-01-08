package pl.ct8.rasztabiga.utils;


import java.math.BigInteger;
import java.security.SecureRandom;

public class ApiCodeGenerator {
    private static final SecureRandom random = new SecureRandom();

    public static String nextApiCode() {
        return new BigInteger(32, random).toString(32);
    }
}

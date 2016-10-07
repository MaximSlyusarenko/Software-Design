package ru.ifmo.ctddev.slyusarenko.sd.lab2;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;

/**
 * @author Maxim Slyusarenko
 * @since 05.10.16
 */
public class Main {

    private static final int HOURS = 7;

    public static void main(String[] args) {
        TwitterService twitterService = new TwitterService();
        try {
            int[] result = twitterService.getHashTagStatistics("Fun", HOURS);
            for (int aResult : result) {
                System.out.print(aResult + " ");
            }
        } catch (IOException | NoSuchAlgorithmException | InvalidKeyException | ParseException e) {
            e.printStackTrace();
        }
    }
}

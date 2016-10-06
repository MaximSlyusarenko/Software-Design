package ru.ifmo.ctddev.slyusarenko.sd.lab2;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * @author Maxim Slyusarenko
 * @since 05.10.16
 */
public class Main {

    public static void main(String[] args) {
        TwitterService twitterService = new TwitterService();
        try {
            twitterService.getHashTagStatistics("aaa", 24);
        } catch (IOException | NoSuchAlgorithmException | InvalidKeyException e) {
            e.printStackTrace();
        }
    }
}

package ru.ifmo.ctddev.slyusarenko.sd.lab2;

import ru.ifmo.ctddev.slyusarenko.sd.core.HttpBasedProcessor;

import java.io.IOException;

/**
 * @author Maxim Slyusarenko
 * @since 05.10.16
 */
public class TwitterService {

    private static final String URL = "https://api.twitter.com/1.1/search/tweets.json";

    private final HttpBasedProcessor processor;

    public TwitterService() {
        processor = new HttpBasedProcessor();
    }

    public int[] getHashTagStatistics(String hashTag, int hours) {
        int[] result = new int[hours];
        try {
            processor.execute(URL);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // TODO: parse String to find answer
        return result;
    }
}

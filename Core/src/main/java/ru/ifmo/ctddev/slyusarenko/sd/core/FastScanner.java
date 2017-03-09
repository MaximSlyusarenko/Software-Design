package ru.ifmo.ctddev.slyusarenko.sd.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * @author Maxim Slyusarenko
 * @since 09.03.17
 */
public class FastScanner {

    private BufferedReader bufferedReader;
    private StringTokenizer stringTokenizer;

    public FastScanner() {
        bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    }

    public String next() {
        while (stringTokenizer == null || !stringTokenizer.hasMoreTokens()) {
            try {
                stringTokenizer = new StringTokenizer(bufferedReader.readLine());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return stringTokenizer.nextToken();
    }

    public void close() {
        if (bufferedReader != null) {
            try {
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

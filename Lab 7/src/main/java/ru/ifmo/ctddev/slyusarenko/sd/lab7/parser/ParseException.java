package ru.ifmo.ctddev.slyusarenko.sd.lab7.parser;

/**
 * @author Maxim Slyusarenko
 * @since 14.12.16
 */
public class ParseException extends Exception {

    private String message;

    public ParseException(String message) {
        super(message);
    }
}

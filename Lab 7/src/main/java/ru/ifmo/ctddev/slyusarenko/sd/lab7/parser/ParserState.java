package ru.ifmo.ctddev.slyusarenko.sd.lab7.parser;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Maxim Slyusarenko
 * @since 14.12.16
 */
@Getter
@Setter
public class ParserState {

    private int parenthesisBalance;
    private int currentNumber;

    public ParserState() {
        parenthesisBalance = 0;
        currentNumber = -1;
    }
}

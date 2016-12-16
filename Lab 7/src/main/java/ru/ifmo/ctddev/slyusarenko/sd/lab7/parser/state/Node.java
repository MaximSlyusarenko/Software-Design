package ru.ifmo.ctddev.slyusarenko.sd.lab7.parser.state;

import ru.ifmo.ctddev.slyusarenko.sd.lab7.parser.ParseException;
import ru.ifmo.ctddev.slyusarenko.sd.lab7.parser.ParserState;
import ru.ifmo.ctddev.slyusarenko.sd.lab7.token.Token;

import java.util.List;

/**
 * @author Maxim Slyusarenko
 * @since 17.12.16
 */
public interface Node {

    Node onOpenParenthesis(ParserState state, List<Token> tokens);
    Node onCloseParenthesis(ParserState state, List<Token> tokens) throws ParseException;
    Node onDigit(ParserState state, List<Token> tokens, int digit);
    Node onPlus(ParserState state, List<Token> tokens);
    Node onMinus(ParserState state, List<Token> tokens);
    Node onMultiply(ParserState state, List<Token> tokens);
    Node onDivide(ParserState state, List<Token> tokens);
    Node onWhitespace(ParserState state, List<Token> tokens);
    void finish(ParserState state, List<Token> tokens) throws ParseException;
}

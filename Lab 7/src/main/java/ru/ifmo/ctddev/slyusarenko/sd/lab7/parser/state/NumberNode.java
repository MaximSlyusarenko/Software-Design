package ru.ifmo.ctddev.slyusarenko.sd.lab7.parser.state;

import ru.ifmo.ctddev.slyusarenko.sd.lab7.parser.ParseException;
import ru.ifmo.ctddev.slyusarenko.sd.lab7.parser.ParserState;
import ru.ifmo.ctddev.slyusarenko.sd.lab7.token.*;

import java.util.List;

/**
 * @author Maxim Slyusarenko
 * @since 17.12.16
 */
public class NumberNode implements Node {

    private void maybeAddCurrentNumberToTokens(ParserState state, List<Token> tokens) {
        if (state.getCurrentNumber() != -1) {
            tokens.add(new NumberToken(state.getCurrentNumber()));
            state.setCurrentNumber(-1);
        }
    }

    @Override
    public Node onOpenParenthesis(ParserState state, List<Token> tokens) {
        state.setParenthesisBalance(state.getParenthesisBalance() + 1);
        maybeAddCurrentNumberToTokens(state, tokens);
        return new OtherNode(new Brace(BraceType.LEFT));
    }

    @Override
    public Node onCloseParenthesis(ParserState state, List<Token> tokens) throws ParseException {
        if (state.getParenthesisBalance() <= 0) {
            throw new ParseException("Unexpected closed parenthesis");
        }
        state.setParenthesisBalance(state.getParenthesisBalance() - 1);
        maybeAddCurrentNumberToTokens(state, tokens);
        return new OtherNode(new Brace(BraceType.RIGHT));
    }

    @Override
    public Node onDigit(ParserState state, List<Token> tokens, int digit) {
        state.setCurrentNumber((state.getCurrentNumber() == -1 ? 0 : state.getCurrentNumber() * 10) + digit);
        return new NumberNode();
    }

    @Override
    public Node onPlus(ParserState state, List<Token> tokens) {
        maybeAddCurrentNumberToTokens(state, tokens);
        return new OtherNode(new Operation(OperationType.PLUS));
    }

    @Override
    public Node onMinus(ParserState state, List<Token> tokens) {
        maybeAddCurrentNumberToTokens(state, tokens);
        return new OtherNode(new Operation(OperationType.MINUS));
    }

    @Override
    public Node onMultiply(ParserState state, List<Token> tokens) {
        maybeAddCurrentNumberToTokens(state, tokens);
        return new OtherNode(new Operation(OperationType.MULTIPLY));
    }

    @Override
    public Node onDivide(ParserState state, List<Token> tokens) {
        maybeAddCurrentNumberToTokens(state, tokens);
        return new OtherNode(new Operation(OperationType.DIVIDE));
    }

    @Override
    public Node onWhitespace(ParserState state, List<Token> tokens) {
        maybeAddCurrentNumberToTokens(state, tokens);
        return new NumberNode();
    }

    @Override
    public void finish(ParserState state, List<Token> tokens) throws ParseException {
        if (state.getParenthesisBalance() != 0) {
            throw new ParseException("Parenthesis not matched");
        }
        maybeAddCurrentNumberToTokens(state, tokens);
    }
}

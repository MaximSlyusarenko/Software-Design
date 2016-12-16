package ru.ifmo.ctddev.slyusarenko.sd.lab7.parser.state;

import ru.ifmo.ctddev.slyusarenko.sd.lab7.parser.ParseException;
import ru.ifmo.ctddev.slyusarenko.sd.lab7.parser.ParserState;
import ru.ifmo.ctddev.slyusarenko.sd.lab7.token.*;

import java.util.List;

/**
 * @author Maxim Slyusarenko
 * @since 17.12.16
 */
public class OtherNode implements Node {

    private final Token token;

    public OtherNode(Token token) {
        this.token = token;
    }

    @Override
    public Node onOpenParenthesis(ParserState state, List<Token> tokens) {
        state.setParenthesisBalance(state.getParenthesisBalance() + 1);
        tokens.add(token);
        return new OtherNode(new Brace(BraceType.LEFT));
    }

    @Override
    public Node onCloseParenthesis(ParserState state, List<Token> tokens) throws ParseException {
        if (state.getParenthesisBalance() <= 0) {
            throw new ParseException("Unexpected closed parenthesis");
        }
        state.setParenthesisBalance(state.getParenthesisBalance() - 1);
        tokens.add(token);
        return new OtherNode(new Brace(BraceType.RIGHT));
    }

    @Override
    public Node onDigit(ParserState state, List<Token> tokens, int digit) {
        state.setCurrentNumber(digit);
        tokens.add(token);
        return new NumberNode();
    }

    @Override
    public Node onPlus(ParserState state, List<Token> tokens) {
        tokens.add(token);
        return new OtherNode(new Operation(OperationType.PLUS));
    }

    @Override
    public Node onMinus(ParserState state, List<Token> tokens) {
        tokens.add(token);
        return new OtherNode(new Operation(OperationType.MINUS));
    }

    @Override
    public Node onMultiply(ParserState state, List<Token> tokens) {
        tokens.add(token);
        return new OtherNode(new Operation(OperationType.MULTIPLY));
    }

    @Override
    public Node onDivide(ParserState state, List<Token> tokens) {
        tokens.add(token);
        return new OtherNode(new Operation(OperationType.DIVIDE));
    }

    @Override
    public Node onWhitespace(ParserState state, List<Token> tokens) {
        return new OtherNode(token);
    }

    @Override
    public void finish(ParserState state, List<Token> tokens) throws ParseException {
        if (state.getParenthesisBalance() != 0) {
            throw new ParseException("Parenthesis not matched");
        }
        tokens.add(token);
    }
}

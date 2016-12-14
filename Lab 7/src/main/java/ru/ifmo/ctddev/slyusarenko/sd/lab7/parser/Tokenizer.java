package ru.ifmo.ctddev.slyusarenko.sd.lab7.parser;

import ru.ifmo.ctddev.slyusarenko.sd.lab7.token.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Maxim Slyusarenko
 * @since 14.12.16
 */
public class Tokenizer {

    private ParserState state;

    public Tokenizer() {
        state = new ParserState();
    }

    private void maybeAddNumberToTokens(List<Token> tokens) {
        if (state.getCurrentNumber() != -1) {
            tokens.add(new NumberToken(state.getCurrentNumber()));
            state.setCurrentNumber(-1);
        }
    }

    public List<Token> parse(InputStream is) throws IOException, ParseException {
        List<Token> result = new ArrayList<>();
        int cur;
        int position = 0;
        while ((cur = is.read()) != -1) {
            char c = (char) cur;
            if (c >= '0' && c <= '9') {
                state.setCurrentNumber((state.getCurrentNumber() == -1 ? 0 : state.getCurrentNumber()) * 10 + cur - '0');
                continue;
            }
            if (Character.isWhitespace(c)) {
                maybeAddNumberToTokens(result);
                continue;
            }
            switch (c) {
                case '(':
                    maybeAddNumberToTokens(result);
                    state.setParenthesisBalance(state.getParenthesisBalance() + 1);
                    result.add(new Brace(BraceType.LEFT));
                    break;
                case ')':
                    maybeAddNumberToTokens(result);
                    if (state.getParenthesisBalance() == 0) {
                        throw new ParseException("Unexpected closed parenthesis on position " + position);
                    }
                    state.setParenthesisBalance(state.getParenthesisBalance() - 1);
                    result.add(new Brace(BraceType.RIGHT));
                    break;
                case '+':
                    maybeAddNumberToTokens(result);
                    result.add(new Operation(OperationType.PLUS));
                    break;
                case '-':
                    maybeAddNumberToTokens(result);
                    result.add(new Operation(OperationType.MINUS));
                    break;
                case '*':
                    maybeAddNumberToTokens(result);
                    result.add(new Operation(OperationType.MULTIPLY));
                    break;
                case '/':
                    maybeAddNumberToTokens(result);
                    result.add(new Operation(OperationType.DIVIDE));
                    break;
                default:
                    throw new ParseException("Unexpected symbol " + c + " on position " + position);
            }
            position++;
        }
        maybeAddNumberToTokens(result);
        return result;
    }
}

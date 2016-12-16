package ru.ifmo.ctddev.slyusarenko.sd.lab7.parser;

import ru.ifmo.ctddev.slyusarenko.sd.lab7.parser.state.Node;
import ru.ifmo.ctddev.slyusarenko.sd.lab7.parser.state.NumberNode;
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

    public List<Token> parse(InputStream is) throws IOException, ParseException {
        List<Token> result = new ArrayList<>();
        int cur;
        int position = 0;
        Node currentNode = new NumberNode();
        ParserState state = new ParserState();
        while ((cur = is.read()) != -1) {
            char c = (char) cur;
            if (c >= '0' && c <= '9') {
                currentNode = currentNode.onDigit(state, result, c - '0');
                continue;
            }
            if (Character.isWhitespace(c)) {
                currentNode = currentNode.onWhitespace(state, result);
                continue;
            }
            switch (c) {
                case '(':
                    currentNode = currentNode.onOpenParenthesis(state, result);
                    break;
                case ')':
                    currentNode = currentNode.onCloseParenthesis(state, result);
                    break;
                case '+':
                    currentNode = currentNode.onPlus(state, result);
                    break;
                case '-':
                    currentNode = currentNode.onMinus(state, result);
                    break;
                case '*':
                    currentNode = currentNode.onMultiply(state, result);
                    break;
                case '/':
                    currentNode = currentNode.onDivide(state, result);
                    break;
                default:
                    throw new ParseException("Unexpected symbol " + c + " on position " + position);
            }
            position++;
        }
        currentNode.finish(state, result);
        return result;
    }
}

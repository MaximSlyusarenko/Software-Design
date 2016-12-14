package ru.ifmo.ctddev.slyusarenko.sd.lab7.visitor;

import lombok.Getter;
import ru.ifmo.ctddev.slyusarenko.sd.lab7.token.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Maxim Slyusarenko
 * @since 14.12.16
 */
public class ParserVisitor implements TokenVisitor {

    private List<Token> startList;
    private List<Token> resultList;
    private List<Token> stack;

    public ParserVisitor(List<Token> startList) {
        this.startList = startList;
        this.resultList = new ArrayList<>();
        this.stack = new ArrayList<>();
    }

    public List<Token> parse() {
        for (Token token : startList) {
            token.accept(this);
        }
        while (!stack.isEmpty()) {
            resultList.add(stack.get(stack.size() - 1));
            stack.remove(stack.size() - 1);
        }
        return resultList;
    }

    @Override
    public void visit(NumberToken token) {
        resultList.add(token);
    }

    @Override
    public void visit(Brace token) {
        if (token.getType() == BraceType.LEFT) {
            stack.add(token);
        } else {
            Token currentToken = stack.get(stack.size() - 1);
            while (!(currentToken instanceof Brace) || (((Brace) currentToken).getType()) != BraceType.LEFT) {
                resultList.add(currentToken);
                stack.remove(stack.size() - 1);
                currentToken = stack.get(stack.size() - 1);
            }
            stack.remove(stack.size() - 1);
        }
    }

    @Override
    public void visit(Operation token) {
        if (stack.size() == 0) {
            stack.add(token);
            return;
        }
        Token currentToken = stack.get(stack.size() - 1);
        while (currentToken instanceof  Operation && token.getType().getPriority() <= ((Operation) currentToken).getType().getPriority()) {
            resultList.add(currentToken);
            stack.remove(stack.size() - 1);
            if (stack.isEmpty()) {
                break;
            }
            currentToken = stack.get(stack.size() - 1);
        }
        stack.add(token);
    }
}

package ru.ifmo.ctddev.slyusarenko.sd.lab7.visitor;

import ru.ifmo.ctddev.slyusarenko.sd.lab7.token.Brace;
import ru.ifmo.ctddev.slyusarenko.sd.lab7.token.NumberToken;
import ru.ifmo.ctddev.slyusarenko.sd.lab7.token.Operation;
import ru.ifmo.ctddev.slyusarenko.sd.lab7.token.Token;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Maxim Slyusarenko
 * @since 14.12.16
 */
public class CalcVisitor implements TokenVisitor {

    private List<Token> source;
    private List<Integer> stack;

    public CalcVisitor(List<Token> source) {
        this.source = source;
        stack = new ArrayList<>();
    }

    public int evaluate() {
        for (Token token : source) {
            token.accept(this);
        }
        if (stack.size() != 1) {
            throw new IllegalStateException("It was wrong formula, can't evaluate");
        }
        return stack.get(0);
    }

    @Override
    public void visit(NumberToken token) {
        stack.add(token.getNumber());
    }

    @Override
    public void visit(Brace token) {
        throw new IllegalStateException("Can't be brace on this step of an algorithm");
    }

    @Override
    public void visit(Operation token) {
        if (stack.size() < 2) {
            throw new IllegalStateException("It was wrong formula, can't evaluate");
        }
        int first = stack.get(stack.size() - 1);
        stack.remove(stack.size() - 1);
        int second = stack.get(stack.size() - 1);
        stack.remove(stack.size() - 1);
        stack.add(token.getType().evaluate(second, first));
    }
}

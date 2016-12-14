package ru.ifmo.ctddev.slyusarenko.sd.lab7.visitor;

import ru.ifmo.ctddev.slyusarenko.sd.lab7.token.Brace;
import ru.ifmo.ctddev.slyusarenko.sd.lab7.token.NumberToken;
import ru.ifmo.ctddev.slyusarenko.sd.lab7.token.Operation;
import ru.ifmo.ctddev.slyusarenko.sd.lab7.token.Token;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.List;

/**
 * @author Maxim Slyusarenko
 * @since 14.12.16
 */
public class PrintVisitor implements TokenVisitor {

    private List<Token> source;
    private OutputStream target;

    public PrintVisitor(List<Token> source) {
        this.source = source;
        this.target = new BufferedOutputStream(System.out);
    }

    public OutputStream solve() {
        for (Token token : source) {
            token.accept(this);
        }
        return target;
    }

    @Override
    public void visit(NumberToken token) {
        writeToken(token);
    }

    @Override
    public void visit(Brace token) {
        writeToken(token);
    }

    @Override
    public void visit(Operation token) {
        writeToken(token);
    }

    private void writeToken(Token token) {
        try {
            target.write(token.toString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

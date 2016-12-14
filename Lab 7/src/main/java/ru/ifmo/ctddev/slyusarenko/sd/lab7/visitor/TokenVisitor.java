package ru.ifmo.ctddev.slyusarenko.sd.lab7.visitor;

import ru.ifmo.ctddev.slyusarenko.sd.lab7.token.Brace;
import ru.ifmo.ctddev.slyusarenko.sd.lab7.token.NumberToken;
import ru.ifmo.ctddev.slyusarenko.sd.lab7.token.Operation;

/**
 * @author Maxim Slyusarenko
 * @since 14.12.16
 */
public interface TokenVisitor {
    void visit(NumberToken token);
    void visit(Brace token);
    void visit(Operation token);
}

package ru.ifmo.ctddev.slyusarenko.sd.lab7.token;

import lombok.Getter;
import ru.ifmo.ctddev.slyusarenko.sd.lab7.visitor.TokenVisitor;

/**
 * @author Maxim Slyusarenko
 * @since 14.12.16
 */
@Getter
public class Operation implements Token {

    private OperationType type;

    public Operation(OperationType type) {
        this.type = type;
    }

    @Override
    public void accept(TokenVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return type.toString() + " ";
    }
}

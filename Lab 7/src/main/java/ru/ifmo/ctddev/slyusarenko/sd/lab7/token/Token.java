package ru.ifmo.ctddev.slyusarenko.sd.lab7.token;

import ru.ifmo.ctddev.slyusarenko.sd.lab7.visitor.TokenVisitor;

/**
 * @author Maxim Slyusarenko
 * @since 14.12.16
 */
public interface Token {
    void accept(TokenVisitor visitor);
}

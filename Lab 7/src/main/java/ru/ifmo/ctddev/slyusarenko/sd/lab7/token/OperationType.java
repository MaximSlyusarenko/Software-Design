package ru.ifmo.ctddev.slyusarenko.sd.lab7.token;

import java.util.function.BiFunction;

/**
 * @author Maxim Slyusarenko
 * @since 14.12.16
 */
public enum OperationType {
    PLUS(0, (a, b) -> a + b), MINUS(0, (a, b) -> a - b), MULTIPLY(1, (a, b) -> a * b), DIVIDE(1, (a, b) -> a / b);

    private int priority;
    private BiFunction<Integer, Integer, Integer> evaluationFunction;

    private OperationType(int priority, BiFunction<Integer, Integer, Integer> evaluationFunction) {
        this.priority = priority;
        this.evaluationFunction = evaluationFunction;
    }

    public int getPriority() {
        return priority;
    }

    public int evaluate(int a, int b) {
        return evaluationFunction.apply(a, b);
    }
}

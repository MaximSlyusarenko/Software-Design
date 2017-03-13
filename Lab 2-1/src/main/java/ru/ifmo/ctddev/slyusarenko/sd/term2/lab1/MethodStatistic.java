package ru.ifmo.ctddev.slyusarenko.sd.term2.lab1;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Maxim Slyusarenko
 * @since 13.03.17
 */
public class MethodStatistic {

    private final String methodName;
    private int invocationCount;
    private long methodSumTimes;
    private long methodAverageTime;
    private Map<String, MethodStatistic> childMethods;

    public MethodStatistic(String methodName) {
        this.methodName = methodName;
        this.invocationCount = 0;
        this.methodSumTimes = 0;
        this.methodAverageTime = 0;
        this.childMethods = new HashMap<>();
    }

    public void addMeasurement(MethodStatistic parent, long invocationTime) {
        if (parent != null) {
            parent.addChildMeasurement(methodName, invocationTime);
        }
        invocationCount++;
        methodSumTimes += invocationTime;
        methodAverageTime = methodSumTimes / invocationCount;
    }

    public void addChildMeasurement(String childName, long invocationTime) {
        childMethods.putIfAbsent(childName, new MethodStatistic(childName));
        MethodStatistic current = childMethods.get(childName);
        current.addMeasurement(null, invocationTime);
    }

    @Override
    public String toString() {
        return toString(0);
    }

    public String toString(int align) {
        StringBuilder result = new StringBuilder();
        result.append("**** ").append(methodName).append(":\n")
                .append(getSpaces(align)).append("  Invocations: ").append(invocationCount).append("\n")
                .append(getSpaces(align)).append("  Sum of times: ").append(methodSumTimes).append("\n")
                .append(getSpaces(align)).append("  Average time: ").append(methodAverageTime).append("\n");
        if (childMethods.size() > 0) {
            result.append(getSpaces(align)).append("  Methods called from current method: \n");
            for (MethodStatistic methodStatistic : childMethods.values()) {
                result.append(getSpaces(align + 1)).append(methodStatistic.toString(align + 1));
            }
        }
        return result.toString();
    }

    private String getSpaces(int align) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < align; i++) {
            result.append("  ");
        }
        if (align != 0) {
            result.append("  ");
        }
        return result.toString();
    }
}

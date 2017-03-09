package ru.ifmo.ctddev.slyusarenko.sd.term2.lab2;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * @author Maxim Slyusarenko
 * @since 09.03.17
 */
@AllArgsConstructor
@Getter
public class QueryResults {
    private List<QueryResult> queryResults;

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (QueryResult queryResult : queryResults) {
            result.append(queryResult.toString());
        }
        return result.toString();
    }
}

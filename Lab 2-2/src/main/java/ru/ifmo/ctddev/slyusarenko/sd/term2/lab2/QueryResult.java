package ru.ifmo.ctddev.slyusarenko.sd.term2.lab2;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author Maxim Slyusarenko
 * @since 09.03.17
 */
@AllArgsConstructor
@Getter
@Setter
public class QueryResult {
    private String searchSystem;
    private List<DocumentResult> results;

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("Results from search system ").append(searchSystem);
        int i = 1;
        for (DocumentResult s : results) {
            result.append(i).append(". ").append(s.getHeader()).append("\n").append(s.getUrl()).append("\n").append(s.getText()).append("\n");
        }
        return result.toString();
    }
}

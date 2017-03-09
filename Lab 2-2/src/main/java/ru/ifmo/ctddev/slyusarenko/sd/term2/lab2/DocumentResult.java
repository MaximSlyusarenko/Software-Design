package ru.ifmo.ctddev.slyusarenko.sd.term2.lab2;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Maxim Slyusarenko
 * @since 09.03.17
 */
@AllArgsConstructor
@Getter
@Setter
public class DocumentResult {
    private String url;
    private String header;
    private String text;
}

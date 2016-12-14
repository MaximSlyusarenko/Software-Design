package ru.ifmo.ctdddev.slyusarenko.sd.lab6.graph;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Maxim Slyusarenko
 * @since 14.12.16
 */
@Getter
@AllArgsConstructor
public class Edge {
    private Vertex from;
    private Vertex to;
}

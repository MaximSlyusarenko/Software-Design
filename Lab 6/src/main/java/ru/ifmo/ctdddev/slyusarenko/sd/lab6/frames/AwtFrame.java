package ru.ifmo.ctdddev.slyusarenko.sd.lab6.frames;

import ru.ifmo.ctdddev.slyusarenko.sd.lab6.drawing.AwtDrawing;
import ru.ifmo.ctdddev.slyusarenko.sd.lab6.graph.Edge;
import ru.ifmo.ctdddev.slyusarenko.sd.lab6.graph.EdgesListGraph;
import ru.ifmo.ctdddev.slyusarenko.sd.lab6.graph.Graph;
import ru.ifmo.ctdddev.slyusarenko.sd.lab6.graph.MatrixGraph;

import java.awt.*;
import java.awt.geom.Ellipse2D;

/**
 * @author Maxim Slyusarenko
 * @since 14.12.16
 */
public class AwtFrame extends Frame {

    private boolean[][] matrix;
    private java.util.List<Edge> edges;
    private long width;
    private long height;

    public AwtFrame(long width, long height, boolean[][] matrix) {
        this.width = width;
        this.height = height;
        this.matrix = matrix;
    }

    public AwtFrame(long width, long height, java.util.List<Edge> edges) {
        this.width = width;
        this.height = height;
        this.edges = edges;
    }

    @Override
    public void paint(Graphics graphics) {
        Graph graph;
        if (matrix != null) {
            graph = new MatrixGraph(new AwtDrawing(width, height, (Graphics2D) graphics), matrix);
        } else {
            graph = new EdgesListGraph(new AwtDrawing(width, height, (Graphics2D) graphics), edges);
        }
        graph.drawGraph();
    }
}

package ru.ifmo.ctdddev.slyusarenko.sd.lab6.graph;

import ru.ifmo.ctdddev.slyusarenko.sd.lab6.drawing.DrawingApi;

/**
 * @author Maxim Slyusarenko
 * @since 14.12.16
 */
public abstract class Graph {

    protected static final int VERTEX_RADIUS = 16;

    public Graph() {}

    protected DrawingApi drawingApi;

    public Graph(DrawingApi drawingApi) {
        this.drawingApi = drawingApi;
    }

    public abstract void drawGraph();
}

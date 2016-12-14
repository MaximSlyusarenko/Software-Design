package ru.ifmo.ctdddev.slyusarenko.sd.lab6.graph;

import ru.ifmo.ctdddev.slyusarenko.sd.lab6.common.Point;
import ru.ifmo.ctdddev.slyusarenko.sd.lab6.drawing.DrawingApi;

import java.util.*;

/**
 * @author Maxim Slyusarenko
 * @since 14.12.16
 */
public class EdgesListGraph extends Graph {

    private List<Edge> edges;
    private int vertexNumber;

    public EdgesListGraph(DrawingApi drawingApi, List<Edge> edges) {
        this.drawingApi = drawingApi;
        this.edges = edges;
        Set<Integer> vertexes = new HashSet<>();
        for (Edge edge : edges) {
            vertexes.add(edge.getFrom().getNumber());
            vertexes.add(edge.getTo().getNumber());
        }
        this.vertexNumber = vertexes.size();
    }

    @Override
    public void drawGraph() {
        Map<Integer, Point> vertexNumberToCoordinates = new HashMap<>();
        for (int i = 0; i < vertexNumber; i++) {
            Point point = new Point(((drawingApi.getDrawingAreaWidth() / 2 + (i == 0 ? 0 : i + 1) * ((drawingApi.getDrawingAreaWidth() - 2 * VERTEX_RADIUS) / vertexNumber)) % drawingApi.getDrawingAreaWidth()),
                    (drawingApi.getDrawingAreaHeight() / vertexNumber * i + 2 * VERTEX_RADIUS) % drawingApi.getDrawingAreaHeight());
            drawingApi.drawCircle(point, VERTEX_RADIUS);
            vertexNumberToCoordinates.put(i, point);
        }
        for (Edge edge : edges) {
            drawingApi.drawLine(vertexNumberToCoordinates.get(edge.getFrom().getNumber()),
                    vertexNumberToCoordinates.get(edge.getTo().getNumber()));
        }
    }
}

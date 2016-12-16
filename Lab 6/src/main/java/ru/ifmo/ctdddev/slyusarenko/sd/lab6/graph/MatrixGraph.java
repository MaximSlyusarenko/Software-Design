package ru.ifmo.ctdddev.slyusarenko.sd.lab6.graph;

import ru.ifmo.ctdddev.slyusarenko.sd.lab6.common.Point;
import ru.ifmo.ctdddev.slyusarenko.sd.lab6.drawing.DrawingApi;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Maxim Slyusarenko
 * @since 14.12.16
 */
public class MatrixGraph extends Graph {

    private boolean[][] matrix;
    private int vertexNumber;

    public MatrixGraph(DrawingApi drawingApi, boolean[][] matrix) {
        this.drawingApi = drawingApi;
        this.matrix = matrix;
        vertexNumber = matrix.length;
    }

    @Override
    public void drawGraph() {
        drawingApi.initializeFrame();
        Map<Integer, Point> vertexNumberToCoordinates = new HashMap<>();
        for (int i = 0; i < vertexNumber; i++) {
            Point point = new Point(((drawingApi.getDrawingAreaWidth() / 2 + (i == 0 ? 0 : i + 1) * ((drawingApi.getDrawingAreaWidth() - 2 * VERTEX_RADIUS) / vertexNumber)) % drawingApi.getDrawingAreaWidth()),
                    (drawingApi.getDrawingAreaHeight() / vertexNumber * i + 2 * VERTEX_RADIUS) % drawingApi.getDrawingAreaHeight());
            drawingApi.drawCircle(point, VERTEX_RADIUS);
            vertexNumberToCoordinates.put(i, point);
        }
        int i = 0;
        for (boolean[] isEdge : matrix) {
            for (int j = 0; j < vertexNumber; j++) {
                if (isEdge[j]) {
                    drawingApi.drawLine(vertexNumberToCoordinates.get(i), vertexNumberToCoordinates.get(j));
                }
            }
            i++;
        }
        drawingApi.startFrame();
    }
}

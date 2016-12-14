package ru.ifmo.ctdddev.slyusarenko.sd.lab6;

import ru.ifmo.ctdddev.slyusarenko.sd.lab6.drawing.AwtDrawing;
import ru.ifmo.ctdddev.slyusarenko.sd.lab6.drawing.DrawingApi;
import ru.ifmo.ctdddev.slyusarenko.sd.lab6.drawing.JavaFxDrawing;
import ru.ifmo.ctdddev.slyusarenko.sd.lab6.frames.AwtFrame;
import ru.ifmo.ctdddev.slyusarenko.sd.lab6.frames.JavaFxFrame;
import ru.ifmo.ctdddev.slyusarenko.sd.lab6.graph.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Maxim Slyusarenko
 * @since 14.12.16
 */
public class Main {

    private static final long WIDTH = 600;
    private static final long HEIGHT = 600;

    private static final boolean[][] MATRIX = new boolean[][]{{false, true, true, false}, {false, false, false, false},
            {false, true, false, true}, {false, false, false, false}};
    private static List<Edge> edges;

    private static void initEdges() {
        edges = new ArrayList<>();
        edges.add(new Edge(new Vertex(0), new Vertex(1)));
    }

    public static void main(String[] args) {
        if (args == null || args.length != 2 || args[0] == null || args[1] == null) {
            System.out.println("Usage: Main <draw mode> <graph mode>");
            return;
        }
        initEdges();
        if (args[0].equals("javafx")) {
            DrawingApi drawingApi;
            JavaFxFrame.init(WIDTH, HEIGHT);
            drawingApi = new JavaFxDrawing(WIDTH, HEIGHT, JavaFxFrame.getGraphicsContext());
            Graph graph;
            switch (args[1]) {
                case "matrix":
                    graph = new MatrixGraph(drawingApi, MATRIX);
                    break;
                case "edges":
                    graph = new EdgesListGraph(drawingApi, edges);
                    break;
                default:
                    System.out.println("args[1] should be matrix or edges");
                    return;
            }
            graph.drawGraph();
            JavaFxFrame.main(new String[]{});
        } else if (args[0].equals("awt")) {
            AwtFrame frame;
            switch (args[1]) {
                case "matrix":
                    frame = new AwtFrame(WIDTH, HEIGHT, MATRIX);
                    break;
                case "edges":
                    frame = new AwtFrame(WIDTH, HEIGHT, edges);
                    break;
                default:
                    System.out.println("args[1] should be matrix or edges");
                    return;
            }
            frame.setSize((int) WIDTH, (int) HEIGHT);
            frame.setVisible(true);
        } else {
            System.out.println("args[0] should be awt or javafx");
        }
    }
}

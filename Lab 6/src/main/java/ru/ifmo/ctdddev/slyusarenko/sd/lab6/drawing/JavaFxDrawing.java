package ru.ifmo.ctdddev.slyusarenko.sd.lab6.drawing;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import ru.ifmo.ctdddev.slyusarenko.sd.lab6.common.Point;

/**
 * @author Maxim Slyusarenko
 * @since 14.12.16
 */
public class JavaFxDrawing implements DrawingApi {

    private long drawingAreaWidth;
    private long drawingAreaHeight;
    private GraphicsContext graphicsContext;

    public JavaFxDrawing(long drawingAreaWidth, long drawingAreaHeight, GraphicsContext graphicsContext) {
        this.drawingAreaWidth = drawingAreaWidth;
        this.drawingAreaHeight = drawingAreaHeight;
        this.graphicsContext = graphicsContext;
    }

    @Override
    public long getDrawingAreaWidth() {
        return drawingAreaWidth;
    }

    @Override
    public long getDrawingAreaHeight() {
        return drawingAreaHeight;
    }

    @Override
    public void drawCircle(Point center, long radius) {
        graphicsContext.setFill(Color.BLUE);
        graphicsContext.fillOval(center.getX(), center.getY(), radius / 2, radius / 2);
    }

    @Override
    public void drawLine(Point start, Point finish) {
        graphicsContext.setFill(Color.BLUE);
        graphicsContext.strokeLine(start.getX(), start.getY(), finish.getX(), finish.getY());
    }
}

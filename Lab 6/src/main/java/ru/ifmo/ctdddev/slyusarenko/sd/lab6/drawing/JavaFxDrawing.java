package ru.ifmo.ctdddev.slyusarenko.sd.lab6.drawing;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import ru.ifmo.ctdddev.slyusarenko.sd.lab6.common.Point;
import ru.ifmo.ctdddev.slyusarenko.sd.lab6.frames.JavaFxFrame;

/**
 * @author Maxim Slyusarenko
 * @since 14.12.16
 */
public class JavaFxDrawing implements DrawingApi {

    private long drawingAreaWidth;
    private long drawingAreaHeight;
    private GraphicsContext graphicsContext;

    public JavaFxDrawing(long drawingAreaWidth, long drawingAreaHeight) {
        this.drawingAreaWidth = drawingAreaWidth;
        this.drawingAreaHeight = drawingAreaHeight;
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
        graphicsContext.fillOval(center.getX() - radius / 2, center.getY() - radius / 2, radius, radius);
    }

    @Override
    public void drawLine(Point start, Point finish) {
        graphicsContext.setFill(Color.BLUE);
        graphicsContext.strokeLine(start.getX(), start.getY(), finish.getX(), finish.getY());
    }

    @Override
    public void initializeFrame() {
        JavaFxFrame.init(drawingAreaWidth, drawingAreaHeight);
        graphicsContext = JavaFxFrame.getGraphicsContext();
    }

    @Override
    public void startFrame() {
        JavaFxFrame.main(new String[]{});
    }
}

package ru.ifmo.ctdddev.slyusarenko.sd.lab6.drawing;

import ru.ifmo.ctdddev.slyusarenko.sd.lab6.common.Point;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

/**
 * @author Maxim Slyusarenko
 * @since 14.12.16
 */
public class AwtDrawing implements DrawingApi {

    private long drawingAreaWidth;
    private long drawingAreaHeight;
    private Graphics2D graphics2D;

    public AwtDrawing(long drawingAreaWidth, long drawingAreaHeight, Graphics2D graphics2D) {
        this.drawingAreaWidth = drawingAreaWidth;
        this.drawingAreaHeight = drawingAreaHeight;
        this.graphics2D = graphics2D;
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
        graphics2D.setColor(Color.BLUE);
        graphics2D.fill(new Ellipse2D.Double(center.getX() - radius / 2, center.getY() - radius / 2, radius, radius));
    }

    @Override
    public void drawLine(Point start, Point finish) {
        graphics2D.setColor(Color.BLUE);
        graphics2D.drawLine((int) start.getX(), (int) start.getY(), (int) finish.getX(), (int) finish.getY());
    }
}

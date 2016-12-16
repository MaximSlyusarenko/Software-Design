package ru.ifmo.ctdddev.slyusarenko.sd.lab6.drawing;

import ru.ifmo.ctdddev.slyusarenko.sd.lab6.common.Point;

/**
 * @author Maxim Slyusarenko
 * @since 14.12.16
 */
public interface DrawingApi {

    long getDrawingAreaWidth();
    long getDrawingAreaHeight();
    void drawCircle(Point center, long radius);
    void drawLine(Point start, Point finish);
    void initializeFrame();
    void startFrame();
}

package ru.ifmo.ctdddev.slyusarenko.sd.lab6.frames;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;

/**
 * @author Maxim Slyusarenko
 * @since 14.12.16
 */
public class JavaFxFrame extends Application {

    private static long width;
    private static long height;
    private static Group root;

    public JavaFxFrame() {}

    public static void init(long width, long height) {
        JavaFxFrame.width = width;
        JavaFxFrame.height = height;
    }

    public static GraphicsContext getGraphicsContext() {
        root = new Group();
        Canvas canvas = new Canvas(width, height);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        root.getChildren().add(canvas);
        return gc;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Drawing graph");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

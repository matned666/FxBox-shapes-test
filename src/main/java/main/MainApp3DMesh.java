package main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainApp3DMesh extends Application {

    private static final double X_STAGE_MOVE = 1.5;

    @Override
    public void start(Stage primaryStage) throws Exception {
        RotatedNode3D controller = Mesh3DController.createMovableWithSize(primaryStage, 50);
        Scene scene = controller.getScene();
        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.show();
        primaryStage.setX(primaryStage.getX()* X_STAGE_MOVE);
        primaryStage.setAlwaysOnTop(true);
    }

}

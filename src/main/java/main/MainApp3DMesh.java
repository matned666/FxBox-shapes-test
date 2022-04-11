package main;

import fx3d.ResizableNode3D;
import fx3d.RotatingCube4Panel3DView;
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
        ResizableNode3D controller = RotatingCube4Panel3DView.createWithSize(primaryStage, 30);
        Scene scene = controller.getRoot().getScene();
        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.show();
        primaryStage.setX(primaryStage.getX()* X_STAGE_MOVE);
        primaryStage.setAlwaysOnTop(true);
    }

}

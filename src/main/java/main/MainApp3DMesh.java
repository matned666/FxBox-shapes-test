package main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainApp3DMesh extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        RotatedNode3D controller = Mesh3DController.createWithSize(50);
        Scene scene = controller.getScene();
        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.TRANSPARENT);

        primaryStage.show();
        primaryStage.setX(primaryStage.getX()*3/2);


        primaryStage.setAlwaysOnTop(true);
        controller.getMoveNode().setOnMouseDragged(event -> {
            double x = event.getScreenX();
            double y = event.getScreenY();
            primaryStage.setX(x - controller.getMoveNode().getLayoutX());
            primaryStage.setY(y - controller.getMoveNode().getLayoutY() - controller.getMoveNode().getTranslateY());
        });

    }

}

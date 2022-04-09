package main;

import javafx.application.Application;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainApp3DMesh extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        RotatedNode3D controller = Mesh3DController.create(new Mesh3DController(), 50);

        Pane root = controller.getRoot();
        Scene scene = new Scene(root);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        scene.setFill(Color.TRANSPARENT);
        primaryStage.setScene(scene);

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

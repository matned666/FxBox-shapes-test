package main.cube3d;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.cube2d.CustomButtonsController;

public class MainApp3D extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        Shape3dController controller = new Shape3dController(30);

        StackPane root = controller.getRoot();
        Scene scene = new Scene(root);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        scene.setFill(Color.TRANSPARENT);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setX(primaryStage.getX()+ primaryStage.getX()/2);

        primaryStage.setAlwaysOnTop(true);
        controller.getMoveNode().setOnMouseDragged(event -> {
            double x = event.getScreenX();
            double y = event.getScreenY();
            primaryStage.setX(x - controller.getMoveNode().getLayoutX());
            primaryStage.setY(y - controller.getMoveNode().getLayoutY() - controller.getMoveNode().getTranslateY());
        });

    }
}

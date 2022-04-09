package main.old.versions.cube2d;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainApp extends Application {

    @Override public void start(Stage primaryStage) throws Exception {

        CustomButtonsController controller = new CustomButtonsController(50, 50);

        StackPane root = controller.getRoot();
        Scene scene = new Scene(root);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        scene.setFill(Color.TRANSPARENT);
        primaryStage.setScene(scene);
        primaryStage.show();

        primaryStage.setAlwaysOnTop(true);
        controller.getRoot().setOnMouseDragged(event -> {
            double x = event.getScreenX() - event.getX()/1.5;
            double y = event.getScreenY() - event.getY()/1.5;
            primaryStage.setX(x);
            primaryStage.setY(y);
        });

    }
}

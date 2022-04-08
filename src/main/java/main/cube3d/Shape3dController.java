package main.cube3d;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point3D;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.PickResult;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Box;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;
import main.cube2d.CustomButtonsController;

import java.io.IOException;

public class Shape3dController {

    public static final int MAX_TRANSFORMERS_PER_BOX = 1000;
    public static final double SHADOW_SIZE_MODIFIER = 0.6;
    public static final double SHADOW_DISTANCE_Y_MODIFIER = 0.3;

    @FXML
    private Ellipse shadow;

    @FXML
    private Circle moveCircle;

    @FXML
    private Box box;

    private final StackPane root;

    private double boxX;

    private double size;

    private boolean dragged = false;

    public Shape3dController(double size) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(CustomButtonsController.class.getResource("/shape3d.fxml"));
        fxmlLoader.setControllerFactory(type -> this);
        root = fxmlLoader.load();
        resize(size);
        initListeners();
    }

    private void initListeners() {

        root.setOnMouseExited(event -> dragged = false);
        box.setOnMouseExited(event -> dragged = false);
        box.setOnMouseDragReleased(event -> dragged = false);
        box.setOnMouseDragExited(event -> dragged = false);
        box.setOnMousePressed(event -> boxX = event.getScreenX());
        box.setOnMouseDragged(event -> {
            double deltaX = (event.getScreenX() - boxX);
            if (box.getTransforms().size() > MAX_TRANSFORMERS_PER_BOX) {
                box.getTransforms().clear();
            }
            if (deltaX != 0) {
                Transform transform = new Rotate(-deltaX, new Point3D(0, 1, 0));
                box.getTransforms().add(transform);
            }
            boxX = event.getScreenX();
            dragged = true;
        });
        box.setOnMouseClicked(event -> {
            if(dragged) {
                dragged = false;
                return;
            }
            PickResult pr = event.getPickResult();
            Tools.Side side = Tools.Side.getByPoint(pr.getIntersectedPoint(), size);
            boxSideAction(event, side);
        });
        shadow.setOnMouseClicked(event -> boxSideAction(event, Tools.Side.BOTTOM));
    }

    private void boxSideAction(MouseEvent event, Tools.Side side) {
        event.consume();
        switch (side) {
        case TOP:
        case LEFT:
        case BOTTOM:
        case RIGHT:
        case BACK:
        case FRONT:
            //            TODO
            System.out.println(side);
        }
    }

    public void resize(double size) {
        this.size = size;
        box.setHeight(size);
        box.setWidth(size);
        box.setDepth(size);
        shadow.setRadiusX(size * SHADOW_SIZE_MODIFIER);
        shadow.setRadiusY(size * SHADOW_DISTANCE_Y_MODIFIER);
        shadow.setTranslateY(size);
    }

    public StackPane getRoot() {
        return root;
    }

    public Circle getMoveNode() {
        return moveCircle;
    }

    private static class Tools {

        private static boolean compareDouble(double x, double v) {
            double result = x - v;
            return Math.abs(result) < 0.1;
        }

        private enum Side {
            TOP, LEFT, RIGHT, FRONT, BACK, BOTTOM;

            private static Side getByPoint(Point3D point, double size) {
                Axis axis = Axis.getByPoint(point, size);
                switch (axis) {
                case X:
                    return Math.signum(point.getX()) > 0 ? RIGHT : LEFT;
                case Y:
                    return Math.signum(point.getY()) > 0 ? BOTTOM : TOP;
                default:
                    return Math.signum(point.getZ()) > 0 ? BACK : FRONT;
                }
            }

            private enum Axis {
                X, Y, Z;

                private static Axis getByPoint(Point3D point, double size) {
                    if (Tools.compareDouble(Math.abs(point.getX()), size / 2)) {
                        return X;
                    }
                    if (Tools.compareDouble(Math.abs(point.getY()), size / 2)) {
                        return Y;
                    }
                    return Z;
                }
            }

        }

    }

}

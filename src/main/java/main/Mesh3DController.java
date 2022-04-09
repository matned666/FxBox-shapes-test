package main;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point3D;
import javafx.scene.Node;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.PickResult;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;

import java.io.IOException;

public class Mesh3DController {

    public static final int MAX_TRANSFORMS_PER_BOX = 1000;
    public static final double SHADOW_SIZE_MODIFIER = 0.7;
    public static final double SHADOW_PLACE_MODIFIER = 0.8;
    public static final double SHADOW_DISTANCE_Y_MODIFIER = 0.3;

    private final StackPane root;

    @FXML
    public Ellipse shadow;

    @FXML
    private MeshView mesh;

    @FXML
    private Circle moveNode;

    float size;
    private double boxX;
    private TriangleMesh triangleMesh;
    private boolean dragged;


    public Mesh3DController(float size) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/mesh3d.fxml"));
        fxmlLoader.setControllerFactory(type -> this);
        root = fxmlLoader.load();
        this.size = size;
        initMesh();
        initListeners();
    }

    private void initMesh() {
        triangleMesh = new TriangleMesh();
        resize(size);
        mesh.setMesh(triangleMesh);
        PhongMaterial material = new PhongMaterial();
        material.setDiffuseMap(new Image("img/box.png"));
        mesh.setMaterial(material);
        resetToDefaultPos();
    }

    private void initListeners() {
        root.setOnMouseExited(event -> dragged = false);
        mesh.setOnMouseExited(event -> dragged = false);
        mesh.setOnMouseDragReleased(event -> dragged = false);
        mesh.setOnMouseDragExited(event -> dragged = false);
        mesh.setOnMousePressed(event -> boxX = event.getScreenX());
        mesh.setOnMousePressed(event -> boxX = event.getScreenX());
        mesh.setOnMouseDragged(event -> {
            double deltaX = (event.getScreenX() - boxX);
            if (mesh.getTransforms().size() > MAX_TRANSFORMS_PER_BOX) {
                resetToDefaultPos();
            }
            Transform transformX = new Rotate(deltaX, new Point3D(0, 1, 0));
            mesh.getTransforms().add(transformX);
            boxX = event.getScreenX();
            dragged = true;
        });
        mesh.setOnMouseClicked(event -> {
            if (dragged) {
                dragged = false;
                return;
            }
            Tools.Side side = getSide(event);
            boxSideAction(event, side);
        });
        shadow.setOnMouseClicked(event -> boxSideAction(event, Tools.Side.BOTTOM));
    }

    public void resize(float size) {
        this.size = size;
        resizeMesh();
        mesh.toFront();
        shadow.setRadiusX(size * SHADOW_SIZE_MODIFIER);
        shadow.setRadiusY(size * SHADOW_DISTANCE_Y_MODIFIER);
        shadow.setTranslateY(size * SHADOW_PLACE_MODIFIER);

    }

    public StackPane getRoot() {
        return root;
    }

    public Node getMoveNode() {
        return moveNode;
    }

    private Tools.Side getSide(MouseEvent event) {
        PickResult pr = event.getPickResult();
        return Tools.Side.getByPoint(pr.getIntersectedPoint(), size);
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

    private void resizeMesh() {
        triangleMesh.getPoints().clear();
        triangleMesh.getTexCoords().clear();
        triangleMesh.getFaces().clear();

        float[] points = getPoints();
        float[] coords = getCoords();
        int[] facets = getFacets();

        triangleMesh.getPoints().addAll(points);
        triangleMesh.getTexCoords().addAll(coords);
        triangleMesh.getFaces().addAll(facets);
        triangleMesh.getFaceSmoothingGroups().addAll(new int[facets.length / triangleMesh.getFaceElementSize()]);
    }

    private int[] getFacets() {
        return new int[]{0, 0, 1, 1, 2, 2,        // Top face1
                1, 1, 3, 3, 2, 2,                 // Top face2
                0, 12, 4, 4, 1, 13,               // Right face1
                1, 13, 4, 4, 5, 5,                // Right face2
                0, 8, 2, 2, 4, 10,                // Back face1
                2, 2, 6, 6, 4, 10,                // Back face2
                2, 2, 3, 3, 6, 6,                 // Left face1
                3, 3, 7, 7, 6, 6,                 // Left face2
                4, 4, 6, 6, 5, 5,                 // Bottom face1
                5, 5, 6, 6, 7, 7,                 // Bottom face2
                1, 9, 5, 11, 3, 3,                // Front face1
                3, 3, 5, 11, 7, 7};               // Front face1
    }

    private float[] getCoords() {
        return new float[]{
                0.00f, 0.33f,                       //0
                0.00f, 0.66f,                       //1
                0.25f, 0.33f,                       //2
                0.25f, 0.66f,                       //3
                0.75f, 0.33f,                       //4
                0.75f, 0.66f,                       //5
                0.50f, 0.33f,                       //6
                0.50f, 0.66f,                       //7
                0.20f, 0.00f,                       //8
                0.25f, 1.00f,                       //9
                0.50f, 0.00f,                       //10
                0.50f, 1.00f,                       //11
                1.00f, 0.33f,                       //12
                1.00f, 0.66f                        //13

        };
    }

    private float[] getPoints() {
        return new float[]{
                -size / 2, -size / 2, -size / 2,    //0
                -size / 2, -size / 2, size / 2,     //1
                size / 2, -size / 2, -size / 2,     //2
                size / 2, -size / 2, size / 2,      //3
                -size / 2, size / 2, -size / 2,     //4
                -size / 2, size / 2, size / 2,      //5
                size / 2, size / 2, -size / 2,      //6
                size / 2, size / 2, size / 2};      //7
    }

    private void resetToDefaultPos() {
        mesh.getTransforms().clear();
        mesh.getTransforms().add(new Rotate(-100, new Point3D(0, 1, 0)));
        mesh.getTransforms().add(new Rotate(30, new Point3D(0, 1, 1)));
        mesh.getTransforms().add(new Rotate(20, new Point3D(0, 1, 0)));
    }

    private static class Tools {

        private static boolean compareDouble(double x, double v) {
            double result = x - v;
            return Math.abs(result) < 0.1;
        }

        private enum Side {
            TOP, LEFT, RIGHT, FRONT, BACK, BOTTOM;

            private static Tools.Side getByPoint(Point3D point, double size) {
                Tools.Side.Axis axis = Tools.Side.Axis.getByPoint(point, size);
                switch (axis) {
                    case X:
                        return Math.signum(point.getX()) > 0 ? FRONT : BACK;
                    case Y:
                        return Math.signum(point.getY()) > 0 ? BOTTOM : TOP;
                    default:
                        return Math.signum(point.getZ()) > 0 ? LEFT : RIGHT;
                }
            }

            private enum Axis {
                X, Y, Z;

                private static Tools.Side.Axis getByPoint(Point3D point, double size) {
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

package fx3d;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Point3D;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.PickResult;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.MeshView;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;
import fx3d.mesh.Cube;
import fx3d.mesh.Mesh3D;
import fx3d.mesh.Shadow;
import javafx.stage.Stage;

import java.io.IOException;

/**
 *  Simple rotating 3D cube
 */
public class RotatingCube4Panel3DView implements ResizableNode3D {

    private static final int MAX_TRANSFORMS_PER_BOX = 500;

    public static ResizableNode3D createWithSize(Stage stage, float size) throws IOException {
        RotatingCube4Panel3DView cube = new RotatingCube4Panel3DView(size);
        cube.moveNode.setOnMouseDragged(event -> {
            double x = event.getScreenX();
            double y = event.getScreenY();
            stage.setX(x - cube.moveNode.getLayoutX());
            stage.setY(y - cube.moveNode.getLayoutY() - cube.moveNode.getTranslateY());
        });
        return cube;
    }


    private final StackPane root;

    @FXML
    private Circle moveNode;

    @FXML
    private MeshView shadowMesh;

    @FXML
    private MeshView boxMesh;

    private final Mesh3D boxFaces;
    private final Mesh3D shadowFaces;

    private float size;
    private double boxX;
    private boolean dragged;

    private RotatingCube4Panel3DView(float size) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/rotatingCube4Panel3DView.fxml"));
        fxmlLoader.setControllerFactory(type -> this);
        root = fxmlLoader.load();
        this.size = size;
        shadowFaces = Shadow.create(shadowMesh, size);
        boxFaces = Cube.create(boxMesh, size);
        root.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, null, null)));
        initMesh();
        initListeners();
    }

    @Override
    public void resize(float size) {
        this.size = size;
        root.setPrefHeight(size*10);
        boxFaces.resize(size);
        shadowFaces.resize(size);
        shadowMesh.toFront();
        boxMesh.toFront();
    }

    @Override
    public StackPane getRoot() {
        return root;
    }

    private void initMesh() {
        resize(size);
        resetToDefaultPos(boxMesh);
        resetToDefaultPos(shadowMesh);
    }

    private void initListeners() {
        root.setOnMouseExited(event -> dragged = false);
        initMeshListeners(boxMesh);
        initMeshListeners(shadowMesh);
        boxMesh.setOnMouseClicked(event -> {
            if (dragged) {
                dragged = false;
                return;
            }
            Side3D side = getSide(event);
            boxSideAction(event, side);
        });
        shadowMesh.setOnMouseClicked(event -> boxSideAction(event, Side3D.BOTTOM));
    }

    private void initMeshListeners(MeshView shadow) {
        shadow.setOnMouseExited(event -> dragged = false);
        shadow.setOnMouseDragReleased(event -> dragged = false);
        shadow.setOnMouseDragExited(event -> dragged = false);
        shadow.setOnMousePressed(event -> boxX = event.getScreenX());
        shadow.setOnMouseDragged(this::onMouseDrag);
    }

    private void onMouseDrag(MouseEvent event) {
        event.consume();
        double deltaX = (event.getScreenX() - boxX);
        if (boxMesh.getTransforms().size() > MAX_TRANSFORMS_PER_BOX) {
            resetToDefaultPos(boxMesh);
            resetToDefaultPos(shadowMesh);
        }
        Transform transform = new Rotate(deltaX, new Point3D(0, 1, 0));
        boxMesh.getTransforms().add(transform);
        shadowMesh.getTransforms().add(transform);
        boxX = event.getScreenX();
        dragged = true;
    }

    private void boxSideAction(MouseEvent event, Side3D side) {
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

    private Side3D getSide(MouseEvent event) {
        PickResult pr = event.getPickResult();
        return Side3D.getByPoint(pr.getIntersectedPoint(), size);
    }

    private void resetToDefaultPos(MeshView mesh) {
        mesh.getTransforms().clear();
        mesh.getTransforms().add(new Rotate(-100, new Point3D(0, 1, 0)));
        mesh.getTransforms().add(new Rotate(30, new Point3D(0, 1, 1)));
        mesh.getTransforms().add(new Rotate(20, new Point3D(0, 1, 0)));
    }
}

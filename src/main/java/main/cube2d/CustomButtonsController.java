package main.cube2d;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Path;
import javafx.scene.shape.Polygon;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CustomButtonsController implements Initializable {


    @FXML
    private Polygon bottomButton;

    @FXML
    private Polygon otherSideButton;

    @FXML
    private Polygon backButton;

    @FXML
    private Pane rootStage;

    @FXML
    private ImageView cube;

    @FXML
    private Path topButton;

    @FXML
    private Path sideButton;

    @FXML
    private Path frontButton;

    private final StackPane root;

    public CustomButtonsController(double width, double height) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(CustomButtonsController.class.getResource("/customButtons.fxml"));
        fxmlLoader.setControllerFactory(type -> this);
        root = fxmlLoader.load();
        initListeners();
        resize(width, height);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    private void initListeners() {
        initHoverAction(topButton);
        initHoverAction(sideButton);
        initHoverAction(frontButton);
        initHoverAction(backButton);
        initHoverAction(bottomButton);
        initHoverAction(otherSideButton);
        initPressAction(topButton);
        initPressAction(sideButton);
        initPressAction(frontButton);
        initPressAction(backButton);
        initPressAction(bottomButton);
        initPressAction(otherSideButton);
        initReleaseAction(topButton, ButtonType.TOP);
        initReleaseAction(sideButton, ButtonType.LEFT);
        initReleaseAction(frontButton, ButtonType.FRONT);
        initReleaseAction(backButton, ButtonType.BACK);
        initReleaseAction(bottomButton, ButtonType.BOTTOM);
        initReleaseAction(otherSideButton, ButtonType.RIGHT);
    }

    private void initPressAction(Node node) {
        node.setOnMousePressed(event -> lighten(0.5, node, node.isHover()));
    }

    private void initReleaseAction(Node node, ButtonType buttonType) {
        node.setOnMouseReleased(event -> {
            if (node.isHover()) {
                nodeAction(buttonType);
            }
            lighten(0.3, node, node.isHover());
        });
    }

    private void initHoverAction(Node node) {
        node.hoverProperty().addListener((a, b, c) -> lighten(0.3, node, c));
    }

    private void nodeAction(ButtonType buttonType) {
        System.out.println(buttonType);
    }

    private void lighten(double value, Node node, Boolean c) {
        if (c) {
            ColorAdjust colorAdjust = new ColorAdjust();
            colorAdjust.setBrightness(value);
            node.setEffect(colorAdjust);
        } else {
            node.setEffect(null);
        }
    }

    public void resize(double width, double height) {
        CustomButtonControllerTools.resizePolygon(backButton, width, height, root.getBoundsInParent().getWidth(), root.getBoundsInParent().getHeight());
        CustomButtonControllerTools.resizePolygon(bottomButton, width, height, root.getBoundsInParent().getWidth(), root.getBoundsInParent().getHeight());
        CustomButtonControllerTools.resizePolygon(otherSideButton, width, height, root.getBoundsInParent().getWidth(), root.getBoundsInParent().getHeight());
        CustomButtonControllerTools.resizePath(topButton, width, height, root.getBoundsInParent().getWidth(), root.getBoundsInParent().getHeight());
        CustomButtonControllerTools.resizePath(sideButton, width, height, root.getBoundsInParent().getWidth(), root.getBoundsInParent().getHeight());
        CustomButtonControllerTools.resizePath(frontButton, width, height, root.getBoundsInParent().getWidth(), root.getBoundsInParent().getHeight());
//        cube.setFitWidth(width);
//        cube.setFitHeight(height);
        root.setPrefWidth(width);
        root.setPrefHeight(height);
    }

    public StackPane getRoot() {
        return root;
    }

    private enum ButtonType {
        TOP, LEFT, RIGHT , FRONT, BACK, BOTTOM
    }
}

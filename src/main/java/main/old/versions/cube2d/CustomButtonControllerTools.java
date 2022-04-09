package main.old.versions.cube2d;

import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Polygon;

import java.util.LinkedList;
import java.util.List;

public class CustomButtonControllerTools {

    static void resizePath(Path path, double width, double height, double actualRootWidth, double actualRootHeight) {
        path.setLayoutX(sizeValueTranslate(path.getLayoutX(), width, actualRootWidth));
        path.setLayoutY(sizeValueTranslate(path.getLayoutY(), height, actualRootHeight));
        path.getElements().forEach(pathElement -> {
            if (pathElement instanceof MoveTo) {
                ((MoveTo) pathElement).setX(sizeValueTranslate(((MoveTo) pathElement).getX(), width, actualRootWidth));
                ((MoveTo) pathElement).setY(
                        sizeValueTranslate(((MoveTo) pathElement).getY(), height, actualRootHeight));
            } else if (pathElement instanceof LineTo) {
                ((LineTo) pathElement).setX(sizeValueTranslate(((LineTo) pathElement).getX(), width, actualRootWidth));
                ((LineTo) pathElement).setY(
                        sizeValueTranslate(((LineTo) pathElement).getY(), height, actualRootHeight));
            }
        });
    }

    static void resizePolygon(Polygon polygon, double width, double height, double actualRootWidth,
            double actualRootHeight) {
        polygon.setLayoutX(sizeValueTranslate(polygon.getLayoutX(), width, actualRootWidth));
        polygon.setLayoutY(sizeValueTranslate(polygon.getLayoutY(), height, actualRootHeight));
        List<Double> result = new LinkedList<>();
        List<Double> points = new LinkedList<>(polygon.getPoints());
        for (int i = 0; i < points.size(); i++) {
            if (i % 2 != 0){
                result.add(sizeValueTranslate(points.get(i), width, actualRootWidth));
            } else {
                result.add(sizeValueTranslate(points.get(i), height, actualRootHeight));
            }
        }
        polygon.getPoints().setAll(result);
    }

    private static double sizeValueTranslate(double oldValue, double value, double rootValue) {
        return oldValue / rootValue * value;
    }
}

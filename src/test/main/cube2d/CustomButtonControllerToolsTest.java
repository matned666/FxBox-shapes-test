package main.cube2d;

import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(JUnitParamsRunner.class) public class CustomButtonControllerToolsTest {

    private Path path;

    @Before public void setUp() {
    }

    @Test
    @Parameters({
            //ax   ay   bx    by   newW  newH  oldW   oldH   eax  eay  ebx   eby
            "0.0, 0.0, 50.0, 50.0, 50.0, 50.0, 100.0, 100.0, 0.0, 0.0, 25.0, 25.0",
            "0.0, -5.0, -50.0, -50.0, 50.0, 50.0, 100.0, 100.0, 0.0, -2.5, -25.0, -25.0",
            "0.0, 5.0, 50.0, 50.0, 50.0, 50.0, 100.0, 100.0, 0.0, 2.5, 25.0, 25.0",
            "0.0, 0.0, 50.0, 50.0, 100.0, 50.0, 100.0, 100.0,0.0, 0.0,  50.0, 25.0" })
    public void Should_PathElementsValuesEqualExpectationParams(
            double ax, double ay, double bx, double by, double newSizeX, double newSizeY, double oldSizeX,
            double oldSizeY, double expectedMoveToX, double expectedMoveToY, double expectedLineToX,
            double expectedLineToY) {

        //when
        path = new Path();
        path.getElements().add(new MoveTo(ax, ay));
        path.getElements().add(new LineTo(bx, by));

        //given
        CustomButtonControllerTools.resizePath(path, newSizeX, newSizeY, oldSizeX, oldSizeY);

        //then
        MoveTo moveTo = (MoveTo) path.getElements().get(0);
        LineTo lineTo = (LineTo) path.getElements().get(1);
        assertEquals("MoveTo X:", expectedMoveToX, moveTo.getX(), 0.1);
        assertEquals("MoveTo Y:", expectedMoveToY, moveTo.getY(), 0.1);
        assertEquals("LineTo X:", expectedLineToX, lineTo.getX(), 0.1);
        assertEquals("LineTo Y:", expectedLineToY, lineTo.getY(), 0.1);
    }

}
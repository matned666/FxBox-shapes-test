package fx3d;

import javafx.geometry.Point3D;

public enum Side3D {

    TOP, LEFT, RIGHT, FRONT, BACK, BOTTOM;

    static Side3D getByPoint(Point3D point, double size) {
        Axis axis = Axis.getByPoint(point, size);
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

        private static Axis getByPoint(Point3D point, double size) {
            if (compareDouble(Math.abs(point.getX()), size / 2)) {
                return X;
            }
            if (compareDouble(Math.abs(point.getY()), size / 2)) {
                return Y;
            }
            return Z;
        }
    }

    private static boolean compareDouble(double x, double v) {
        double result = x - v;
        return Math.abs(result) < 0.1;
    }
}

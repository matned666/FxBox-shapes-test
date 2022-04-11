package fx3d.mesh;

import javafx.scene.shape.MeshView;

import java.net.URISyntaxException;

public class Cube extends BaseMesh3D {

    public static Mesh3D create(MeshView mesh, float size) {
        try {
            return new Cube(mesh, size);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Cube(MeshView mesh, float size) throws URISyntaxException {
        super(mesh, size, "img/box.png");
    }

    @Override
    protected float[] getPoints() {
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

    @Override
    protected float[] getCoords() {
        return new float[]{
                0.00f, 0.33f,                       //0
                0.00f, 0.66f,                       //1
                0.25f, 0.33f,                       //2
                0.25f, 0.66f,                       //3
                0.75f, 0.33f,                       //4
                0.75f, 0.66f,                       //5
                0.50f, 0.33f,                       //6
                0.50f, 0.66f,                       //7
                0.25f, 0.00f,                       //8
                0.25f, 1.00f,                       //9
                0.50f, 0.00f,                       //10
                0.50f, 1.00f,                       //11
                1.00f, 0.33f,                       //12
                1.00f, 0.66f                        //13
        };
    }

    @Override
    protected int[] getFacets() {
        return new int[]{
                0, 0, 1, 1, 2, 2,                 // Top face1
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

}

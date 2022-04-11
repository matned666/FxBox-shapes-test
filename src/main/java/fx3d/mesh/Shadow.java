package fx3d.mesh;

import javafx.scene.shape.MeshView;

import java.net.URISyntaxException;

public class Shadow extends BaseMesh3D {

    public static Mesh3D create(MeshView mesh, float size) {
        try {
            return new Shadow(mesh, size);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Shadow(MeshView mesh, float size) throws URISyntaxException {
        super(mesh, size, "img/box.png");
    }

    @Override
    protected float[] getPoints() {
        return new float[] {
                -size / 2, size * 2, -size / 2, //0
                -size / 2, size * 2, size / 2,  //1
                size / 2, size * 2, -size / 2,  //2
                size / 2, size * 2, size / 2    //3
        };
    }

    @Override
    protected float[] getCoords() {
        return new float[] { 0.75f, 0.33f,  //0
                0.75f, 0.66f,               //1
                0.50f, 0.33f,               //2
                0.50f, 0.66f,               //3
                0.75f, 0.33f,               //4
                0.75f, 0.66f,               //5
                0.50f, 0.33f,               //6
                0.50f, 0.66f                //7
        };
    }

    @Override
    protected int[] getFacets() {
        return new int[] {
                0, 4, 1, 5, 2, 6,           // Top face1
                1, 5, 3, 7, 2, 6            // Top face2
        };
    }
}

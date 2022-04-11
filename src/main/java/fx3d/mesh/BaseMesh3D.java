package fx3d.mesh;

import javafx.scene.image.Image;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;

import java.net.URISyntaxException;

public abstract class BaseMesh3D implements Mesh3D {

    protected final TriangleMesh triangleMesh;
    protected float size;

    protected BaseMesh3D(MeshView mesh, float size, String imgUrl) throws URISyntaxException {
        triangleMesh = new TriangleMesh();
        mesh.setMesh(triangleMesh);
        PhongMaterial material = new PhongMaterial();
        material.setDiffuseMap(new Image(imgUrl));
        mesh.setMaterial(material);
        resize(size);
    }

    @Override
    public void resize(float size) {
        this.size = size;
        triangleMesh.getPoints().clear();
        triangleMesh.getTexCoords().clear();
        triangleMesh.getFaces().clear();
        triangleMesh.getPoints().setAll(getPoints());
        triangleMesh.getTexCoords().setAll(getCoords());
        triangleMesh.getFaces().setAll(getFacets());
        triangleMesh.getFaceSmoothingGroups()
                .setAll(new int[triangleMesh.getFaces().size() / triangleMesh.getFaceElementSize()]);
    }

    /**
     * A float array of mesh points x, y and z.
     * @return float[]
     */
    protected float[] getPoints() {
        return new float[0];
    }

    /**
     * An array of floats representing coordinates v(vertical) and h(horizontal) ratio on a texture map.
     * The coordinates go to each facet point
     * - so if there were alternative facets added, they should appear here as well.
     *
     * @return float[]
     */
    protected float[] getCoords() {
        return new float[0];
    }

    /**
     * An array of integers where each 6 numbers is an index
     * of a triangleMesh point.
     * each second index represents an alternative face
     * for textures connection
     *
     * @return int[]
     */
    protected int[] getFacets() {
        return new int[0];
    }

}

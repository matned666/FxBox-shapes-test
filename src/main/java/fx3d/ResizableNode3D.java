package fx3d;

import javafx.scene.layout.StackPane;

public interface ResizableNode3D {

    /**
     * Changes base size of the rotated object
     * @param size in px
     */
    void resize(float size);

    /**
     * Gets a root pane of the object
     * @return background Pane
     */
    StackPane getRoot();

}

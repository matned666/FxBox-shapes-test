package main;

import javafx.scene.Node;
import javafx.scene.Scene;

public interface RotatedNode3D {

    /**
     * Changes base size of the rotated object
     * @param size in px
     */
    void resize(float size);

    /**
     * Gets a root pane of the object
     * @return background Pane
     */
    Scene getScene();

    /**
     * Gets a node by which you can move the whole object
     * @return Node
     */
    Node getMoveNode();


}

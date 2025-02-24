package inf112.skeleton.model.gameobject;

import com.badlogic.gdx.graphics.Texture;

public interface ViewableObject {

    /**
     * Returns the objects position and size
     *
     * @return Translate of Position and Size
     */
    Transform getTransform();

    /**
     * Returns the Texture of the object
     *
     * @return Texture of an image
     */
    Texture getTexture();
}

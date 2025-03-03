package inf112.skeleton.model.gameobject;

import com.badlogic.gdx.graphics.Texture;

// TODO: comment me :)
public interface ViewableObject {

    /**
     * Returns the objects position and size
     *
     * @return Transform of Position and Size
     */
    Transform getTransform();

    /**
     * Returns the Texture of the object
     *
     * @return Texture of an image
     */
    Texture getTexture();

    /**
     * Returns the Texture and Transform combined in
     * a ObjectProperties record
     */
    //ObjectProperties getObjectProperties();

    CollisionBox getCollisionBox();

    void setCollisionBox(Transform transform);
}

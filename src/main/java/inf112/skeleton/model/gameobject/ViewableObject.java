package inf112.skeleton.model.gameobject;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

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
     * TODO: comment me
     * @return
     */
    CollisionBox getCollisionBox();
    TextureRegion getCurrentFrame();

    void update(float deltaTime);

    void dispose();
}

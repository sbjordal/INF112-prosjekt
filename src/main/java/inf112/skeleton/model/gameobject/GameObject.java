package inf112.skeleton.model.gameobject;

import com.badlogic.gdx.graphics.Texture;

/**
 * Represents every object that is visualized on the screen.
 * To visualize the object, both a {@link Transform} and a {@link Texture} is required.
 */
public class GameObject implements ViewableObject{
    private Transform transform;
    private Texture texture;

    /**
     * Constructor that creates a game object based on its position
     * and texture
     *
     * @param transform current position and size of object
     * @param texture Texture object
     */
    public GameObject(Transform transform, Texture texture){
        this.transform = transform;
        this.texture = texture;
    }

    @Override
    public Transform getTransform() {
        return transform;
    }

    @Override
    public Texture getTexture() {
        return texture;
    }
}

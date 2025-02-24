package inf112.skeleton.model.gameobject;
import com.badlogic.gdx.graphics.Texture;

/**
 * This class represents every object that appears on the play screen.
 *
 */
public class GameObject implements ViewableObject{
    protected Transform transform;
    protected Texture texture;

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

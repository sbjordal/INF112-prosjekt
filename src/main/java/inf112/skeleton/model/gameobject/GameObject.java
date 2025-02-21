package inf112.skeleton.model.gameobject;
import com.badlogic.gdx.graphics.Texture;

/**
 * This class represents every object that appears on the play screen.
 *
 */
public class GameObject {
    protected Position position;
    protected Texture sprite;

    /**
     * Constructor that creates a game object based on its position
     * and texture
     *
     * @param position current position of object
     * @param sprite Texture object
     */

    public GameObject(Position position, Texture sprite){
        this.position = position;
        this.sprite = sprite;

    }
}

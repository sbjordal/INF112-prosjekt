package inf112.skeleton.model.gameobject.fixedobject;
import com.badlogic.gdx.graphics.Texture;
import inf112.skeleton.model.gameobject.GameObject;
import inf112.skeleton.model.gameobject.Position;

/**
 * Class that represent all game objects that dont move.
 */

public class FixedObject extends GameObject {
    protected Position position;
    protected Texture sprite;

    public FixedObject(Position position, Texture sprite) {
        super(position, sprite);

    }
}

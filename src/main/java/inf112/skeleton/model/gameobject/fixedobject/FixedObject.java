package inf112.skeleton.model.gameobject.fixedobject;
import com.badlogic.gdx.graphics.Texture;
import inf112.skeleton.model.gameobject.GameObject;
import inf112.skeleton.model.gameobject.Position;
import inf112.skeleton.model.gameobject.Transform;

/**
 * Class that represent all game objects that don't move.
 */

public class FixedObject extends GameObject {
    public FixedObject(Transform transform, Texture texture) {
        super(transform, texture);

    }
}

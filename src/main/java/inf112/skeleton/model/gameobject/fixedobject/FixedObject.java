package inf112.skeleton.model.gameobject.fixedobject;

import com.badlogic.gdx.graphics.Texture;
import inf112.skeleton.model.gameobject.GameObject;
import inf112.skeleton.model.gameobject.Transform;

/**
 * Represents all fixed object types.
 * A fixed object is any {@link GameObject} that has a fixed position.
 * A fixed position can not be altered once instantiated.
 */
public class FixedObject extends GameObject {

    /**
     * Creates a new FixedObject with the specified transform and texture.
     *
     * @param transform The rate of which the FixedObject moves horizontally.
     * @param texture   The visual representation of the FixedObject.
     */
    public FixedObject(Transform transform, Texture texture) {
        super(transform, texture);
    }
}

package inf112.skeleton.model.gameobject.fixedobject;

import inf112.skeleton.model.gameobject.GameObject;
import inf112.skeleton.model.gameobject.Transform;

/**
 * Represents all fixed object types.
 * A fixed object is any {@link GameObject} that has a fixed position.
 * A fixed position can not be altered once instantiated.
 */
public class FixedObject extends GameObject { // TODO: fjerne denne klassen siden den ikke har noe unik funksjon?

    /**
     * Creates a new FixedObject with the specified transform and texture.
     *
     * @param transform The fixed position and size of the FixedObject.
     */
    public FixedObject(Transform transform) {
        super(transform);
    }
}

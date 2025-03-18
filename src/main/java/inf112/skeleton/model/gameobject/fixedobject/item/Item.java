package inf112.skeleton.model.gameobject.fixedobject.item;

import inf112.skeleton.model.gameobject.Transform;
import inf112.skeleton.model.gameobject.fixedobject.FixedObject;
import inf112.skeleton.model.gameobject.mobileobject.actor.Player;

/**
 * Represents every fixed object that can be picked up by {@link Player}.
 */
public class Item extends FixedObject {// TODO: fjerne denne klassen siden den ikke har noe unik funksjon?

    /**
     * Creates a new Item with the specified transform and texture.
     *
     * @param transform The fixed position and size of the Item.
     */
    public Item(Transform transform) {
        super(transform);
    }

    public void pickUp() {
        // TODO: remove this method. Logic is handled in WorldModel.
        //       Java doesn't support passing by reference for primitives.
        //       So updating 'coinCounter' and 'totalScore' would be cumbersome.
    }
}

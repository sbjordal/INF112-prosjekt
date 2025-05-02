package inf112.starhunt.model.gameobject.fixedobject.item;

import inf112.starhunt.model.gameobject.Transform;
import inf112.starhunt.model.gameobject.fixedobject.FixedObject;
import inf112.starhunt.model.gameobject.mobileobject.actor.Player;

/**
 * Represents every fixed object that can be picked up by {@link Player}.
 */
public class Item extends FixedObject {
    private boolean isCollected;

    /**
     * Creates a new Item with the specified transform.
     *
     * @param transform The fixed position and size of the Item.
     */
    public Item(Transform transform) {
        super(transform);
        isCollected = false;
    }

    /**
     * Marks the item as collected.
     * <p>
     * This can be used by the game logic when the player picks up the item.
     */
    public void collect() {
        isCollected = true;
    }

    /**
     * Checks whether the item has been collected.
     *
     * @return {@code true} if the item has been collected; {@code false} otherwise.
     */
    public boolean isCollected() {
        return isCollected;
    }

}

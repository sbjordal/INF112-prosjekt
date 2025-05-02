package inf112.starhunt.model.gameobject.fixedobject.item;

import inf112.starhunt.model.gameobject.Collidable;
import inf112.starhunt.model.gameobject.Transform;
import inf112.starhunt.model.gameobject.Visitor;

/**
 * {@link Item} that represents a Star, something a {@link inf112.starhunt.model.gameobject.mobileobject.actor.Player} can collect to continue to the next level.
 */
public class Star extends Item implements Collidable {

    /**
     * Creates a new Star with the specified transform.
     *
     * @param transform The fixed position and size of the Item.
     */
    public Star(Transform transform) {
        super(transform);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visitStar(this);
    }
}

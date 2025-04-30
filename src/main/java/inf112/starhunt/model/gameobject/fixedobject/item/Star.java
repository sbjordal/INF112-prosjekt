package inf112.starhunt.model.gameobject.fixedobject.item;

import inf112.starhunt.model.gameobject.Collidable;
import inf112.starhunt.model.gameobject.Transform;
import inf112.starhunt.model.gameobject.Visitor;

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

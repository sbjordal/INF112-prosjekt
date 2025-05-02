package inf112.starhunt.model.gameobject;

/**
 * All classes that are Collidable is possible to collide into.
 */
public interface Collidable extends ViewableObject {

    /**
     * Accepts a visitor and allows it to perform an operation on this object.
     *
     * @param visitor the visitor performing an operation on this object
     */

    void accept(Visitor visitor);

    /**
     * A getter for CollisionBox. CollisionBox is the bounding box of an object, used to
     * detect collisions.
     * @return the collisionBox of the GameObject
     */
    CollisionBox getCollisionBox();

}

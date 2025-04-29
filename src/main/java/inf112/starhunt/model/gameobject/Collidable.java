package inf112.starhunt.model.gameobject;

/**
 * All classes that are Collidable is able to collide into.
 */
public interface Collidable extends ViewableObject {

    /**
     *
     * @param visitor
     */
    void accept(Visitor visitor);

    /**
     * A getter for CollisionBox. CollisionBox is the bounding box of an object, used to
     * detect collisions.
     * @return the collisionBox of the GameObject
     */
    CollisionBox getCollisionBox();
}

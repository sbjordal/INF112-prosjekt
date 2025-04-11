package inf112.skeleton.model.gameobject;

public interface Collidable extends ViewableObject {

    /**
     * TODO
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

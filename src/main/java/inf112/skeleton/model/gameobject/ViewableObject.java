package inf112.skeleton.model.gameobject;

// TODO: comment me :)
public interface ViewableObject {

    /**
     * Returns the objects position and size
     *
     * @return Transform of Position and Size
     */
    Transform getTransform();

    /**
     * TODO: comment me
     * @return
     */
    CollisionBox getCollisionBox();

}

package inf112.skeleton.model.gameobject;

import com.badlogic.gdx.math.Rectangle;

// TODO: comment me :)
public interface ViewableObject {

    /**
     * Returns the objects position and size
     *
     * @return Transform of Position and Size
     */
    Transform getTransform();

//    /**
//     * TODO: comment/delete me
//     * @return
//     */
//    CollisionBox getCollisionBox();


    /**
     * TODO: comment me
     * @return
     */
    Rectangle getRectangle();

}

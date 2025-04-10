package inf112.skeleton.model.gameobject;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

// TODO: comment me :)
public interface ViewableObject {

    /**
     * Returns the objects position and size
     *
     * @return Transform of Position and Size
     */
    Transform getTransform();

    /**
     * TODO: comment/delete me
     * @return
     */
    CollisionBox getCollisionBox();


//    /**
//     * TODO: comment me
//     * @return
//     */
//    Rectangle getRectangle();
//
//    /**
//     * TODO: comment me
//     * @param newPos
//     * @return
//     */
//    void setRectanglePos(Vector2 newPos);

}

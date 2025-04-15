package inf112.skeleton.model.gameobject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Represents every object that is visualized on the screen.
 * To visualize the object a {@link Transform} is required.
 */
public class GameObject implements ViewableObject{
    private Transform transform;
    private CollisionBox collisionBox;
//    private Rectangle rectangle;

    /**
     * Creates a new GameObject with the specified transform and texture.
     *
     * @param transform The initial position and size of the GameObject.
     */
    public GameObject(Transform transform){
        this.transform = transform;
//        this.rectangle = new Rectangle(transform.getPos().x,transform.getPos().y, transform.getSize().x, transform.getSize().y);
        this.collisionBox = new CollisionBox(transform);
    }

    @Override
    public Transform getTransform() {
        return transform;
    }

    @Override
    public CollisionBox getCollisionBox() {
        return collisionBox;
    }

    protected void setCollisionBox(Transform transform) {
        collisionBox = new CollisionBox(transform);
    }

//    @Override
//    public Rectangle getRectangle(){
//        return rectangle;
//    }

//    @Override
//    public void setRectanglePos(Vector2 newPos) {
//        rectangle.setPosition(newPos);
//    }

    public void setSize(Vector2 size) {
        transform.size = size;
    }
}

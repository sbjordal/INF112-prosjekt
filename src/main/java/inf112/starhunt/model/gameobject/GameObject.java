package inf112.starhunt.model.gameobject;
import com.badlogic.gdx.math.Vector2;


/**
 * Represents every object that is visualized on the screen.
 * To visualize the object a {@link Transform} is required.
 */
public class GameObject implements ViewableObject {
    private final Transform transform;
    private CollisionBox collisionBox;

    /**
     * Creates a new GameObject with the specified transform and texture.
     *
     * @param transform The initial position and size of the GameObject.
     */
    public GameObject(Transform transform){
        this.transform = transform;
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

    @Override
    public void setOnCoinCollected(Runnable callback) {}

    @Override
    public void setOnCollisionWithEnemy(Runnable callback) {}

    @Override
    public void setOnCollisionWithEnemyDealDamage(Runnable callback) {}

    @Override
    public void setOnBananaCollected(Runnable callback) {}

    @Override
    public int getDirection() {
        return 0;
    }

    protected void setCollisionBox(Transform transform) {
        collisionBox = new CollisionBox(transform);
    }

    public void setSize(Vector2 size) {
        transform.alterSize((int) size.x, (int) size.y);
    }
}

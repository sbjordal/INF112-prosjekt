package inf112.skeleton.model.gameobject;

import com.badlogic.gdx.graphics.Texture;

/**
 * Represents every object that is visualized on the screen.
 * To visualize the object, both a {@link Transform} and a {@link Texture} is required.
 */
public class GameObject implements ViewableObject{
    private Transform transform;
    private Texture texture;
    private CollisionBox collisionBox;

    /**
     * Creates a new GameObject with the specified transform and texture.
     *
     * @param transform The initial position and size of the GameObject.
     * @param texture   The visual representation of the GameObject.
     */
    public GameObject(Transform transform, Texture texture){
        this.transform = transform;
        this.texture = texture;
        this.collisionBox = new CollisionBox(transform);
    }

    @Override
    public Transform getTransform() {
        return transform;
    }

    @Override
    public Texture getTexture() {
        return texture;
    }

    @Override
    public CollisionBox getCollisionBox() {
        return collisionBox;
    }

    @Override
    public void setCollisionBox(Transform transform) {
        collisionBox = new CollisionBox(transform); // TODO: hvis vi ikke vil lage nye objekter hver gang, så endrer vi det senere.
    }
}

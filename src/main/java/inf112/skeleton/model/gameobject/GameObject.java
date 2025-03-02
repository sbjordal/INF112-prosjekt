package inf112.skeleton.model.gameobject;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;

/**
 * Represents every object that is visualized on the screen.
 * To visualize the object, both a {@link Transform} and a {@link Texture} is required.
 */
public class GameObject implements ViewableObject{
    private Transform transform;
    private Texture texture;

    /**
     * Creates a new GameObject with the specified transform and texture.
     *
     * @param transform The initial position and size of the GameObject.
     * @param texture   The visual representation of the GameObject.
     */
    public GameObject(Transform transform, Texture texture){
        this.transform = transform;
        this.texture = texture;
    }

    @Override
    public Transform getTransform() {
        return transform;
    }

    @Override
    public Texture getTexture() {
        TextureData textureData= texture.getTextureData();
        if (!textureData.isPrepared()) {
            textureData.prepare();
        }
        return new Texture(textureData);
    }
}

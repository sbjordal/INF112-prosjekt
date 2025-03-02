package inf112.skeleton.model.gameobject;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;

/**
 * This class represents every object that appears on the play screen.
 *
 */
public class GameObject implements ViewableObject{
    private Transform transform;
    private Texture texture;

    /**
     * Constructor that creates a game object based on its position
     * and texture
     *
     * @param transform current position and size of object
     * @param texture Texture object
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

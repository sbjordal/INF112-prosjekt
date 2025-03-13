package inf112.skeleton.model.gameobject.fixedobject.item;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import inf112.skeleton.model.gameobject.Transform;

public class Mushroom extends Item {
    private final static Texture MUSHROOM_TEXTURE = new Texture(Gdx.files.internal("assets/mushroom.png"));

    /**
     * Creates a new Item with the specified transform and texture.
     *
     * @param transform The fixed position and size of the Item.
     */
    public Mushroom(Transform transform) {
        super(transform, MUSHROOM_TEXTURE);
    }
}

package inf112.skeleton.model.gameobject.fixedobject;
import com.badlogic.gdx.graphics.Texture;
import inf112.skeleton.model.gameobject.Position;
import inf112.skeleton.model.gameobject.Transform;

/**
 * An Item is every fixed object that can be picked up
 */

public class Item extends FixedObject{
    protected Transform transform;
    protected Texture sprite;

    public Item(Transform transform, Texture sprite) {
        super(transform, sprite);
    }

    private void pickUp(){ // TODO implement meg

    }


}

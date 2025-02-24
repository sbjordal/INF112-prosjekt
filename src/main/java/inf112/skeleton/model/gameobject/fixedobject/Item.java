package inf112.skeleton.model.gameobject.fixedobject;
import com.badlogic.gdx.graphics.Texture;
import inf112.skeleton.model.gameobject.Position;

/**
 * An Item is every fixed object that can be picked up
 */

public class Item extends FixedObject{
    protected Position position;
    protected Texture sprite;

    public Item(Position position, Texture sprite) {
        super(position, sprite);
    }

    private void pickUp(){ // TODO implement meg

    }


}

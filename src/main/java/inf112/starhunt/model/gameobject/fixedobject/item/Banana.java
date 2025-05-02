package inf112.starhunt.model.gameobject.fixedobject.item;
import com.badlogic.gdx.math.Vector2;
import inf112.starhunt.model.gameobject.Collidable;
import inf112.starhunt.model.gameobject.Transform;
import inf112.starhunt.model.gameobject.Visitor;

/**
 * Class for {@link inf112.starhunt.model.gameobject.mobileobject.actor.Player} powerup.
 * An {@link Item} that can be collected by player, gets removed after collection.
 */
public class Banana extends Item implements Collidable {
    private final static int BIG_JUMP_FORCE = 73000;
    private final static Vector2 LARGE_PLAYER_SIZE = new Vector2(65, 135);

    /**
     * Creates a new Item with the specified transform.
     *
     * @param transform The fixed position and size of the Item.
     */
    public Banana(Transform transform) {
        super(transform);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visitBanana(this);
    }

    public int getBigJumpForce() {
        return BIG_JUMP_FORCE;
    }

    public Vector2 getLargePlayerSize() {
        return LARGE_PLAYER_SIZE;
    }
}

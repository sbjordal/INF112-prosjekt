package inf112.starhunt.model.gameobject.fixedobject;

import inf112.starhunt.model.gameobject.Collidable;
import inf112.starhunt.model.gameobject.GameObject;
import inf112.starhunt.model.gameobject.Transform;
import inf112.starhunt.model.gameobject.Visitor;
import inf112.starhunt.view.ViewableGround;


/**
 * A {@link FixedObject} that is representing the platforms/ground that
 * the enemies and player walks on and collides into.
 */
public class Ground extends FixedObject implements Collidable, ViewableGround {
    private final String alteration;

    /**
     * A constructor for a ground object with the specified transform and alteration.
     * @param transform  The fixed position and size of the Ground.
     * @param alteration The alteration to be set for the Ground
     */
    public Ground(Transform transform, String alteration) {
        super(transform);
        this.alteration = alteration;
    }

    /**
     * Default constructor for a ground object with the specified transform.
     * Alteration is set to "0000" as default.
     * @param transform The fixed position and size of the Ground.
     */
    public Ground(Transform transform) {
        super(transform);
        this.alteration = "0000";
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visitGround(this);
    }

    @Override
    public String getAlteration() {
        return alteration;
    }
}

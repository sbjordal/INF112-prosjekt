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

    public Ground(Transform transform, String alteration) {
        super(transform);
        this.alteration = alteration;
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

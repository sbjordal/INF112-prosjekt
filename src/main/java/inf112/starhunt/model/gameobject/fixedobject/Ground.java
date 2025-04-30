package inf112.starhunt.model.gameobject.fixedobject;

import inf112.starhunt.model.gameobject.Collidable;
import inf112.starhunt.model.gameobject.Transform;
import inf112.starhunt.model.gameobject.Visitor;


public class Ground extends FixedObject implements Collidable {
    public Ground(Transform transform) {
        super(transform);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visitGround(this);
    }
}

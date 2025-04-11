package inf112.skeleton.model.gameobject.fixedobject;

import inf112.skeleton.model.gameobject.Collidable;
import inf112.skeleton.model.gameobject.Transform;
import inf112.skeleton.model.gameobject.Visitor;


//TODO, javadoc
public class Ground extends FixedObject implements Collidable {
    public Ground(Transform transform) {
        super(transform);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}

package inf112.skeleton.model.gameobject;

public interface Collidable {

    void accept(CollisionVisitor visitor);

}

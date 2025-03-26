package inf112.skeleton.model.gameobject;

public interface Movable {
    //TODO javadoc

    void applyGravity(float deltaTime, boolean isOnGround);
    void moveHorizontally(float deltaTime, boolean moveLeft, boolean moveRight);
    void moveVertically(float deltaTime);
}

package inf112.skeleton.model.gameobject;

public interface Movable {
    //TODO javadoc
    // TODO: Ha isTouchingGround her? Andre metoder som også kan ligge her?


    void applyGravity(float deltaTime, boolean isOnGround);
    void moveHorizontally(float deltaTime, boolean moveLeft, boolean moveRight);
    void moveVertically(float deltaTime);
}

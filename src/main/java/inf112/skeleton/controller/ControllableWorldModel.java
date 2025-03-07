package inf112.skeleton.controller;

import inf112.skeleton.model.GameState;

public interface ControllableWorldModel {

    /**
     * Tells us the state of the game
     *
     * @return the state of the game
     */
    GameState getGameState();

    /**
     * Moves player left if the move is legal (also calls isLegalMove) TODO: finskriv
     */
    public void move(int deltaX, int deltaY);

    /**
     * Makes player jump. TODO: finskriv
     */
    public void jump();

    /**
     * Pauses the game and modifies GameState
     */
    public void pause();

    /**
     * Resumes the game and modifies GameState
     */
    public void resume();

    /**
     * Set the movement speed for when the objet is moving
     *
     * @param speed the speed of the object
     */
    public void setMovementSpeed(int speed);

    /**
     * Gets the movement speed of the object.
     * @return an int, the speed the object is moving in
     */
    public int getMovementSpeed();

    /**
     * Set the direction the player is moving in
     * @param direction the direction the player moves in
     */ //TODO : Skrive dette mer forklarende/endre navn og skrive bedre dokumentasjon
    public void setMovement(Direction direction);
}


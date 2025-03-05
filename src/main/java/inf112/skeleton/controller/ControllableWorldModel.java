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
     * // TODO - Midlertidig, da Model har andre metoder som endrer gamestate, som jeg glemte at eksisterte :)
     * Changes the state of the game
     *
     * @param gameState the new gamestate
     */
    public void setGameState(GameState gameState);

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
}


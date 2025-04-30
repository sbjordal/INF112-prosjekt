package inf112.starhunt.model.gameobject.mobileobject.actor;

import com.badlogic.gdx.math.Vector2;
import inf112.starhunt.model.PositionValidator;
import inf112.starhunt.model.WorldModel;
import inf112.starhunt.model.gameobject.Collidable;
import inf112.starhunt.model.gameobject.Visitor;

import java.util.List;

/**
 * This interface defines the essential behaviors and attributes a player must expose
 * in order to interact with the {@link WorldModel}.
 */
public interface ModelablePlayer extends Visitor, Collidable {

    /**
     * Attempts to initiate a jump if the actor is currently grounded.
     *
     * @param isGrounded Whether the actor is currently touching the ground.
     */
    void jump(boolean isGrounded);

    /**
     * Applies a given force to the player's vertical velocity, causing a jump.
     *
     * @param force The amount of force to apply for the jump.
     */
    void applyJumpForce(int force);

    /**
     * A method for checking whether the player is standing on the ground, or not
     * @param objectList, the list of objects that the player potentially is standing on.
     * @return true if the Player is touching the ground, false if not.
     */
    boolean isTouchingGround(List<Collidable> objectList);

    /**
     * Resolves a proposed movement of the player.
     * It filters the movement based on the provided validator.
     *
     * @param deltaX      The proposed change in the x-coordinate.
     * @param deltaY      The proposed change in the y-coordinate.
     * @param validator   The {@link PositionValidator} used to filter the movement.
     */
    void resolveMovement(float deltaX, float deltaY, PositionValidator validator);

    /**
     * Reset score attributes back to their initial values.
     */
    void resetScores();

    /**
     * Reset some of the player's attributes back to their initial values.
     * The player's position is set to a new spawn point.
     *
     * @param spawnPoint The new spawn point.
     */
    void resetForNewLevel(Vector2 spawnPoint);

    /**
     * Applies gravity to the player's vertical velocity.
     * If the player is on the ground and not moving upward, vertical velocity is set to 0.
     * Otherwise, gravity is applied based on the elapsed time since the last frame.
     *
     * @param deltaTime   The time in seconds since the last update. Used to ensure frame rate–independent physics.
     * @param isOnGround  Whether the object is currently standing on a solid surface.
     */
    void applyGravity(float deltaTime, boolean isOnGround);

    /**
     * Reduces the player's lives by the specified damage amount.
     * If the damage reduces lives to zero or below, the player dies.
     *
     * @param damage The amount of damage to inflict. Must be non-negative.
     * @throws IllegalArgumentException if {@code damage} is negative.
     */
    void receiveDamage(int damage);

    boolean getIsAlive();

    int getVerticalVelocity();

    int getMovementSpeed();

    int getTotalScore();

    int getCoinCounter();

    boolean getGoToNextLevel();

    int getLives();

    int getMovementDirection();

    List<Collidable> getObjectsToRemove();

    void setMovementDirection(int movementDirection);

    void setRespawned(boolean bool);
}

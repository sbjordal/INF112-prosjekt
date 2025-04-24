package inf112.starhunt.model;

import inf112.starhunt.model.gameobject.CollisionBox;

/**
 * Defines a contract for validating whether a game object's position is legal within the game world.
 *
 */
public interface PositionValidator {
    /**
     * Determines whether the specified collision box is in a valid (legal) position.
     *
     * @param collisionBox The collision box representing the object's prospective position and size.
     * @return {@code true} if the position is valid and movement is allowed; {@code false} otherwise.
     */
    boolean isLegalMove(CollisionBox collisionBox);
}

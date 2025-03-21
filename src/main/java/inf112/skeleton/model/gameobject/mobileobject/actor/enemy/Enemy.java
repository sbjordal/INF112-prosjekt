package inf112.skeleton.model.gameobject.mobileobject.actor.enemy;

import com.badlogic.gdx.math.Vector2;
import inf112.skeleton.model.WorldModel;
import inf112.skeleton.model.gameobject.CollisionBox;
import inf112.skeleton.model.gameobject.Scorable;
import inf112.skeleton.model.gameobject.GameObject;
import inf112.skeleton.model.gameobject.Transform;
import inf112.skeleton.model.gameobject.fixedobject.item.Item;
import inf112.skeleton.model.gameobject.mobileobject.actor.Actor;

import java.util.List;

/**
 * Represents all enemy types.
 * An enemy type is any {@link GameObject} that inflicts damage on the player.
 */
public abstract class Enemy extends Actor implements Scorable {
    final private static long COLLISION_COOLDOWN = 48;
    final private int objectScore;
    final private int damage;
    private long lastCollisionTime;
    protected Direction direction;

    /**
     * Creates a new Enemy with the specified movement speed, object score, damage and transform.
     *
     * @param movementSpeed The rate of which the Enemy moves horizontally.
     * @param objectScore   The score points obtained by defeating the Enemy.
     * @param damage        The amount of damage the Enemy will inflict.
     * @param transform     The initial transform of the Enemy.
     */
    public Enemy(int movementSpeed, int objectScore, int damage, Transform transform) {
        super(1, movementSpeed, transform);

        this.objectScore = objectScore;
        this.damage = damage;
        this.lastCollisionTime = 0;
    }

    /**
     * Moves the Enemy in a predefined movement pattern.
     */
    public void moveEnemy(float deltaTime, List<GameObject> objectList) {
        for (GameObject gameObject : objectList) {
            if (gameObject == null) {
                throw new NullPointerException("GameObject is null.");
            }

            // makes enemy able to move through items
            if (gameObject instanceof Item) {
                continue;
            }

            // switches direction when colliding
            CollisionBox otherCollisionBox = gameObject.getCollisionBox();
            final float endOfLevel = WorldModel.LEVEL_WIDTH - getTransform().getSize().x;
            final boolean isColliding = getCollisionBox().isCollidingWith(otherCollisionBox);
            final boolean isCollidingFromBottom = getCollisionBox().isCollidingFromBottom(otherCollisionBox);
            final boolean isOutsideLevel = getTransform().getPos().x < 0 || getTransform().getPos().x > endOfLevel;

            if ((isColliding && !isCollidingFromBottom) || isOutsideLevel) {
                if (isReadyToCollide()) {
                    switchDirection();
                    break;
                }
            }
        }

        int distance = (int) (getMovementSpeed() * deltaTime);
        if (direction == Direction.LEFT) {
            distance *= -1;
        }

        move(distance, 0);
    }

    /**
     * Checks if the enemy has recently collided.
     * See {@code COLLISION_COOLDOWN} for duration constraint.
     *
     * @return  true if enemy is ready to collide, else false
     */
    private boolean isReadyToCollide() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastCollisionTime >= COLLISION_COOLDOWN) {
            lastCollisionTime = currentTime;
            return true;
        }

        return false;
    }

    /**
     * Represents the direction an enemy can move.
     */
    protected enum Direction {
        LEFT,
        RIGHT
    }

    /**
     * Switch the movement direction of an enemy.
     */
    protected void switchDirection() {
        switch (direction) {
            case LEFT: direction = Direction.RIGHT; break;
            case RIGHT: direction = Direction.LEFT; break;
        }
    }

    /**
     * Returns the amount of damage the Enemy can inflict.
     *
     * @return The damage value as an integer.
     */
    public int getDamage() {
        return damage;
    }

    @Override
    public int getObjectScore() {
        return objectScore;
    }


    // TODO: prøvde å få enemy til å kun skifte retning når den kolliderer fra retning fremover.
    //  Fungerer ikke helt som den skal på grunn av kollisjons håndteringen fra vesntre/høyre.
//    private boolean isCollidingFromFront(CollisionBox otherCollisionBox) {
//        if (otherCollisionBox == null) {
//            throw new NullPointerException("CollisionBox is null.");
//        }
//
//        return switch (direction) {
//            case LEFT -> getCollisionBox().isCollidingFromLeft(otherCollisionBox);
//            case RIGHT -> getCollisionBox().isCollidingFromRight(otherCollisionBox);
//        };
//    }

}

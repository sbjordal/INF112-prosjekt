package inf112.skeleton.model.gameobject.mobileobject.actor.enemy;

import com.badlogic.gdx.math.Vector2;
import inf112.skeleton.model.WorldModel;
import inf112.skeleton.model.gameobject.CollisionBox;
import inf112.skeleton.model.gameobject.Scorable;
import inf112.skeleton.model.gameobject.GameObject;
import inf112.skeleton.model.gameobject.Transform;
import inf112.skeleton.model.gameobject.fixedobject.item.Item;
import inf112.skeleton.model.gameobject.mobileobject.actor.Actor;
import inf112.skeleton.model.gameobject.mobileobject.actor.Player;

import java.util.List;

/**
 * Represents all enemy types.
 * An enemy type is any {@link GameObject} that inflicts damage on the player.
 */
public abstract class Enemy extends Actor implements Scorable {
    final private static long COLLISION_COOLDOWN = 48;
    final private int objectScore;
    private long lastCollisionTime;
    protected Direction direction;

    /**
     * Creates a new Enemy with the specified lives, movement speed, object score, damage and transform.
     *
     * @param lives         The initial amount of lives of the Enemy.
     * @param movementSpeed The rate of which the Enemy moves horizontally.
     * @param objectScore   The score points obtained by defeating the Enemy.
     * @param damage        The amount of damage the Enemy will inflict.
     * @param transform     The initial transform of the Enemy.
     */
    public Enemy(int lives, int movementSpeed, int objectScore, int damage, Transform transform) {
        super(lives, movementSpeed, transform);

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

                    // Attack the player if the colliding object is the player
                    if (gameObject instanceof Player player && !isOutsideLevel) {
                        attack(player);
                    }
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
     * Attacks the player, prioritizing power-up removal over direct damage.
     * If the player has a power-up, it is removed. Otherwise, the player takes damage.
     *
     * @param player The player to attack.
     */
    private void attack(Player player) {
        // TODO: This is copy-pasted straight from WorldModel. This should use Player.hitBy() once WorldModel code regarding player is refactored.
        // TODO: include ATTACK_COOLDOWN to affect attack frequency.
        if (player.getHasPowerUp()) {
            player.setHasPowerUp(false);
            player.setSize(new Vector2(40, 80)); // TODO: STANDARD_PLAYER_SIZE.
            int middleOfPlayer = (int) (player.getTransform().getSize().x / 2);
            player.move(middleOfPlayer, 0);

        } else {
            dealDamage(player, getDamage());
        }
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

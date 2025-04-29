package inf112.starhunt.model.gameobject.mobileobject.actor.enemy;

import com.badlogic.gdx.math.Vector2;
import inf112.starhunt.model.PositionValidator;
import inf112.starhunt.model.WorldModel;
import inf112.starhunt.model.gameobject.*;
import inf112.starhunt.model.gameobject.fixedobject.Ground;
import inf112.starhunt.model.gameobject.fixedobject.item.Banana;
import inf112.starhunt.model.gameobject.fixedobject.item.Coin;
import inf112.starhunt.model.gameobject.fixedobject.item.Star;
import inf112.starhunt.model.gameobject.mobileobject.actor.Actor;
import inf112.starhunt.model.gameobject.mobileobject.actor.Player;

import java.util.List;

/**
 * Represents all enemy types.
 * An enemy type is any {@link GameObject} that inflicts damage on the player.
 */
public abstract class Enemy extends Actor implements Scorable, Visitor, Collidable {
    private final static long COLLISION_COOLDOWN = 48;
    private final int objectScore;
    private long lastCollisionTime;

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
    public void moveEnemy(float deltaTime) {
        float distance = getMovementSpeed() * deltaTime;
        distance *= getMovementDirection();

        move(distance, 0);
    }

    protected void attack(Player player) {
        player.takeDamage(getDamage());
    }

    /**
     * Checks if the enemy has recently collided.
     * See {@code COLLISION_COOLDOWN} for duration constraint.
     *
     * @return  true if enemy is ready to collide, else false
     */
    protected boolean isReadyToCollide() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastCollisionTime >= COLLISION_COOLDOWN) {
            lastCollisionTime = currentTime;
            return true;
        }
        return false;
    }

    @Override
    public void resolveMovement(float deltaX, float deltaY, PositionValidator validator) {
        Vector2 newActorPosition = filterPosition(deltaX, deltaY, validator, this);

        final int belowLevel = -200;
        if (newActorPosition.y <= belowLevel) {
            receiveDamage(getLives());
        }
        move(newActorPosition);
    }

    @Override
    public boolean isColliding(List<Collidable> collidables, CollisionBox collisionBox) {
        for (Collidable collided : collidables) {
            if (this.equals(collided)) {
                continue;
            }

            if (collisionBox.isCollidingWith(collided.getCollisionBox())) {
                collided.accept(this);
                return true;
            }
        }
        return false;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visitEnemy(this);
    }

    @Override
    public void visitCoin(Coin coin) {}

    @Override
    public void visitStar(Star star) {}

    @Override
    public void visitBanana(Banana banana) {}

    @Override
    public void visitGround(Ground ground) {
        switchDirection();
    }

    @Override
    public void visitEnemy(Enemy enemy) {
        if (!this.equals(enemy)) {
            switchDirection();
        }
    }

    @Override
    public void visitPlayer(Player player) {
        attack(player);
        switchDirection();
    }

    @Override
    public int getObjectScore() {
        return objectScore;
    }
}

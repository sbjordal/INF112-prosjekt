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
        this.direction = Direction.RIGHT;
    }

    /**
     * Moves the Enemy in a predefined movement pattern.
     */
    public void moveEnemy(float deltaTime) {
        int distance = (int) (getMovementSpeed() * deltaTime);
        if (direction == Direction.LEFT) {
            distance *= -1;
        }

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
        System.out.println("Switching direction!");
        switch (direction) {
            case LEFT: direction = Direction.RIGHT; break;
            case RIGHT: direction = Direction.LEFT; break;
        }
    }

    @Override
    public void resolveMovement(int deltaX, int deltaY, PositionValidator validator, Visitor visitor) {
        Vector2 newActorPosition = filterPosition(deltaX, deltaY, validator, this);

        final int belowLevel = -200;
        if (newActorPosition.y <= belowLevel) {
            receiveDamage(getLives());
        }
    }

    @Override
    public boolean isColliding(List<Collidable> collidables, CollisionBox collisionBox) {
        for (Collidable collided : collidables) {
            if (getCollisionBox().isCollidingWith(collided.getCollisionBox())) {
                collided.accept(this);
                return true;
            }
        }

        return false;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public void visit(Coin coin) {}

    @Override
    public void visit(Star star) {}

    @Override
    public void visit(Banana banana) {}

    @Override
    public void visit(Ground ground) {
        System.out.println("Enemy visits Ground!");

//        CollisionBox groundBox = ground.getCollisionBox();
//        CollisionBox myBox = getCollisionBox();
//
//        boolean collidesFromLeft = myBox.isCollidingFromLeft(groundBox);
//        boolean collidesFromRight = myBox.isCollidingFromRight(groundBox);
//
//        // Bare snu hvis det er en faktisk veggkollisjon
//        if (collidesFromLeft || collidesFromRight) {
//            switchDirection();
//        }
    }

    @Override
    public void visit(Enemy enemy) {
        if (!this.equals(enemy)) {
            switchDirection();
        }
    }

    @Override
    public void visit(Player player) {
        CollisionBox playerCollisionBox = player.getCollisionBox();
        final float endOfLevel = WorldModel.LEVEL_WIDTH - getTransform().getSize().x;
        final boolean isColliding = getCollisionBox().isCollidingWith(playerCollisionBox);
        final boolean isCollidingFromBottom = getCollisionBox().isCollidingFromTop(playerCollisionBox); // TODO: dinna va skifta til "isCollidingFromTop" fra "isCollidingFromBottom". Tror det skal være mer rett, men lager denne kommentaren her for å markere dette som en potensiell feil. :)
        final boolean isOutsideLevel = getTransform().getPos().x < 0 || getTransform().getPos().x > endOfLevel;

        if (((isColliding && !isCollidingFromBottom) || isOutsideLevel) && isReadyToCollide()) {
            if (!isOutsideLevel) {
                attack(player);
            }
            switchDirection();
        }
    }



    @Override
    public int getObjectScore() {
        return objectScore;
    }
}

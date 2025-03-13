package inf112.skeleton.model.gameobject.mobileobject.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import inf112.skeleton.model.gameobject.GameObject;
import inf112.skeleton.model.gameobject.Transform;

/**
 * Represents the user-controlled actor in the game.
 * The user-controlled actor is unique and is defined as the only {@link GameObject}
 * that responds to user-input.
 */
final public class Player extends Actor {
    private final static Texture PLAYER_TEXTURE;
    private final static Vector2 START_POSITION = new Vector2(380, 500); // 102 = old value
    private final static Vector2 SIZE = new Vector2(40, 80);
    private final static Transform PLAYER_TRANSFORM = new Transform(START_POSITION, SIZE);
    private Animation<TextureRegion> runAnimation;
    private Animation<TextureRegion> idleAnimation;
    private boolean isRunning;
    private float stateTime;
    static {
        PLAYER_TEXTURE = new Texture(Gdx.files.internal("player/idle/i1.png"));
    }
    /**
     * Creates a new Player with the specified health and movement speed.
     *
     * @param health        The initial health of the Player.
     * @param movementSpeed The rate of which the Player moves horizontally.
     */
    public Player(int health, int movementSpeed) {
        super(health, movementSpeed, PLAYER_TRANSFORM, PLAYER_TEXTURE);
        this.isRunning = false;
        this.stateTime = 0f;
        TextureRegion[] runFrames = new TextureRegion[8];
        for (int i = 0; i < 8; i++) {
            runFrames[i] = new TextureRegion(new Texture(Gdx.files.internal("player/run/r" + (i + 1) + ".png")));
        }
        runAnimation = new Animation<>(0.1f, runFrames);
        runAnimation.setPlayMode(Animation.PlayMode.LOOP);

        TextureRegion[] idleFrames = new TextureRegion[12];
        for (int i = 0; i < 12; i++) {
            idleFrames[i] = new TextureRegion(new Texture(Gdx.files.internal("player/idle/i" + (i + 1) + ".png")));
        }
        idleAnimation = new Animation<>(0.1f, idleFrames);
        idleAnimation.setPlayMode(Animation.PlayMode.LOOP);
    }
    @Override
    public TextureRegion getCurrentFrame() {
        return isRunning ? runAnimation.getKeyFrame(stateTime, true) : idleAnimation.getKeyFrame(stateTime, true);
    }
    @Override
    public void update(float deltaTime) {
        this.stateTime += deltaTime;
    }
    public void setRunning(boolean running) {
        if (this.isRunning != running) {
            this.stateTime = 0;
        }
        this.isRunning = running;
    }

    public void jump(int jumpForce) {
        setVerticalVelocity(jumpForce);
    }

//    @Override
//    public int getCurrentMovementSpeed() {
//        return super.getCurrentMovementSpeed();
//    }
    public void dispose() {
        for (TextureRegion frame : runAnimation.getKeyFrames()) {
            frame.getTexture().dispose();
        }
        for (TextureRegion frame : idleAnimation.getKeyFrames()) {
            frame.getTexture().dispose();
        }
    }
}

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
    private Animation<TextureRegion> runAnimationRight;
    private Animation<TextureRegion> runAnimationLeft;
    private Animation<TextureRegion> idleAnimation;
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
        this.stateTime = 0f;
        TextureRegion[] runFramesRight = new TextureRegion[8];
        TextureRegion[] runFramesLeft = new TextureRegion[8];
        for (int i = 0; i < 8; i++) {
            TextureRegion frame = new TextureRegion(new Texture(Gdx.files.internal("player/run/r" + (i + 1) + ".png")));
            runFramesRight[i] = frame;
            runFramesLeft[i] = new TextureRegion(frame);
            runFramesLeft[i].flip(true, false);
        }

        runAnimationRight = new Animation<>(0.1f, runFramesRight);
        runAnimationRight.setPlayMode(Animation.PlayMode.LOOP);
        runAnimationLeft = new Animation<>(0.1f, runFramesLeft);
        runAnimationLeft.setPlayMode(Animation.PlayMode.LOOP);

        TextureRegion[] idleFrames = new TextureRegion[12];
        for (int i = 0; i < 12; i++) {
            idleFrames[i] = new TextureRegion(new Texture(Gdx.files.internal("player/idle/i" + (i + 1) + ".png")));
        }
        idleAnimation = new Animation<>(0.1f, idleFrames);
        idleAnimation.setPlayMode(Animation.PlayMode.LOOP);
    }
    @Override
    public TextureRegion getCurrentFrame() {
        int dir = getMovementDirection();
        if (dir == 0) {
            return idleAnimation.getKeyFrame(stateTime, true);
        } else if (dir == 1) {
            return runAnimationRight.getKeyFrame(stateTime, true);
        } else{
            return runAnimationLeft.getKeyFrame(stateTime, true);
        }
    }
    @Override
    public void update(float deltaTime) {
        this.stateTime += deltaTime;
    }

    public void jump(int jumpForce) {
        setVerticalVelocity(jumpForce);
    }

    @Override
    public void dispose() {
        for (TextureRegion frame : runAnimationRight.getKeyFrames()) {
            frame.getTexture().dispose();
        }
        for (TextureRegion frame : runAnimationLeft.getKeyFrames()){
            frame.getTexture().dispose();
        }
        for (TextureRegion frame : idleAnimation.getKeyFrames()) {
            frame.getTexture().dispose();
        }
    }
}

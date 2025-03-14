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
    private Animation<TextureRegion> runAnimationRight;
    private Animation<TextureRegion> runAnimationLeft;
    private Animation<TextureRegion> idleAnimation;
    private float stateTime;
    private boolean hasPowerUp;
    static {
        PLAYER_TEXTURE = new Texture(Gdx.files.internal("player/idle/i1.png"));
    }

    /**
     * Creates a new Player with the specified lives and movement speed.
     *
     * @param lives         The initial lives of the Player.
     * @param movementSpeed The rate of which the Player moves horizontally.
     */
    public Player(int lives, int movementSpeed, Transform transform) {
        super(lives, movementSpeed, transform, PLAYER_TEXTURE);
        this.hasPowerUp = false;
        this.stateTime = 0f;
        animate();

    }
    private void animate(){
        TextureRegion[] runFramesRight = new TextureRegion[8];
        TextureRegion[] runFramesLeft = new TextureRegion[8];
        for (int i = 0; i < 8; i++) {
            TextureRegion frame = new TextureRegion(new Texture(Gdx.files.internal("player/run/r" + (i + 1) + ".png")));
            runFramesRight[i] = frame;
            runFramesLeft[i] = new TextureRegion(frame);
            runFramesLeft[i].flip(true, false);
        }

        this.runAnimationRight = new Animation<>(0.1f, runFramesRight);
        this.runAnimationRight.setPlayMode(Animation.PlayMode.LOOP);
        this.runAnimationLeft = new Animation<>(0.1f, runFramesLeft);
        this.runAnimationLeft.setPlayMode(Animation.PlayMode.LOOP);

        TextureRegion[] idleFrames = new TextureRegion[12];
        for (int i = 0; i < 12; i++) {
            idleFrames[i] = new TextureRegion(new Texture(Gdx.files.internal("player/idle/i" + (i + 1) + ".png")));
        }
        this.idleAnimation = new Animation<>(0.1f, idleFrames);
        this.idleAnimation.setPlayMode(Animation.PlayMode.LOOP);
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

    public void setHasPowerUp(boolean hasPowerUp) {
        this.hasPowerUp = hasPowerUp;
    }

    public boolean getHasPowerUp() {
        return hasPowerUp;
    }
}

package inf112.starhunt.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import java.util.HashMap;

public class PlayerAnimation {
    private final HashMap<String, Animation<TextureRegion>> animations;
    private float stateTime;

    public PlayerAnimation() {
        this.animations = new HashMap<>();
        this.stateTime = 0f;
        loadAnimations();
    }

    private void loadAnimations() {
        loadRunningAnimations();
        loadIdleAnimation();
        loadJumpingAnimations();
    }

    private void loadRunningAnimations() {
        TextureRegion[] runFramesRight = new TextureRegion[8];
        TextureRegion[] runFramesLeft = new TextureRegion[8];

        for (int i = 0; i < 8; i++) {
            TextureRegion frame = new TextureRegion(new Texture(Gdx.files.internal("player/run/r" + (i + 1) + ".png")));
            runFramesRight[i] = frame;
            runFramesLeft[i] = new TextureRegion(frame);
            runFramesLeft[i].flip(true, false);
        }

        animations.put("runRight", new Animation<>(0.1f, runFramesRight));
        animations.put("runLeft", new Animation<>(0.1f, runFramesLeft));

        animations.get("runRight").setPlayMode(Animation.PlayMode.LOOP);
        animations.get("runLeft").setPlayMode(Animation.PlayMode.LOOP);
    }

    private void loadIdleAnimation() {
        TextureRegion[] idleFrames = new TextureRegion[12];
        for (int i = 0; i < 12; i++) {
            idleFrames[i] = new TextureRegion(new Texture(Gdx.files.internal("player/idle/i" + (i + 1) + ".png")));
        }

        animations.put("idle", new Animation<>(0.1f, idleFrames));
        animations.get("idle").setPlayMode(Animation.PlayMode.LOOP);
    }

    private void loadJumpingAnimations() {
        TextureRegion jumpFramesRight = new TextureRegion(new Texture(Gdx.files.internal("player/jump/jump.png")));
        TextureRegion jumpFramesLeft = new TextureRegion(jumpFramesRight);
        jumpFramesLeft.flip(true, false);

        animations.put("jumpRight", new Animation<>(0.1f, jumpFramesRight));
        animations.put("jumpLeft", new Animation<>(0.1f, jumpFramesLeft));

        TextureRegion landingFramesRight = new TextureRegion(new Texture(Gdx.files.internal("player/jump/landing.png")));
        TextureRegion landingFramesLeft = new TextureRegion(landingFramesRight);
        landingFramesLeft.flip(true, false);

        animations.put("landingRight", new Animation<>(0.1f, landingFramesRight));
        animations.put("landingLeft", new Animation<>(0.1f, landingFramesLeft));
    }

    public void update(float deltaTime, boolean isPaused) {
        if (!isPaused) {
            stateTime += deltaTime;
        }
    }

    public TextureRegion getFrame(int direction, float verticalVelocity) {
        final boolean isFalling = verticalVelocity < 0;
        final boolean isJumping = verticalVelocity > 0;

        if (direction == 0) {
            if (isFalling) {
                return animations.get("landingRight").getKeyFrame(stateTime, true);
            } else if (isJumping) {
                return animations.get("jumpRight").getKeyFrame(stateTime, true);
            } else {
                return animations.get("idle").getKeyFrame(stateTime, true);
            }
        } else if (direction == 1) {
            if (isFalling) {
                return animations.get("landingRight").getKeyFrame(stateTime, true);
            } else if (isJumping) {
                return animations.get("jumpRight").getKeyFrame(stateTime, true);
            } else {
                return animations.get("runRight").getKeyFrame(stateTime, true);
            }
        } else {
            if (isFalling) {
                return animations.get("landingLeft").getKeyFrame(stateTime, true);
            } else if (isJumping) {
                return animations.get("jumpLeft").getKeyFrame(stateTime, true);
            } else {
                return animations.get("runLeft").getKeyFrame(stateTime, true);
            }
        }
    }

    public void dispose() {
        for (Animation<TextureRegion> animation : animations.values()){
            for (TextureRegion frame : animation.getKeyFrames()){
                frame.getTexture().dispose();
            }
        }
    }
}

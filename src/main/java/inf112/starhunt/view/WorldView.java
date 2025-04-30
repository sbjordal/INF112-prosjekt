package inf112.starhunt.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import inf112.starhunt.model.GameState;
import inf112.starhunt.model.gameobject.Transform;
import inf112.starhunt.model.gameobject.ViewableObject;
import inf112.starhunt.model.gameobject.mobileobject.actor.Player;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import java.util.EventListener;
import java.util.HashMap;

public class WorldView extends AbstractScreen implements EventListener {
    private final ViewableWorldModel model;
    private SpriteBatch batch;
    private Texture headerTexture;
    private ParallaxBackground parallaxBackground;
    private BitmapFont font;
    private GlyphLayout layout;
    private HashMap<String, Texture> textures;
    private PlayerAnimation playerAnimation;
    private GameState currentGameState;
    private GameState previousGameState = null;
    private SoundHandler soundHandler;
    private final Viewport viewport;
    private Music menuMusic;
    private Music activeGameMusic;
    private Sound gameOverSound;
    private boolean gameOverSoundHasPlayed;

    public WorldView(ViewableWorldModel model, int width, int height) {
        this.viewport = new ExtendViewport(width, height);
        this.model = model;
        this.layout = new GlyphLayout();
        this.textures = new HashMap<>();
        this.currentGameState = model.getGameState();
    }

    /**
     * The following 6 setters are only for testing purposes
     */
    void setFont(BitmapFont font) { this.font = font; }
    void setBatch(SpriteBatch batch) { this.batch = batch; }
    void setParallaxBackground(ParallaxBackground bg) { this.parallaxBackground = bg; }
    void setPlayerAnimation(PlayerAnimation animation) { this.playerAnimation = animation; }
    void setTextures(HashMap<String, Texture> textures) { this.textures = textures; }
    void setLayout(GlyphLayout layout) {this.layout = layout;}
    HashMap<String, Texture> getTextures(){return textures;}

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
        parallaxBackground.dispose();
        playerAnimation.dispose();
        for (Texture texture : textures.values()){
            texture.dispose();
        }
    }

    @Override
    public void show() {
        parallaxBackground = new ParallaxBackground(model.getLevelWidth());
        playerAnimation = new PlayerAnimation();
        loadTextures();
        batch = new SpriteBatch();
        headerTexture = new Texture("background/header.png");
        soundHandler = new SoundHandler();
        font = loadFont("font/VT323-Regular.ttf");
        menuMusic = soundHandler.getMusic("menu");
        activeGameMusic = soundHandler.getMusic("active");
        gameOverSound = soundHandler.getSound("gameover");
        gameOverSoundHasPlayed = false;

        model.getViewablePlayer().setOnCoinCollected(() -> soundHandler.playSound("coin"));
        model.getViewablePlayer().setOnCollisionWithEnemy(() -> soundHandler.playSound("ouch"));
        model.getViewablePlayer().setOnCollisionWithEnemyDealDamage(() -> soundHandler.playSound("bounce"));
        model.getViewablePlayer().setOnBananaCollected(() -> soundHandler.playSound("powerup"));
        model.getViewablePlayer().setOnCollisionWithStar(() -> soundHandler.playSound("newlevel"));
    }

    private BitmapFont loadFont(String fontFilePath) {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(fontFilePath));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 24;
        parameter.color = Color.WHITE;
        parameter.borderWidth = 1f;
        parameter.borderColor = Color.BLACK;
        BitmapFont font = generator.generateFont(parameter);
        generator.dispose();
        return font;
    }

    @Override
    public void render(float v) {
        currentGameState = model.getGameState();
        handleGameStateSounds();

        switch (currentGameState) {
            case GAME_MENU -> drawGameMenu();
            case GAME_ACTIVE -> drawGameActive();
            case GAME_PAUSED -> drawGamePaused();
            case GAME_OVER -> drawGameOver();
        }

        if (!model.getInfoMode() && (currentGameState == GameState.GAME_MENU || currentGameState == GameState.GAME_PAUSED)) {
            drawCenteredText("Press 'i' for game info",2, 300);
        } else if (model.getInfoMode() && (currentGameState == GameState.GAME_MENU || currentGameState == GameState.GAME_PAUSED)) {
            drawGameInfo();
        }
    }

    private void handleGameStateSounds() {
        if(currentGameState != previousGameState ){
            switch (currentGameState) {
                case GAME_MENU -> {
                    gameOverSoundHasPlayed = false;
                    if (activeGameMusic != null){
                        activeGameMusic.setLooping(false);
                        activeGameMusic.stop();
                    }
//                    if (menuMusic != null && !menuMusic.isPlaying()) {
//                        soundHandler.playMusic(menuMusic);
//                    }
                }
                case GAME_ACTIVE -> {
//                    if(menuMusic != null && menuMusic.isPlaying()){
//                        menuMusic.setLooping(false);
//                        menuMusic.stop();
//                    }
                    if (activeGameMusic != null && !activeGameMusic.isPlaying()) {
                        soundHandler.playMusic(activeGameMusic);
                    }
                }
                case GAME_PAUSED -> {
                    if (activeGameMusic != null) {
                        activeGameMusic.pause();
                    }
                }
                case GAME_OVER -> {
                    if (activeGameMusic != null) {
                        activeGameMusic.setLooping(false);
                        activeGameMusic.stop();
                    }
                    if (gameOverSound != null && !gameOverSoundHasPlayed) {
                        gameOverSound.play();
                        gameOverSoundHasPlayed = true;
                    }
                }
            }
            previousGameState = currentGameState;
        }
    }

    private void drawGameMenu() {
        ScreenUtils.clear(Color.CLEAR);
        float headerWidth = 800f;
        float headerHeight = 400f;
        float margin = 50f;
        float headerY = Gdx.graphics.getHeight() - headerHeight - margin;
        float centerX = viewport.getCamera().position.x;

        batch.begin();
        parallaxBackground.render(batch);
        if (currentGameState.equals(GameState.GAME_MENU) && !model.getInfoMode()) {
            batch.draw(headerTexture, centerX - headerWidth/2, headerY, headerWidth, headerHeight);
        }
        batch.end();

        drawCenteredText("Press ENTER to start the game", 3,100);
    }

    private void drawGameInfo() {
        if (currentGameState.equals(GameState.GAME_MENU)) {
            font.setColor(Color.GOLD);
            drawCenteredText("Race to catch the star and grab as many coins as you can! Watch out for enemies — a single fall\n" +
                    "or hit could cost you everything. Jump on enemies to knock them out, and munch on bananas to grow\n" +
                    "bigger and tougher! Time's ticking... complete your mission before it's too late!", 1.3f, 200);
            drawCenteredText("Ready? GO!", 1.5f, 350);
            font.setColor(Color.WHITE);
        }
        drawCenteredText("Press 'i' to remove game info", 3,-400);
        drawCenteredText("To jump press 'w', 'space' or up arrow\n" +
                "To move right press 'd' or right arrow\n" +
                "To move left press 'a' or left arrow\n" +
                "To pause the game when playing press 'p'\n" +
                "To exit the game at any time press ESC", 2,-350);
    }


    private void drawGameActive() {
        drawLevel();
    }

    private void drawGamePaused() {
        drawLevel();
        drawCenteredText("PAUSED", 3, 0);
        drawCenteredText("Press 'r' to return to the game menu", 3, 330);
    }

    private void drawGameOver() {
        ScreenUtils.clear(Color.CLEAR);
        drawCenteredText("GAME OVER", 4.5f, 0);
        font.getData().setScale(1);
        drawCenteredText("Press ENTER to return to the game menu", 2, -200);
        drawCenteredText("Score: " + model.getTotalScore() + "   Level: " + model.getLevelCounter(), 2, 200);
    }

    void drawCenteredText(String text, float textScale, float lowerTextBy) {
        font.getData().setScale(textScale);
        layout.setText(font, text);

        float centerX = viewport.getCamera().position.x;
        float centerY = viewport.getCamera().position.y;
        float width = layout.width;
        float height = layout.height;

        batch.begin();
        font.draw(batch, text, centerX- width/2, centerY - height/2 - lowerTextBy);
        batch.end();
    }

    private void drawPlayer(float deltaTime, int movementDirection, float verticalVelocity, GameState gameState){
        // Player-data
        Transform playerTransform = model.getViewablePlayer().getTransform();
        float playerX = playerTransform.getPos().x;
        float playerY = playerTransform.getPos().y;
        float playerWidth = playerTransform.getSize().x;
        float playerHeight = playerTransform.getSize().y;

        TextureRegion currentFrame = playerAnimation.getFrame(movementDirection, verticalVelocity);
        boolean isPaused = !gameState.equals(GameState.GAME_ACTIVE);
        playerAnimation.update(deltaTime,  isPaused);
        batch.draw(currentFrame, playerX, playerY, playerWidth, playerHeight);
    }

    void drawLevel() {

        // Map-data
        float deltaTime = Gdx.graphics.getDeltaTime();

        // Camera
        ScreenUtils.clear(Color.CLEAR);
        updateViewportCamera();
        batch.setProjectionMatrix(viewport.getCamera().combined);

        // Text to be shown
        String totalScore = "Total score: "+ model.getTotalScore();
        String coinCount = "Coins: " + model.getCoinCounter();
        String lives = "Lives: " + model.getPlayerLives();
        String countDown = "CountDown: " + model.getCountDown();
        String levelCount = "Level: " + model.getLevelCounter();
        font.getData().setScale(2);

        float screenHeight = viewport.getWorldHeight();
        float leftX = getViewportLeftX();

        // Parallax background
        int movementDirection = model.getMovementDirection();
        parallaxBackground.update(movementDirection, deltaTime, model.getGameState() != GameState.GAME_ACTIVE);

        // Drawing objects
        float verticalVelocity = model.getVerticalVelocity();
        batch.begin();
        parallaxBackground.render(batch);
        drawPlayer(deltaTime, movementDirection, verticalVelocity, currentGameState);
        drawObjects();
        font.draw(batch, lives, leftX + 80, screenHeight - 15);
        font.draw(batch, coinCount, leftX + 320, screenHeight - 15);
        font.draw(batch, totalScore, leftX + 550, screenHeight - 15);
        font.draw(batch, countDown, leftX + 930, screenHeight - 15);
        font.draw(batch, levelCount, leftX + 1300, screenHeight - 15);
        batch.end();
    }

    void updateViewportCamera() {
        Transform playerTransform = model.getViewablePlayer().getTransform();
        float playerX = playerTransform.getPos().x;
        float playerWidth = playerTransform.getSize().x;
        float playerCenterX = playerX + playerWidth / 2;
        float camX = viewport.getCamera().position.x;
        float camY = viewport.getCamera().position.y;
        float screenWidth = viewport.getWorldWidth();

        if (playerCenterX > camX && camX < model.getBoardWidth() - screenWidth / 2) {
            camX = playerCenterX;
        }
        camX = MathUtils.clamp(camX, screenWidth/2, 5000 - screenWidth/2); // bytt 5000 med bredden av brettet

        viewport.getCamera().position.set(camX, camY, 0);
        viewport.apply();
        model.updateViewportLeftX(getViewportLeftX());
    }

    void loadTextures(){
        textures.put("leopard", new Texture("assets/leopard.png"));
        textures.put("snail", new Texture("assets/snail.png"));
        textures.put("coin", new Texture("assets/coin.png"));
        textures.put("powerup", new Texture("assets/banana.png"));
        textures.put("ground", new Texture("obstacles/castleCenter.png"));
        textures.put("star", new Texture("assets/star.png"));
    }

    Texture getTexture(ViewableObject obj){
        String className = obj.getClass().getSimpleName();
        return switch (className) {
            case "Leopard" -> textures.get("leopard");
            case "Snail" -> textures.get("snail");
            case "Coin" -> textures.get("coin");
            case "Banana" -> textures.get("powerup");
            case "Ground" -> textures.get("ground");
            case "Star" -> textures.get("star");
            default -> throw new IllegalArgumentException("Unsupported class name for texture: " + className);
        };
    }

    void drawObjects() {
        for (ViewableObject object : model.getObjectList()) {

            if (object instanceof Player) continue;
            int direction = object.getDirection();

            Texture objectTexture = getTexture(object);

            float objectX = object.getTransform().getPos().x;
            float objectY = object.getTransform().getPos().y;
            float objectWidth = object.getTransform().getSize().x;
            float objectHeight = object.getTransform().getSize().y;

            if (direction >= 0) {
                batch.draw(objectTexture, objectX + objectWidth, objectY, -objectWidth, objectHeight);
            } else {
                batch.draw(objectTexture, objectX, objectY, objectWidth, objectHeight);
            }
        }
    }

    private float getViewportLeftX() {
        return viewport.getCamera().position.x - viewport.getWorldWidth() / 2;
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

}

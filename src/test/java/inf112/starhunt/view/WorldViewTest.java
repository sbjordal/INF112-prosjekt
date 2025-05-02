package inf112.starhunt.view;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Vector2;
import inf112.starhunt.model.GameState;
import inf112.starhunt.model.gameobject.Transform;
import inf112.starhunt.model.gameobject.ViewableObject;
import inf112.starhunt.model.gameobject.fixedobject.Ground;
import inf112.starhunt.model.gameobject.fixedobject.item.Banana;
import inf112.starhunt.model.gameobject.fixedobject.item.Coin;
import inf112.starhunt.model.gameobject.fixedobject.item.Star;
import inf112.starhunt.model.gameobject.mobileobject.actor.Player;
import inf112.starhunt.model.gameobject.mobileobject.actor.enemy.Leopard;
import inf112.starhunt.model.gameobject.mobileobject.actor.enemy.Snail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class WorldViewTest {

    private WorldView worldView;
    private ViewableWorldModel mockModel;
    private Player mockPlayer;
    private Transform mockTransform;
    private SpriteBatch mockBatch;
    private BitmapFont mockFont;
    private BitmapFont.BitmapFontData mockFontData;
    private GlyphLayout mockLayout;
    private ParallaxBackground mockBackground;
    private PlayerAnimation mockPlayerAnimation;
    private Texture mockTexture;

    @BeforeEach
    void setUp() {
        Gdx.graphics = mock(Graphics.class);
        Gdx.gl = mock(GL20.class);
        when(Gdx.graphics.getDeltaTime()).thenReturn(1f);

        mockModel = mock(ViewableWorldModel.class);
        mockPlayer = mock(Player.class);
        mockTransform = mock(Transform.class);
        mockBatch = mock(SpriteBatch.class);
        mockFont = mock(BitmapFont.class);
        mockFontData = mock(BitmapFont.BitmapFontData.class);
        mockLayout = mock(GlyphLayout.class);
        mockBackground = mock(ParallaxBackground.class);
        mockPlayerAnimation = mock(PlayerAnimation.class);
        mockTexture = mock(Texture.class);

        when(mockModel.getGameState()).thenReturn(GameState.GAME_MENU);
        when(mockModel.getViewablePlayer()).thenReturn(mockPlayer);
        when(mockPlayer.getTransform()).thenReturn(mockTransform);
        when(mockTransform.getPos()).thenReturn(new com.badlogic.gdx.math.Vector2(100, 50));
        when(mockTransform.getSize()).thenReturn(new com.badlogic.gdx.math.Vector2(20, 40));
        when(mockFont.getData()).thenReturn(mockFontData);
        Gdx.files = mock(Files.class);
        FileHandle mockFileHandle = mock(FileHandle.class);
        when(Gdx.files.internal("font/VT323-Regular.ttf")).thenReturn(mockFileHandle);

        worldView = new WorldView(mockModel, 800, 600);

        worldView.setFont(mockFont);
        worldView.setBatch(mockBatch);
        worldView.setParallaxBackground(mockBackground);
        worldView.setPlayerAnimation(mockPlayerAnimation);
        worldView.setTextures(new HashMap<>() {{
            put("leopard", mockTexture);
            put("snail", mockTexture);
            put("coin", mockTexture);
            put("powerup", mockTexture);
            put("ground", mockTexture);
            put("star", mockTexture);
        }});
        worldView.setLayout(mockLayout);
    }

    @Test
    void testLoadTextures() {
        worldView.setTextures(new HashMap<>());

        FileHandle mockHandle = mock(FileHandle.class);
        Gdx.files = mock(Files.class);
        when(Gdx.files.internal(anyString())).thenReturn(mockHandle);

        try (MockedConstruction<Texture> mocked = mockConstruction(Texture.class,
                (mock, context) -> {
                })) {
            worldView.loadTextures();

            var textures = worldView.getTextures();
            assertNotNull(textures);
            assertEquals(21, textures.size());
            assertTrue(textures.containsKey("leopard"));
            assertTrue(textures.containsKey("snail"));
            assertTrue(textures.containsKey("coin"));
            assertTrue(textures.containsKey("powerup"));
            assertTrue(textures.containsKey("star"));

            assertEquals(21, mocked.constructed().size());
        }
    }

    @Test
    public void testGetTextureThrowsForUnknownClass() {
        ViewableObject unknown = mock(ViewableObject.class);

        when(unknown.getClass()).thenAnswer(invocation -> new Object() {
            public String getSimpleName() {
                return "Cactus";
            }
        }.getClass());

        assertThrows(IllegalArgumentException.class, () -> worldView.getTexture(unknown));
    }

    @Test
    void testGetTextures() {
        Texture mockTexture = mock(Texture.class);

        HashMap<String, Texture> textures = new HashMap<>();
        textures.put("leopard", mockTexture);
        textures.put("snail", mockTexture);
        textures.put("coin", mockTexture);
        textures.put("powerup", mockTexture);
        textures.put("ground_0000", mockTexture);
        textures.put("star", mockTexture);
        worldView.setTextures(textures);

        Transform transform = new Transform(new Vector2(), new Vector2());
        List<ViewableObject> objects = List.of(
                new Leopard(new Vector2()),
                new Snail(new Vector2()),
                new Coin(transform),
                new Banana(transform),
                new Ground(transform),
                new Star(transform)
        );

        for (ViewableObject obj : objects) {
            Texture texture = worldView.getTexture(obj);
            assertNotNull(texture, "Texture should not be null for: " + obj.getClass().getSimpleName());
            assertEquals(mockTexture, texture, "Texture mismatch for: " + obj.getClass().getSimpleName());
        }
    }


    @Test
    public void testDisposeDoesNotThrow() {
        assertDoesNotThrow(() -> worldView.dispose());
    }

    @Test
    public void testRenderGameMenuCallsDrawMenu() {
        when(mockModel.getGameState()).thenReturn(GameState.GAME_MENU);
        worldView.render(1f);
        verify(mockModel, atLeastOnce()).getGameState();
    }

    @Test
    public void testDrawLevelDoesNotCrash() {
        when(mockModel.getGameState()).thenReturn(GameState.GAME_ACTIVE);
        when(mockModel.getMovementDirection()).thenReturn(1);
        when(mockModel.getTotalScore()).thenReturn(100);
        when(mockModel.getCoinCounter()).thenReturn(5);
        when(mockModel.getPlayerLives()).thenReturn(3);
        when(mockModel.getCountDown()).thenReturn(30);
        when(mockModel.getVerticalVelocity()).thenReturn(0f);

        TextureRegion frame = mock(TextureRegion.class);
        when(mockPlayerAnimation.getFrame(anyInt(), anyFloat())).thenReturn(frame);

        WorldView spyView = spy(worldView);
        doNothing().when(spyView).updateViewportCamera();

        spyView.drawLevel();

        verify(mockBackground).render(mockBatch);
        verify(mockBatch).draw(eq(frame), anyFloat(), anyFloat(), anyFloat(), anyFloat());
    }

    @Test
    public void testDrawObjectsSkipsPlayer() {
        ViewableObject enemy = mock(ViewableObject.class);
        when(enemy.getTransform()).thenReturn(mockTransform);
        when(mockModel.getObjectList()).thenReturn(List.of(mockPlayer, enemy));

        WorldView spyView = spy(worldView);
        doReturn(mockTexture).when(spyView).getTexture(enemy);

        spyView.setBatch(mockBatch);
        spyView.drawObjects();

        verify(mockBatch).draw(eq(mockTexture), anyFloat(), anyFloat(), anyFloat(), anyFloat());
    }

    @Test
    public void testDrawCenteredTextWorks() {
        worldView.drawCenteredText("Test Message", 2, 50);
        verify(mockFont).draw(any(), eq("Test Message"), anyFloat(), anyFloat());
    }

    @Test
    public void testDisposeCallsAllDisposeMethods() {
        Texture texture1 = mock(Texture.class);
        Texture texture2 = mock(Texture.class);

        HashMap<String, Texture> textures = new HashMap<>();
        textures.put("ground", texture1);
        textures.put("coin", texture2);

        worldView.setTextures(textures);
        worldView.setBatch(mockBatch);
        worldView.setFont(mockFont);
        worldView.setParallaxBackground(mockBackground);
        worldView.setPlayerAnimation(mockPlayerAnimation);
        worldView.dispose();

        verify(mockBatch).dispose();
        verify(mockFont).dispose();
        verify(mockBackground).dispose();
        verify(mockPlayerAnimation).dispose();
        verify(texture1).dispose();
        verify(texture2).dispose();
    }
}

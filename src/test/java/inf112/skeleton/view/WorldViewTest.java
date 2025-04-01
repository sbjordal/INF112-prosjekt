package inf112.skeleton.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import inf112.skeleton.model.GameState;
import inf112.skeleton.model.gameobject.Transform;
import inf112.skeleton.model.gameobject.ViewableObject;
import inf112.skeleton.model.gameobject.mobileobject.actor.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
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

        TextureRegion frame = mock(TextureRegion.class);
        when(mockPlayerAnimation.getFrame(anyInt())).thenReturn(frame);

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
}

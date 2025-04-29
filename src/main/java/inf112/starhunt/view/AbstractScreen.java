package inf112.starhunt.view;

import com.badlogic.gdx.Screen;

/**
 * Provides a default implementation for the {@link Screen} interface.
 * This abstract class allows concrete screens to extend it and only
 * override the methods they are interested in, rather than having to provide
 * empty implementations for all methods of the interface.
 */
public class AbstractScreen implements Screen {
    @Override
    public void show() {}

    @Override
    public void render(float v) {}

    @Override
    public void resize(int i, int i1) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {}
}

package inf112.starhunt.model;

import com.badlogic.gdx.ApplicationListener;

/**
 * Provides a default implementation for the {@link ApplicationListener} interface.
 * This abstract class allows concrete application listeners to extend it and only
 * override the methods they are interested in, rather than having to provide
 * empty implementations for all methods of the interface.
 */
public class AbstractApplicationListener implements ApplicationListener {
    @Override
    public void create() {}

    @Override
    public void resize(int i, int i1) {}

    @Override
    public void render() {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void dispose() {}

}

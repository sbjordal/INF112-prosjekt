package inf112.starhunt.app;

import org.lwjgl.system.Configuration;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.utils.Os;
import com.badlogic.gdx.utils.SharedLibraryLoader;

import inf112.starhunt.model.WorldModel;

public class Main {
    private final static int WINDOW_WIDTH = 1500;
    private final static int WINDOW_HEIGHT = 920;

    public static void main(String[] args) {
		if (SharedLibraryLoader.os == Os.MacOsX) {


			Configuration.GLFW_LIBRARY_NAME.set("glfw_async");
		}
		
		Lwjgl3ApplicationConfiguration cfg = new Lwjgl3ApplicationConfiguration();
        cfg.setResizable(false);
        cfg.setTitle("Star Hunt");
        cfg.setWindowedMode(WINDOW_WIDTH, WINDOW_HEIGHT);

        new Lwjgl3Application(new WorldModel(WINDOW_WIDTH, WINDOW_HEIGHT), cfg);
    }
}
package inf112.skeleton.app;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

public class Main {
    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration cfg = new Lwjgl3ApplicationConfiguration();
        cfg.setTitle("dinosaurspill - LOL");
        cfg.setWindowedMode(480, 320);
        System.out.println("Endre var her");

        new Lwjgl3Application(new HelloWorld(), cfg);
        // Eivind branch test
    }
}
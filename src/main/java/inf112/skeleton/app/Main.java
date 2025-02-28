package inf112.skeleton.app;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import inf112.skeleton.controller.PlayerController;
import inf112.skeleton.model.WorldBoard;
import inf112.skeleton.model.WorldModel;

public class Main {
    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration cfg = new Lwjgl3ApplicationConfiguration();
        cfg.setResizable(false); // Gjør det umulig å justere viduet ved å dra i det
        cfg.setTitle("Mario 2");
        final int windowWidth = 1280;
        final int windowHeight = 720;
        WorldBoard board = new WorldBoard(windowHeight,windowWidth);
        cfg.setWindowedMode(windowWidth, windowHeight);

        new Lwjgl3Application(new WorldModel(board), cfg);
        System.out.println("Eivind er ikke dum i hode sitt");
    }
}
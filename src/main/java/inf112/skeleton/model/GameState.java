package inf112.skeleton.model;

/**
 * Represents the different states of the game.
 * {@link #GAME_ACTIVE}     The game is currently running.
 * {@link #GAME_PAUSED}     The game is temporarily paused.
 * {@link #GAME_OVER}       The game has ended.
 * {@link #GAME_MENU}       The game is in the main menu.
 */
public enum GameState {
    GAME_ACTIVE,
    GAME_PAUSED,
    GAME_OVER,
    GAME_MENU
}
package inf112.skeleton.app.model;

import inf112.skeleton.model.WorldBoard;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class WorldBoardTest {

    @Test
    void constructorShouldSetHeightAndWidthCorrectly() {
        WorldBoard board = new WorldBoard(10, 15);
        assertEquals(10, board.height());
        assertEquals(15, board.width());
    }

    @Test
    void testHashCodeAndEquals() {
        WorldBoard board1 = new WorldBoard(5, 5);
        WorldBoard board2 = new WorldBoard(5, 5);

        assertEquals(board1, board2);
        assertEquals(board1.hashCode(), board2.hashCode());
    }

    @Test
    void testNotEquals() {
        WorldBoard board1 = new WorldBoard(5, 5);
        WorldBoard board2 = new WorldBoard(10, 10);

        assertNotEquals(board1, board2);
    }

    @Test
    void testToString() {
        WorldBoard board = new WorldBoard(7, 8);
        String expected = "WorldBoard[height=7, width=8]";

        assertEquals(expected, board.toString());
    }
}

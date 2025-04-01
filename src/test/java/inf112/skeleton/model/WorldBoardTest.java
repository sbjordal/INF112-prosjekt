package inf112.skeleton.model;

import inf112.skeleton.model.WorldBoard;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class WorldBoardTest {
    // worldboard statisk

    @Test
    void constructorShouldSetHeightAndWidthCorrectly() {
        WorldBoard board = new WorldBoard(15, 10);
        assertEquals(15, board.width());
        assertEquals(10, board.height());

    }

    @Test
    void testHashCodeAndEquals() {
        WorldBoard board1 = new WorldBoard(10, 5);
        WorldBoard board2 = new WorldBoard(10, 5);

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
        WorldBoard board = new WorldBoard(8, 7);
        String expected = "WorldBoard[width=8, height=7]";

        assertEquals(expected, board.toString());
    }
}

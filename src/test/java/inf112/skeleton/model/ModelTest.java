package inf112.skeleton.model;

import inf112.skeleton.model.WorldBoard;
import inf112.skeleton.model.WorldModel;
import org.junit.jupiter.api.BeforeEach;

public class ModelTest {
    private WorldModel worldModel;

    @BeforeEach
    void setUp() {
        WorldBoard board = new WorldBoard(100, 100); // Oppretter et brett med størrelse 100x100
//        worldModel = new WorldModel(board);
    }
}

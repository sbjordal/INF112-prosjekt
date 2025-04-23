package inf112.starhunt.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

    public class TripleTest {

        private Triple<String, Integer, Boolean> triple;

        @BeforeEach
        void setUp() {
            triple = new Triple<>("Test", 42, true);
        }

        @Test
        void testTripleNotNull() {
            assertNotNull(triple);
        }

        @Test
        void testGetFirst() {
            assertEquals("Test", triple.getFirst(), "getFirst() should return the first value");
        }

        @Test
        void testGetSecond() {
            assertEquals(42, triple.getSecond(), "getSecond() should return the second value");
        }

        @Test
        void testGetThird() {
            assertTrue(triple.getThird(), "getThird() should return the third value");
        }

        @Test
        void testAllValuesCorrect() {
            assertAll(
                    () -> assertEquals("Test", triple.first),
                    () -> assertEquals(42, triple.second),
                    () -> assertEquals(true, triple.third)
            );
        }
    }



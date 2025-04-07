package inf112.skeleton.app;


import inf112.skeleton.model.WorldModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

import org.mockito.Mockito;
import org.slf4j.Logger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

public class MainTest {

    //TODO: Vurdere å fjerne / finne alternativ måte å teste main, gir null utslag i PIT

    @Test
    public void testLogger() {
        // Mock Logger
        Logger mockLogger = mock(Logger.class);
        when(mockLogger.isInfoEnabled()).thenReturn(true);

        // Test logging
        mockLogger.info("App started");
        verify(mockLogger).info("App started");
    }

    @Test
    public void testLwjgl3ApplicationConfiguration() {
        Lwjgl3ApplicationConfiguration cfg = new Lwjgl3ApplicationConfiguration();

        Assertions.assertNotNull(cfg);
    }

}

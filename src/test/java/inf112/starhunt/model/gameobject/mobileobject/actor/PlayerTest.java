package inf112.starhunt.model.gameobject.mobileobject.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.math.Vector2;
import inf112.starhunt.model.PositionValidator;
import inf112.starhunt.model.gameobject.*;
import inf112.starhunt.model.gameobject.fixedobject.Ground;
import inf112.starhunt.model.gameobject.fixedobject.item.Banana;
import inf112.starhunt.model.gameobject.fixedobject.item.Coin;
import inf112.starhunt.model.gameobject.fixedobject.item.Star;
import inf112.starhunt.model.gameobject.mobileobject.MobileObjectFactory;
import inf112.starhunt.model.gameobject.mobileobject.actor.enemy.Enemy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PlayerTest {

    private Player player;
    private Transform transform;
    private Banana banana;
    private Coin coin;
    private Ground ground;
    private Enemy enemy;

    @BeforeEach
    void setUp() {
        //Mocker graphics for å kunne bruke deltatime i testene som krever det
        Graphics mockGraphics = mock(Graphics.class);
        Gdx.graphics = mockGraphics;
        when(Gdx.graphics.getDeltaTime()).thenReturn(1 / 60f); // 60 FPS som eksempel

        transform = TransformUtils.createTransformForObjects(0,0,50,100);
        player = new Player(3, 5, transform);

        Transform bananaTransform = TransformUtils.createTransformForObjects(0,0,1,1);
        banana = new Banana(bananaTransform);

        Transform coinTransform = TransformUtils.createTransformForObjects(0,0,1,1);
        coin = new Coin(coinTransform);

        Transform groundTransform = TransformUtils.createTransformForObjects(0,-10,10,10);
        ground = new Ground(groundTransform);

        Transform snailTransform = TransformUtils.createTransformForObjects(0,0,1,1);
        enemy = MobileObjectFactory.createSnail(10, 0);
    }

    @Test
    void testPlayerInitialization() {
        assertNotNull(player);
        assertEquals(3, player.getLives());
        assertTrue(player.getIsAlive());

        assertFalse(player.getHasPowerUp(), "Player should not have a power-up initially.");
        assertFalse(player.getRespawned(), "Player should not be respawned initially.");

        assertEquals(0, player.getLastAttackTime(), "Last attack time should be initialized to 0.");
        assertEquals(0, player.getLastBounceTime(), "Last bounce time should be initialized to 0.");
        assertEquals(5, player.getMovementSpeed(), "Player should have a movement speed of 5");
    }


    @Test
    void testReceiveDamageReducesLives() {
        player.receiveDamage(1);
        assertEquals(2, player.getLives());
        assertTrue(player.getIsAlive());
    }

    @Test
    void testReceiveDamageKillsPlayer() {
        // player has 3 lives initially
        assertEquals(3, player.getLives());

        // player loses 3 lives and should die
        player.receiveDamage(3);
        assertEquals(0, player.getLives());
        assertFalse(player.getIsAlive());
    }

    @Test
    void testReceiveNegativeDamage() {
        assertThrows(IllegalArgumentException.class, () -> player.receiveDamage(-1), "Damage can not be negative.");
        assertThrows(IllegalArgumentException.class, () -> player.dealDamage(player, -1), "Damage can not be negative.");
    }

    @Test
    void testReceiveZeroDamage() {
        int initialLives = player.getLives();
        player.receiveDamage(0);
        assertEquals(initialLives, player.getLives(), "Receiving 0 damage should not reduce lives.");
    }


    @Test
    void testSetAndGetHasPowerUp() {
        player.setHasPowerUp(true);
        assertTrue(player.getHasPowerUp());

        player.setHasPowerUp(false);
        assertFalse(player.getHasPowerUp());
    }

    @Test
    void testTakeDamageWithPowerUp() {
        player.setHasPowerUp(true);
        player.takeDamage(1);
        assertFalse(player.getHasPowerUp());
    }


    @Test
    void testVisitStarSetsNextLevel() {
        Star star = mock(Star.class);

        player.visitStar(star);
        assertTrue(player.getGoToNextLevel());
        assertFalse(player.getGoToNextLevel()); // reset
    }

    @Test
    void testTakeDamageWithoutPowerUp() {
        player.setHasPowerUp(false);
        int livesBefore = player.getLives();
        player.takeDamage(1);
        assertEquals(livesBefore - 1, player.getLives());
    }


    @Test
    void testPlayerNonPositiveLives() {

        assertThrows(IllegalArgumentException.class, () -> new Player(0, 5, transform));
        assertThrows(IllegalArgumentException.class, () -> new Player(-1, 5, transform));
    }

    @Test
    void testDealDamageThrowsExceptionForNullTarget() {
        assertThrows(IllegalArgumentException.class, () -> player.dealDamage(null, 1));
    }

    @Test
    void testJumpWhenGrounded() {
        // Spilleren hopper med grounded = true
        player.jump(true);

        // Forventet beregning av vertikal hastighet (velocity)
        float deltaTime = Gdx.graphics.getDeltaTime();
        int expectedVelocity = (int) (player.getJumpForce() * deltaTime);  // JumpForce * deltaTime

        // Sjekk om vertikal hastighet er satt korrekt
        assertNotEquals(0, player.getVerticalVelocity(), "Player should have a vertical velocity after jump.");
        assertEquals(expectedVelocity, player.getVerticalVelocity(), "Player's velocity should be correctly calculated based on jump force and deltaTime.");
    }

    @Test
    public void testJumpWhenNotGrounded() {
        player.jump(false);
        assertEquals(0, player.getVerticalVelocity(), "Player should not jump when not grounded.");

    }

    @Test
    public void testBounceWithoutPowerUp() {
        player.setHasPowerUp(false);
        player.bounce();
        assertTrue(player.getVerticalVelocity() > 0, "Player should bounce with normal force.");
    }

    @Test
    public void testBounceWithPowerUp() {
        player.setHasPowerUp(true);
        player.bounce();
        assertTrue(player.getVerticalVelocity() > 0, "Player should bounce with small force when powered up.");
    }

    @Test
    public void testSetAndGetRespawned() {
        player.setRespawned(true);
        assertTrue(player.getRespawned(), "Player should be marked as respawned.");
        player.setRespawned(false);
        assertFalse(player.getRespawned(), "Player should not be marked as respawned.");
    }

    @Test
    public void testLastAttackTime() {
        long currentTime = System.currentTimeMillis();
        player.setLastAttackTime(currentTime);
        assertEquals(currentTime, player.getLastAttackTime(), "Last attack time should be updated.");
    }

    @Test
    public void testLastBounceTime() {
        long currentTime = System.currentTimeMillis();
        player.setLastBounceTime(currentTime);
        assertEquals(currentTime, player.getLastBounceTime(), "Last bounce time should be updated.");
    }



    @Test
    void testResolvePlayerMovementBelowLevelKillsPlayer() {
        // Opprett en mock for PositionValidator
        PositionValidator validator = mock(PositionValidator.class);

        // Sett posisjonen til et punkt langt under nivået
        Vector2 farDown = new Vector2(0, -300); // Dette er under nivået

        // Opprett en CollisionBox for spilleren og mock dens posisjon
        CollisionBox collisionBox = new CollisionBox(new Transform(farDown, new Vector2(10, 10)));

        // Vi antar at isLegalMove skal returnere false for fall under nivået
        when(validator.isLegalMove(any(), eq(collisionBox))).thenReturn(false);  // Bruker eq(collisionBox) som matcher

        // Mock Transform for spilleren
        Transform transformMock = mock(Transform.class);
        when(transformMock.getPos()).thenReturn(farDown);
        when(transformMock.getSize()).thenReturn(new Vector2(50, 100));

        // Opprett spilleren med 3 liv
        Player player = new Player(3, 5, transformMock);

        // Simuler bevegelse, spilleren bør falle under bakken og få livene sine redusert
        player.resolveMovement(0, 0, validator);

        // Etter bevegelsen, sjekk at spilleren har 0 liv
        assertEquals(0, player.getLives(), "Player should lose all lives if they fall below the level.");
        assertFalse(player.getIsAlive(), "Player should not be alive if they fall below the level.");
    }


    @Test
    void testIsCollidingSkipsPlayer() {
        // Opprett et spy-objekt for player, så vi kan spore 'accept' metoden
        Player otherPlayer = spy(new Player(3, 5, TransformUtils.createTransformForObjects(0,0,50,100)));

        // Legg til 'otherPlayer' i listen over kolliderbare objekter
        List<Collidable> collidables = new ArrayList<>();
        collidables.add(otherPlayer); // Denne spillerens kollisjonsboks skal ignoreres i isColliding

        // Kall isColliding for å se om player hopper over andre spillere
        boolean result = player.isColliding(collidables, player.getCollisionBox());

        // Verifiser at accept() ikke ble kalt på otherPlayer, ettersom den skal bli hoppet over
        verify(otherPlayer, times(0)).accept(player); // Skal ikke kalle accept på otherPlayer

        // Sjekk at metoden returnerte false, da spilleren ikke skulle håndtere en kollisjon med en annen spiller
        assertFalse(result, "Collision with Player should be skipped.");
    }


    @Test
    void testUpdateAndGetTotalScore() {
        assertEquals(0, player.getTotalScore());
        player.updateTotalScore(5);
        assertEquals(5, player.getTotalScore());
    }

    @Test
    void testUpdateAndGetCoinCounter() {
        assertEquals(0, player.getCoinCounter());
        player.updateCoinCounter(3);
        assertEquals(3, player.getCoinCounter());
    }

    @Test
    void testAcceptCallsVisitor() {
        Visitor visitor = mock(Visitor.class);
        player.accept(visitor);
        verify(visitor, times(1)).visitPlayer(player);
    }

    @Test
    void testVisitCoin() {
        // Mock Coin-objektet
        Coin mockCoin = mock(Coin.class);
        int mockScore = 10;
        when(mockCoin.getObjectScore()).thenReturn(mockScore);

        // Simuler besøk
        player.visitCoin(mockCoin);

        // Sjekk at coinCounter har økt med 1
        assertEquals(1, player.getCoinCounter());

        // Sjekk at totalScore har økt med coinens score
        assertEquals(mockScore, player.getTotalScore());

        // Sjekk at objectsToRemove inneholder mockCoin
        assertTrue(player.getObjectsToRemove().contains(mockCoin));
    }

    @Test
    void testVisitBanana() {

        int oldJumpForce = player.getJumpForce();

        player.visitBanana(banana);

        assertTrue(player.getHasPowerUp(), "Player should have power-up after visiting Banana.");
        assertTrue(oldJumpForce < player.getJumpForce(), "Player's jump force should be updated.");
        assertTrue(player.getObjectsToRemove().contains(banana), "Player should add Banana to objectsToRemove.");
    }

    @Test
    void testIsCollidingWithNonPlayer() {
        // Opprett mock-objekter
        List<Collidable> collidables = new ArrayList<>();

        Coin coinSpy = spy(coin);

        CollisionBox playerCollisionBox = player.getCollisionBox();

        collidables.add(coinSpy);

        boolean result = player.isColliding(collidables, playerCollisionBox);

        // Verifisere at accept-metoden ble kalt på mynten
        verify(coinSpy).accept(player); // Dette sjekker at accept() ble kalt på mynten

        // Bekreft at metoden returnerte true fordi vi fant en kollisjon
        assertTrue(result, "Player should have collided with the coin.");
    }

    @Test
    void testSetOnCoinCollected() {
        // Lag en enkel mock for Runnable
        Runnable coinCollectedCallback = mock(Runnable.class);

        // Sett callback-en via metoden
        player.setOnCoinCollected(coinCollectedCallback);

        // Kall på callbacken ved å simulere at en mynt blir samlet
        player.visitCoin(coin);

        // Verifiser at callbacken ble kalt
        verify(coinCollectedCallback, times(1)).run();
    }

    @Test
    void testSetLivesToValidValue() {
        player.setLives(2);
        assertEquals(2, player.getLives(), "Lives should be updated to the new valid value.");

        player.setLives(0);
        assertEquals(0, player.getLives(), "Lives should be updated to the new valid value. 0 is a valid value");
    }

    @Test
    void testSetLivesToInvalidValueThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> player.setLives(-1), "Setting lives to negative should throw exception.");
        assertThrows(IllegalArgumentException.class, () -> player.setLives(-3), "Setting lives to negative should throw exception.");
    }

    @Test
    public void testJumpWithoutApplyJumpForce() {
        player.jump(true);  // Spilleren er på bakken
        assertTrue(player.getVerticalVelocity() > 0);

        // Spilleren får en hoppkraft som vanlig
        player.applyJumpForce(1000);
        assertTrue(player.getVerticalVelocity() > 0);
    }

    @Test
    public void testBananaCollectionWithoutCallback() {
        player.setOnBananaCollected(null);  // Sett callback til null
        player.visitBanana(banana);  // Spilleren samler en banana, men uten å trigge callback

        assertEquals(0, player.getCoinCounter());  // Her kan vi også sjekke at ingen score ble tildelt
    }

    @Test
    public void testPlayerBounceOffEnemy() {
        player.setLastBounceTime(System.currentTimeMillis() - 1000);  // Gjør spillerens bounce klart
        player.visitEnemy(enemy);

        assertEquals(3, player.getLives());
    }


    @Test
    void testSetAndGetIsMovingHorizontally() {
        player.setIsMovingHorizontally(false);

        assertFalse(player.getIsMovingHorizontally(), "Player should not be moving horizontally initially.");

        player.setIsMovingHorizontally(true);
        assertTrue(player.getIsMovingHorizontally(), "Player should be moving horizontally after set to true.");

        player.setIsMovingHorizontally(false);
        assertFalse(player.getIsMovingHorizontally(), "Player should not be moving horizontally after set to false.");
    }



}

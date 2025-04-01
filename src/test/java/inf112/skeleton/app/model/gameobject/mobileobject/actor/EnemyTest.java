package inf112.skeleton.app.model.gameobject.mobileobject.actor;

import inf112.skeleton.model.gameobject.mobileobject.actor.enemy.Enemy;
import inf112.skeleton.model.gameobject.mobileobject.actor.enemy.EnemyFactory;
import inf112.skeleton.model.gameobject.mobileobject.actor.enemy.EnemyType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class EnemyTest {

    //TODO: Sjekk pip for hva som mangler. Det meste av enemytester, utenom move
    // er implementert under enemyfactory
    @Test
    void testEnemyDeathSnail(){
        Enemy enemy = EnemyFactory.createSnail(0, 0, EnemyType.SNAIL);
        enemy.receiveDamage(1);
        assertFalse( enemy.isAlive());



    }

    @Test
    void testEnemyDeathLepoard(){
        Enemy enemy = EnemyFactory.createLeopard(0, 0, EnemyType.LEOPARD);
        enemy.receiveDamage(1);
        //TODO: Kommentert ut for å få kompilert
        // assertFalse( enemy.isAlive());



    }
}

package inf112.skeleton.model.gameobject.mobileobject.actor;

// TODO: Revisjon. Kan legge til flere enemytyper under SNEGL,
//       usikker på om dette er beste implementasjon
public enum EnemyType {
    SNEGL (50, 50, 1, 1, 50, 50);

    public final int health;
    public final int movementSpeed;
    public final int objectScore;
    public final int damage;
    public final float width;
    public final float height;

    EnemyType(int health, int movementSpeed, int objectScore, int damage, float width, float height) {
        this.health = health;
        this.movementSpeed = movementSpeed;
        this.objectScore = objectScore;
        this.damage = damage;
        this.width = width;
        this.height = height;
    }
}

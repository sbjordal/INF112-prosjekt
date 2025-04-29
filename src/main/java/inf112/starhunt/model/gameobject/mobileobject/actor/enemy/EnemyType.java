package inf112.starhunt.model.gameobject.mobileobject.actor.enemy;

public enum EnemyType {
    SNAIL(1, 70, 10, 1, 40, 40),
    LEOPARD(2, 140, 30, 1, 70, 70 );

    public final int health; // TODO: bør disse heller ha get-ere?
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

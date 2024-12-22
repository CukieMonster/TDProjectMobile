package com.tdproject.towers;

import static com.tdproject.main.FieldParameters.ENEMY_RADIUS;

import com.tdproject.enemies.Enemy;
import com.tdproject.enemies.EnemyManager;
import com.tdproject.gamestates.Playing;
import com.tdproject.graphics.Sprite;

import java.util.ArrayList;
import java.util.List;
import javax.vecmath.Vector2d;

public class HomingMissile extends Sprite {

    //private Vector2d position;
    private final Enemy target;
    private final Tower origin;
    private final double speed;

    public HomingMissile(Vector2d pos, Enemy target, Tower origin, double speed) {
        position = (Vector2d) pos.clone();
        this.target = target;
        this.origin = origin;
        this.speed = speed;
        loadSprite(SpriteId.MISSILE, MISSILE_WIDTH, MISSILE_HEIGHT);
    }

    public void update() {
        move();
    }

    private void move() {
        if (target == null) {
            //destroy missile
            origin.missiles.remove(this);
        }
        Vector2d direction = new Vector2d();// = Vector2d. target.position - position);
        direction.sub(target.getPosition(), position);
        //target reached (incl error)
        //System.err.println(direction.length());
        if (direction.length() < ENEMY_RADIUS) { // * Game.getInstance().getGameSpeed()) {
            impact();
            origin.missiles.remove(this);
        }
        direction.normalize();
        direction.scale(speed * Playing.getInstance().getGameSpeed());
        position.add(direction);
    }

    private void impact() {
        List<Enemy> enemiesInRange = calculateEnemiesInRange(target);
        for (Enemy enemy : enemiesInRange) {
            enemy.damage(origin.getDamage());
            enemy.applySlow(origin.getSlow(), 1);   // TODO: customizable duration
            enemy.applyDamageOverTime(origin.getDamageOverTime(), 1);   // TODO: customizable duration
        }
    }

    private List<Enemy> calculateEnemiesInRange(Enemy target) {
        List<Enemy> enemiesInRange = new ArrayList<>();
        enemiesInRange.add(target);
        for (Enemy enemy : EnemyManager.getInstance().getEnemies()) {
            if (enemy != null) {
                if (distanceToEnemy(target, enemy) < origin.getSplash()) {
                    enemiesInRange.add(enemy);
                }
            }
        }
        return enemiesInRange;
    }

    private double distanceToEnemy(Enemy target, Enemy enemy) {
        return Math.sqrt(Math.pow((target.getPosition().x - enemy.getPosition().x), 2) + Math.pow((target.getPosition().y - enemy.getPosition().y), 2));
    }

}

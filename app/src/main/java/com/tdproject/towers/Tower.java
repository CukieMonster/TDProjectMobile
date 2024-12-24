package com.tdproject.towers;

import static com.tdproject.main.FieldParameters.FIELD_SIZE;
import static com.tdproject.towers.TowerParameters.ATTACK_SPEED;
import static com.tdproject.towers.TowerParameters.COST;
import static com.tdproject.towers.TowerParameters.DAMAGE;
import static com.tdproject.towers.TowerParameters.DAMAGE_UPGRADE;
import static com.tdproject.towers.TowerParameters.DOT_UPGRADE;
import static com.tdproject.towers.TowerParameters.MAX_UPGRADE_LEVEL;
import static com.tdproject.towers.TowerParameters.MISSILE_SPEED;
import static com.tdproject.towers.TowerParameters.RANGE;
import static com.tdproject.towers.TowerParameters.RANGE_UPGRADE;
import static com.tdproject.towers.TowerParameters.SLOW_UPGRADE;
import static com.tdproject.towers.TowerParameters.SPEED_UPGRADE;
import static com.tdproject.towers.TowerParameters.SPLASH_UPGRADE;

import com.tdproject.enemies.Enemy;
import com.tdproject.enemies.EnemyManager;
import com.tdproject.main.Game;
import com.tdproject.main.Square;
import com.tdproject.ui.Button;

import lombok.Getter;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Getter
public class Tower extends Button {

    //private BufferedImage img;
    private final List<Enemy> enemiesInRange = new LinkedList<>();

    private final Map<UpgradeType, Integer> upgrades = new EnumMap<>(UpgradeType.class);
    private final int towerType;
    private int damage;
    private double attackSpeed;
    //private float range;
    private int splash;
    private int slow;
    private int damageOverTime;
    private double areaOfEffectMultiplier;
    private int radius;
    private int cost;
    private Square square = new Square(-1, -1);
    //private Vector2d position = new Vector2d(0, 0);
    //public int x;
    //public int y;

    public Set<HomingMissile> missiles = new HashSet<>();
    private int lastShot;
    /*public boolean active = false;
    public boolean visible = false;*/

    public Tower(int towerType) {
        super(SpriteId.TOWER_1, null, TOWER_WIDTH, TOWER_HEIGHT);
        action = b -> {
            TowerManager.getInstance().setSelectedTower(this);
            TowerManager.getInstance().setMode(TowerManagerMode.UPGRADING);
        };
        this.towerType = towerType;
        damage = DAMAGE[towerType];
        attackSpeed = ATTACK_SPEED[towerType];
        areaOfEffectMultiplier = 1;
        radius = (FIELD_SIZE / 2) + (FIELD_SIZE * RANGE[towerType]);
        cost = COST[towerType];
        setActive(false);
        setVisible(false);
        //this.x = x;
        //this.y = y;
        //this.img = img;
        //loadSprite(Sprite.TOWER_1);
    }

    public void update(int u) {
        calculateEnemiesInRange();
        attemptShot(u);
        for (HomingMissile m : missiles) {
            if (m != null) {
                m.update();
            }
        }
    }

    public void upgrade(UpgradeType upgradeType) {
        upgrades.computeIfPresent(upgradeType, (ut, i) -> i + 1);
        upgrades.putIfAbsent(upgradeType, 1);
        updateStats();
        cost *= 2;
    }

    private void updateStats() {
        areaOfEffectMultiplier = 0.25 * upgrades.getOrDefault(UpgradeType.AREA_OF_EFFECT, 0);
        if (areaOfEffectMultiplier == 0.0) {
            areaOfEffectMultiplier = 1;
        }
        damage = (int) ((DAMAGE[towerType] + Math.pow(DAMAGE_UPGRADE, upgrades.getOrDefault(UpgradeType.DAMAGE, 0))) * areaOfEffectMultiplier);
        attackSpeed = ATTACK_SPEED[towerType] - (SPEED_UPGRADE * upgrades.getOrDefault(UpgradeType.SPEED, 0));
        double range = RANGE[towerType] + (RANGE_UPGRADE * upgrades.getOrDefault(UpgradeType.RANGE, 0));
        radius = (FIELD_SIZE / 2) + (int) (FIELD_SIZE * range);
        splash = (int) (SPLASH_UPGRADE * upgrades.getOrDefault(UpgradeType.SPLASH, 0) * radius);
        slow = (int) ((SLOW_UPGRADE * upgrades.getOrDefault(UpgradeType.SLOW, 0)) * areaOfEffectMultiplier);
        damageOverTime = (int) ((DOT_UPGRADE * upgrades.getOrDefault(UpgradeType.DAMAGE_OVER_TIME, 0)) * areaOfEffectMultiplier);
    }

    private void attemptShot(int u) {
        if (lastShot + attackSpeed * Game.getInstance().getUpsSet() <= u) {
            if (upgrades.containsKey(UpgradeType.AREA_OF_EFFECT)) {
                applyAreaOfEffect();
                lastShot = u;
            }
            else if (findTarget()) {
                lastShot = u;
            }
        }
    }

    private void applyAreaOfEffect() {
        for (Enemy enemy : enemiesInRange) {
            enemy.damage(damage);
            enemy.applySlow(slow, 1);
            enemy.applyDamageOverTime(damageOverTime, 1);
        }
    }

    private boolean findTarget() {
        if (enemiesInRange.isEmpty()) {
            return false;
        }
        Enemy furthest = enemiesInRange.get(0);
        for (Enemy e : enemiesInRange) {
            if (e.getDistanceToTarget() < furthest.getDistanceToTarget()) {
                furthest = e;
            }
        }
        shoot(furthest);
        return true;
    }

    private void shoot(Enemy e) {
        missiles.add(new HomingMissile(position, e, this, MISSILE_SPEED[towerType]));
    }

    private void calculateEnemiesInRange() {
        enemiesInRange.clear();
        for (Enemy e : EnemyManager.getInstance().getEnemies()) {
            if (e != null) {
                if (distanceToEnemy(e) < radius) {
                    enemiesInRange.add(e);
                }
            }
        }
    }

    private double distanceToEnemy(Enemy e) {
        return Math.sqrt(Math.pow((position.x - e.getPosition().x), 2) + Math.pow((position.y - e.getPosition().y), 2));
    }

    // Getters and setters
//    public BufferedImage getImg() {
//        return img;
//    }

    public int getTowerType() {
        return towerType;
    }

    public Square getSquare() {
        return square;
    }

    public void setSquare(Square square) {
        this.square = square;
    }

}

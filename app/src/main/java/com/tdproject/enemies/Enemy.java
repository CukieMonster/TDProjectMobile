package com.tdproject.enemies;

import static com.tdproject.main.FieldParameters.FIELD_SIZE;
import static com.tdproject.main.FieldParameters.X_CENTERED_OFFSET;
import static com.tdproject.main.FieldParameters.X_FIELDS;
import static com.tdproject.main.FieldParameters.Y_CENTERED_OFFSET;
import static com.tdproject.main.FieldParameters.Y_FIELDS;

import com.tdproject.gamestates.MainMenu;
import com.tdproject.gamestates.Playing;
import com.tdproject.graphics.Sprite;
import com.tdproject.items.Item;
import com.tdproject.items.ItemParameters;
import com.tdproject.main.Game;
import com.tdproject.main.Modifiers;
import com.tdproject.main.Square;

import java.util.Random;
import lombok.Getter;

@Getter
public class Enemy extends Sprite {

    private final HealthBar healthBar;

    private final int enemyType;
    private final int maxHP;
    private final int value;
    private final int progress;
    private final double speed;

    private int HP;
    private Square square;
    private double distanceToTarget;
    private boolean active = true;

    private double slow;
    private int slowDuration;
    private int damageOverTime;
    private int damageOverTimeDuration;

    private boolean xAxisLocked = false;
    private boolean yAxisLocked = false;
    private final Random random = new Random(); // TODO move random outside class

    public Enemy(Square spawn, int enemyType) {
        this.enemyType = enemyType;
        maxHP = (int) (EnemyParameters.MAX_HP[enemyType] * (1 + MainMenu.getInstance().getDifficulty() / 10.0));
        HP = maxHP;
        speed = EnemyParameters.SPEEDS[EnemyParameters.SPEED_TIER[enemyType]];
        value = EnemyParameters.VALUE[enemyType];
        progress = EnemyParameters.PROGRESS[enemyType];
        square = spawn;
        distanceToTarget = Pathfinding.getInstance().getDistanceField()[square.getX()][square.getY()];
        position = spawn.squareToPosition();
        loadSprite(switch (enemyType) {
            case 0 -> SpriteId.ENEMY_0;
            case 1 -> SpriteId.ENEMY_1;
            case 2 -> SpriteId.ENEMY_2;
            case 3 -> SpriteId.ENEMY_3;
            case 4 -> SpriteId.ENEMY_4;
            default -> SpriteId.MISSING_SPRITE;
        }, Sprite.ENEMY_WIDTH, Sprite.ENEMY_HEIGHT);
        healthBar = new HealthBar(this);
    }

    public void update(int u) {
        if (slowDuration < u) {
            slow = 1;
        }
        if (damageOverTimeDuration < u) {
            damageOverTime = 0;
        } else {
            damage(damageOverTime);
        }
        moveInDirection(u);
    }

    public void damage(int amount) {
        HP -= amount;
        if (HP <= 0) {
            Playing.getInstance().adjustMoney(value);
            active = false;

            if (random.nextInt(100) < Modifiers.getDropRate() * progress) {
                Item newItem;
                int rar = random.nextInt(100);
                if (rar < Modifiers.getLegendaryDropRate()) {
                    newItem = new Item(5, ItemParameters.Rarity.Legendary);
                }
                else if (rar < Modifiers.getRareDropRate()) {
                    newItem = new Item(5, ItemParameters.Rarity.Rare);
                }
                else {
                    newItem = new Item(5, ItemParameters.Rarity.Common);
                }
                dropItem(newItem);
            }
        }
    }

    public void applySlow(int slowPercentage, int duration) {
        slow = 1.0 - (slowPercentage / 100.0);
        slowDuration = Game.getInstance().getUpdateCycle() + (duration * Game.getInstance().getUPS_SET());
    }

    public void applyDamageOverTime(int damageOverTime, int duration) {
        this.damageOverTime = damageOverTime;
        damageOverTimeDuration = Game.getInstance().getUpdateCycle() + (duration * Game.getInstance().getUPS_SET());
    }

    void dropItem(Item newItem) {
        Playing.getInstance().getDroppedItems().add(newItem);
    }

    private void moveInDirection(int u) {
        square = Square.positionToSquare(position);
        //enemy is in the center of a field
        double movementAmount = speed * Playing.getInstance().getGameSpeed() * slow;
        if (Math.abs((position.x - X_CENTERED_OFFSET) % FIELD_SIZE) < movementAmount && Math.abs((position.y - Y_CENTERED_OFFSET) % FIELD_SIZE) < movementAmount) {
            xAxisLocked = yAxisLocked = false;
            if (Pathfinding.getInstance().getDistanceField()[square.getX()][square.getY()] == 1) {
                //base reached
                Playing.getInstance().reduceHealth(progress);
                active = false;
                return;
            }
            distanceToTarget = Pathfinding.getInstance().getDistanceField()[square.getX()][square.getY()];
            //decide direction
            if (square.getX() + 1 < X_FIELDS && Pathfinding.getInstance().getDistanceField()[square.getX() + 1][square.getY()] < Pathfinding.getInstance().getDistanceField()[square.getX()][square.getY()])
            {
                roundYValue();
                yAxisLocked = true;
                position.x += movementAmount;
            }
            else if (square.getY() + 1 < Y_FIELDS && Pathfinding.getInstance().getDistanceField()[square.getX()][square.getY() + 1] < Pathfinding.getInstance().getDistanceField()[square.getX()][square.getY()])
            {
                roundXValue();
                xAxisLocked = true;
                position.y += movementAmount;
            }
            else if (square.getY() > 0 && Pathfinding.getInstance().getDistanceField()[square.getX()][square.getY() - 1] < Pathfinding.getInstance().getDistanceField()[square.getX()][square.getY()])
            {
                roundXValue();
                xAxisLocked = true;
                position.y -= movementAmount;
            }
            else if (square.getX() > 0 && Pathfinding.getInstance().getDistanceField()[square.getX() - 1][square.getY()] < Pathfinding.getInstance().getDistanceField()[square.getX()][square.getY()])
            {
                roundYValue();
                yAxisLocked = true;
                position.x -= movementAmount;
            }
        }
        //enemy is moving between two fields
        else if (position.x % FIELD_SIZE != X_CENTERED_OFFSET || position.y % FIELD_SIZE != Y_CENTERED_OFFSET) {
            Square[] neighbors = Square.getNeighbors(position);
            distanceToTarget(neighbors);
            //decide direction
            if (!xAxisLocked) {
                if (Pathfinding.getInstance().getDistanceField()[neighbors[0].getX()][neighbors[0].getY()] < Pathfinding.getInstance().getDistanceField()[neighbors[1].getX()][neighbors[1].getY()]) {
                    position.x += (neighbors[0].getX() - neighbors[1].getX()) * movementAmount;
                }
                else if (Pathfinding.getInstance().getDistanceField()[neighbors[0].getX()][neighbors[0].getY()] > Pathfinding.getInstance().getDistanceField()[neighbors[1].getX()][neighbors[1].getY()]) {
                    position.x += (neighbors[1].getX() - neighbors[0].getX()) * movementAmount;
                }
            }
            if (!yAxisLocked) {
                if (Pathfinding.getInstance().getDistanceField()[neighbors[0].getX()][neighbors[0].getY()] < Pathfinding.getInstance().getDistanceField()[neighbors[1].getX()][neighbors[1].getY()]) {
                    position.y += (neighbors[0].getY() - neighbors[1].getY()) * movementAmount;
                }
                else if (Pathfinding.getInstance().getDistanceField()[neighbors[0].getX()][neighbors[0].getY()] > Pathfinding.getInstance().getDistanceField()[neighbors[1].getX()][neighbors[1].getY()]) {
                    position.y += (neighbors[1].getY() - neighbors[0].getY()) * movementAmount;
                }
            }
        }
    }

    //calculates distanceToTarget in between fields
    private void distanceToTarget(Square[] neighbors) {

        if (!xAxisLocked) {
            distanceToTarget = Pathfinding.getInstance().getDistanceField()[neighbors[0].getX()][neighbors[0].getY()]
                    * (Math.abs(neighbors[1].getX() * FIELD_SIZE - position.x) / (double)FIELD_SIZE)
                    + Pathfinding.getInstance().getDistanceField()[neighbors[1].getX()][neighbors[1].getY()]
                    * (Math.abs(neighbors[0].getX() * FIELD_SIZE - position.x) / (double)FIELD_SIZE);
        }
        if (!yAxisLocked) {
            distanceToTarget = Pathfinding.getInstance().getDistanceField()[neighbors[0].getX()][neighbors[0].getY()]
                    * (Math.abs(neighbors[1].getY() * FIELD_SIZE - position.y) / (double)FIELD_SIZE)
                    + Pathfinding.getInstance().getDistanceField()[neighbors[1].getX()][neighbors[1].getY()]
                    * (Math.abs(neighbors[0].getY() * FIELD_SIZE - position.y) / (double)FIELD_SIZE);
        }
    }

    private void roundXValue() {
        double delta = position.x - (square.getX() * FIELD_SIZE + X_CENTERED_OFFSET);
        if (delta < FIELD_SIZE / 2.0) {
            position.x -= delta;
        }
        else {
            position.x += FIELD_SIZE - delta;
        }
    }

    private void roundYValue() {
        double delta = position.y - (square.getY() * FIELD_SIZE + Y_CENTERED_OFFSET);
        if (delta < FIELD_SIZE / 2.0) {
            position.y -= delta;
        }
        else {
            position.y += FIELD_SIZE - delta;
        }
    }

}

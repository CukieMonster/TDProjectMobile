package com.tdproject.gamestates;

import static com.tdproject.main.FieldParameters.X_FIELDS;
import static com.tdproject.main.FieldParameters.Y_FIELDS;

import com.tdproject.enemies.EnemyManager;
import com.tdproject.graphics.Background;
import com.tdproject.graphics.ButtonPanel;
import com.tdproject.graphics.Sprite;
import com.tdproject.graphics.Text;
import com.tdproject.inputs.MyEvent;
import com.tdproject.items.Item;
import com.tdproject.towers.TowerManager;
import com.tdproject.ui.PlayingButtons;

import lombok.Getter;
import java.util.LinkedList;

public class Playing extends GameState {

    @Getter
    private static final Playing instance = new Playing();
    @Getter
    private final ButtonPanel playingButtons;
    @Getter
    private int gameSpeed = 1;
    @Getter
    private int money = 50;

    @Getter
    private final Text[] infos = {new Text("Round: 0/10", 0, 30), new Text("Health: 100", 200, 30), new Text("Gold: " + money, 400, 30), new Text("Mode: DEFAULT", 800, 30)};
    private int health = 100;
    @Getter
    private final boolean[][] collisionMap = new boolean[X_FIELDS][Y_FIELDS];
    @Getter
    private final LinkedList<Item> droppedItems = new LinkedList<>();
    private final Sprite background;

    private Playing() {
        playingButtons = new ButtonPanel(1795, 950, 250, 180, PlayingButtons.buttons, 2);
        background = new Background();
    }

    @Override
    public void update(int u) {
        EnemyManager.getInstance().update(u);
        TowerManager.getInstance().update(u);
    }

    @Override
    public void render(Object o) {
        background.drawCentered(o);
        drawInfos(o);
        playingButtons.draw(o);
        EnemyManager.getInstance().draw(o);
        TowerManager.getInstance().draw(o);
        drawDroppedItems(o);
    }

    @Override
    public void mouseReleased(MyEvent e) {
        playingButtons.mouseReleased(e);
        TowerManager.getInstance().mouseReleased(e);
    }

    @Override
    public void mouseMoved(MyEvent e) {
        playingButtons.mouseMoved(e);
        TowerManager.getInstance().mouseMoved(e);
    }


    public boolean adjustMoney(int value) {
        if (money + value < 0) {
            return false;
        }
        money += value;
        infos[2].setString("Gold: " + money);
        return true;
    }

    public void reduceHealth(int value) {
        health -= value;
        infos[1].setString("Health: "+ health);
    }

    public void updateRound(int value) {
        infos[0].setString(String.format("Round: %d/10", value));
    }

    public void changeGameSpeed() {
        if (gameSpeed == 1) {
            gameSpeed = 2;
        }
        else if (gameSpeed == 2) {
            gameSpeed = 1;
        }
    }

    public void pause() {
        if (gameSpeed == 0) {
            gameSpeed = 1;
        }
        else {
            gameSpeed = 0;
        }
    }

    private void drawDroppedItems(Object o) {
        for (Item i : droppedItems) {
            if (i != null){
                // TODO
                //g.drawImage(i.getSprite(), 0, 0, null);
            }
        }
    }

    private void drawInfos(Object o) {
        for (Text t : infos) {
            t.draw(o);
        }
    }

}

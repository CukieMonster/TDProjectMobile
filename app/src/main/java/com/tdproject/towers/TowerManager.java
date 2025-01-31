package com.tdproject.towers;

import static com.tdproject.main.FieldParameters.X_FIELDS;
import static com.tdproject.main.FieldParameters.Y_FIELDS;
import static com.tdproject.towers.TowerParameters.COST;

import com.tdproject.enemies.Pathfinding;
import com.tdproject.gamestates.Playing;
import com.tdproject.graphics.ButtonPanel;
import com.tdproject.graphics.Text;
import com.tdproject.graphics.TextPanel;
import com.tdproject.inputs.MyEvent;
import com.tdproject.main.Square;
import com.tdproject.ui.BuildingButtons;

import lombok.Getter;
import lombok.Setter;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import javax.vecmath.Vector2d;

public class TowerManager implements UpgradeHandling {

    private static TowerManager instance;
    private final int maxTowers = X_FIELDS * Y_FIELDS;

    private final ButtonPanel buildingButtonPanel;
    private final ButtonPanel cancelBuildingButtonPanel;
    private final ButtonPanel deleteButtonPanel;
    @Getter
    private final ButtonPanel upgradeButtons;
    private final TextPanel towerInfo;

    private final Map<UpgradeType, Text> upgradeLevels = new EnumMap<>(UpgradeType.class);
    private final Tower[] towers = new Tower[maxTowers];
    private int towerNr;

    @Getter
    @Setter
    private Tower selectedTower;

    @Setter
    private TowerManagerMode mode = TowerManagerMode.DEFAULT;

    private TowerManager() {
        buildingButtonPanel = new ButtonPanel(1720, 490, 100, 980, BuildingButtons.BUILD_TOWER_BUTTON);
        cancelBuildingButtonPanel = new ButtonPanel(1720, 490, 100, 100, BuildingButtons.CANCEL_BUILDING_BUTTON);
        deleteButtonPanel = new ButtonPanel(1620, 100, 100, 100, BuildingButtons.DELETE_TOWER_BUTTON);
        upgradeButtons = new ButtonPanel(1720, 490, 100, 980, createUpgradeButtons());
        towerInfo = new TextPanel(1845, 490, 150, 980, createTowerInfoText());
        initializeUpgradeLevelTexts(upgradeLevels);
    }

    public void update(int u) {
        // tmp
        Playing.getInstance().getInfos()[3].setString("Mode: " + mode.toString());
        // ---
        if (selectedTower != null) {
            mode = TowerManagerMode.UPGRADING;
            for (UpgradeType upgradeType : UpgradeType.values()) {
                int level = Objects.requireNonNullElse(selectedTower.getUpgrades().get(upgradeType), 0);
                upgradeLevels.get(upgradeType).setString(level + ", " + selectedTower.getCost());
            }
        }
        for (Tower t : towers) {
            if (t != null && t.isActive()) {
                t.update(u);
            }
        }
    }

    public void enterBuildMode(int towerType) {
        for (int i = 0; i < towers.length; i++) {
            if (towers[i] == null) {
                towerNr = i;
            }
        }
        mode = TowerManagerMode.BUILDING;
        towers[towerNr] = new Tower(towerType);
    }

    public void cancelBuild() {
        towers[towerNr] = null;
        mode = TowerManagerMode.DEFAULT;
    }

    public void deleteTower() {
        for (int i = 0; i < towers.length; i++) {
            if (towers[i] == selectedTower) {
                Square square = towers[i].getSquare();
                Playing.getInstance().getCollisionMap()[square.getX()][square.getY()] = false;
                Pathfinding.getInstance().buildDistanceField();
                towers[i] = null;
                return;
            }
        }
    }

    private void moveTower(Vector2d mousePos) {
        towers[towerNr].setSquare(Square.positionToSquare(mousePos));
        if (checkSquare(towers[towerNr].getSquare())) {
            Vector2d pos = towers[towerNr].getSquare().squareToPosition();
            towers[towerNr].setPosition(pos);
            towers[towerNr].setVisible(true);
        } else {
            towers[towerNr].setVisible(false);
        }
    }

    private void buildTower() {
        // TODO ghost tower is out of position

        //TODO: check if tower can be placed, (no enemy in the way,) impossible path or enemy getting caught
        Square square = towers[towerNr].getSquare();
        if (square == null) {
            return;
        }
        if (!checkSquare(square)) {
            return;
        }
        if (!Playing.getInstance().adjustMoney(-COST[towers[towerNr].getTowerType()])) {
            // not enough money
            // TODO: visual feedback
            Playing.getInstance().getCollisionMap()[square.getX()][square.getY()] = false;
            return;
        }
        Playing.getInstance().getCollisionMap()[square.getX()][square.getY()] = true;
        if (!Pathfinding.getInstance().buildDistanceField()) {
            //Can't build tower here
            Playing.getInstance().getCollisionMap()[square.getX()][square.getY()] = false;
            return;
        }
        towers[towerNr].initBounds();
        towers[towerNr].setActive(true);
        mode = TowerManagerMode.DEFAULT;
    }

    private boolean checkSquare(Square square) {
        int x = square.getX();
        int y = square.getY();
        if (x >= 0 && x < X_FIELDS && y >= 0 && y < Y_FIELDS)
        {
            return !Playing.getInstance().getCollisionMap()[x][y];
        }
        return false;
    }

    public void mouseReleased(MyEvent e) {
        switch (mode) {
            case DEFAULT -> {
                buildingButtonPanel.mouseReleased(e);
                for (Tower tower : towers) {
                    if (tower == null || !tower.isActive()) {
                        continue;
                    }
                    tower.mouseReleased(e);
                }
            }
            case BUILDING -> {
                moveTower(new Vector2d(e.getX(), e.getY()));
                buildTower();
                cancelBuildingButtonPanel.mouseReleased(e);
            }
            case UPGRADING -> {
                deleteButtonPanel.mouseReleased(e);
                if (!upgradeButtons.mouseReleased(e)) {
                    selectedTower = null;
                    setMode(TowerManagerMode.DEFAULT);
                }
            }
        }
    }

    public void mouseMoved(MyEvent e) {
        switch (mode) {
            case DEFAULT -> buildingButtonPanel.mouseMoved(e);
            case BUILDING -> {
                cancelBuildingButtonPanel.mouseMoved(e);
                moveTower(new Vector2d(e.getX(), e.getY()));
            }
            case UPGRADING -> {
                deleteButtonPanel.mouseMoved(e);
                upgradeButtons.mouseMoved(e);
            }
        }
    }

    public void draw(Object o) {
        for (Tower tower : towers) {
            if (tower != null && tower.isVisible()) {
                tower.drawCentered(o);
                for (HomingMissile missile : tower.missiles) {
                    if (missile != null) {
                        missile.drawCentered(o);
                    }
                }
            }
        }
        switch (mode) {
            case DEFAULT -> buildingButtonPanel.draw(o);
            case BUILDING -> cancelBuildingButtonPanel.draw(o);
            case UPGRADING -> {
                toggleActiveUpgradeButtons();
                deleteButtonPanel.draw(o);
                upgradeButtons.draw(o);
                towerInfo.draw(o);
                upgradeLevels.forEach((u, t) -> t.draw(o));
            }
        }
    }

    public static TowerManager getInstance() {
        if (instance == null) {
            instance = new TowerManager();
        }
        return instance;
    }

}

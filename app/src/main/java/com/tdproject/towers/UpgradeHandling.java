package com.tdproject.towers;

import com.tdproject.gamestates.Playing;
import com.tdproject.graphics.ButtonPanel;
import com.tdproject.graphics.Text;
import com.tdproject.main.Modifiers;
import com.tdproject.ui.Button;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.vecmath.Vector2d;

import static com.tdproject.towers.TowerParameters.MAX_UPGRADE_LEVEL;

public interface UpgradeHandling {

    ButtonPanel getUpgradeButtons();
    Tower getSelectedTower();

    private void upgradeSelectedTower(UpgradeType upgradeType) {
        Integer upgradeLevel = getSelectedTower().getUpgrades().get(upgradeType);
        if (upgradeLevel != null && upgradeLevel >= MAX_UPGRADE_LEVEL) {
            return;
        }
        if (Playing.getInstance().adjustMoney(-getSelectedTower().getCost())) {
            getSelectedTower().upgrade(upgradeType);
        }
    }

    default Button[] createUpgradeButtons() {
        Button[] upgradeButtons = new Button[UpgradeType.values().length];
        int i = 0;
        for (UpgradeType upgradeType : UpgradeType.values()) {
            upgradeButtons[i++] = new Button(
                    upgradeType.spriteId,
                    b -> upgradeSelectedTower(upgradeType)
            );
        }
        return upgradeButtons;
    }

    default Text[] createTowerInfoText() {
        Text[] towerInfo = new Text[UpgradeType.values().length];
        int i = 0;
        for (UpgradeType upgradeType : UpgradeType.values()) {
            towerInfo[i] = new Text(upgradeType.displayName);
            i++;
        }
        return towerInfo;
    }

    default void initializeUpgradeLevelTexts(Map<UpgradeType, Text> upgradeLevels) {
        int i = 0;
        for (UpgradeType upgradeType : UpgradeType.values()) {
            Button button = getUpgradeButtons().getContent()[i++];
            Vector2d position = button.getPosition();
            upgradeLevels.put(upgradeType, new Text(
                    "0",
                    15,
                    Text.Color.RED,
                    (int) position.x + (button.getSprite().getWidth() / 2),
                    (int) position.y
            ));
        }
    }

    default void toggleActiveUpgradeButtons() {
        activateUpgradeButtons();

        int usedUpgradePaths = 0;
        List<UpgradeType> unusedUpgradeTypes = new ArrayList<>();
        for (UpgradeType upgradeType : UpgradeType.values()) {
            Integer upgradeLevel = getSelectedTower().getUpgrades().get(upgradeType);   // TODO: possible NPE
            if (upgradeLevel != null && upgradeLevel > 0) {
                usedUpgradePaths++;
            } else {
                unusedUpgradeTypes.add(upgradeType);
            }
        }
        if (usedUpgradePaths >= Modifiers.getMultipleUpgradePaths()) {
            deactivateUnusedUpgradeButtons(unusedUpgradeTypes);
        }
    }

    private void activateUpgradeButtons() {
        for (Button button : getUpgradeButtons().getContent()) {
            button.setActive(true);
        }
    }

    private void deactivateUnusedUpgradeButtons(List<UpgradeType> unusedUpgradeTypes) {
        for (UpgradeType upgradeType : unusedUpgradeTypes) {
            getUpgradeButtons().getContent()[upgradeType.ordinal()].setActive(false);
        }
    }
}

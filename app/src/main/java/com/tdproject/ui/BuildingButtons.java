package com.tdproject.ui;

import com.example.myapplication.R;
import com.tdproject.graphics.Sprite;
import com.tdproject.towers.TowerManager;

public class BuildingButtons {

    public static final Button CANCEL_BUILDING_BUTTON = new Button(
            Sprite.SpriteId.BUTTON_CANCEL_BUILD,
            i -> TowerManager.getInstance().cancelBuild(),
            false
    );

    public static final Button[] buttons = {
            CANCEL_BUILDING_BUTTON,
            new Button(
                    Sprite.SpriteId.BUTTON_BUILD_TOWER_1,
                    i -> TowerManager.getInstance().enterBuildMode(0)
            )
    };

    public static void setBuildMode(boolean buildMode) {
        for (Button button : buttons) {
            button.setVisible(!buildMode);
        }
        CANCEL_BUILDING_BUTTON.setVisible(buildMode);
    }

}

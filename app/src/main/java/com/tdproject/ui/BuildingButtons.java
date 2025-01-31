package com.tdproject.ui;

import com.tdproject.graphics.Sprite;
import com.tdproject.towers.TowerManager;

public class BuildingButtons {

    public static final Button BUILD_TOWER_BUTTON = new Button(
            Sprite.SpriteId.BUTTON_BUILD_TOWER_1,
            i -> TowerManager.getInstance().enterBuildMode(0)
    );

    public static final Button CANCEL_BUILDING_BUTTON = new Button(
            Sprite.SpriteId.BUTTON_CANCEL_BUILD,
            i -> TowerManager.getInstance().cancelBuild()
    );

    public static final Button DELETE_TOWER_BUTTON = new Button(
            Sprite.SpriteId.BUTTON_DELETE_TOWER,
            i -> TowerManager.getInstance().deleteTower()
    );

}

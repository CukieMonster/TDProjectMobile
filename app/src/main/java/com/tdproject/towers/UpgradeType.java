package com.tdproject.towers;

import com.tdproject.graphics.Sprite;

public enum UpgradeType {

    // TODO: do not allow splash and aoe
    DAMAGE(Sprite.SpriteId.UPGRADE_DAMAGE),
    SPEED(Sprite.SpriteId.UPGRADE_SPEED),
    RANGE(Sprite.SpriteId.UPGRADE_RANGE),
    SPLASH(Sprite.SpriteId.UPGRADE_SPLASH),
    SLOW(Sprite.SpriteId.UPGRADE_SLOW),
    DAMAGE_OVER_TIME(Sprite.SpriteId.UPGRADE_DOT),
    AREA_OF_EFFECT(Sprite.SpriteId.UPGRADE_AOE);
    // MORE_MONEY_PER_KILL ???

    final Sprite.SpriteId spriteId;

    UpgradeType(Sprite.SpriteId spriteId) {
        this.spriteId = spriteId;
    }

}

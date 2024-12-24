package com.tdproject.towers;

import com.tdproject.graphics.Sprite;

public enum UpgradeType {

    // TODO: do not allow splash and aoe
    DAMAGE(Sprite.SpriteId.UPGRADE_DAMAGE, "Damage"),
    SPEED(Sprite.SpriteId.UPGRADE_SPEED, "Attack Speed"),
    RANGE(Sprite.SpriteId.UPGRADE_RANGE, "Attack Range"),
    SPLASH(Sprite.SpriteId.UPGRADE_SPLASH, "Splash Damage"),
    SLOW(Sprite.SpriteId.UPGRADE_SLOW, "Slow"),
    DAMAGE_OVER_TIME(Sprite.SpriteId.UPGRADE_DOT, "Damage over Time"),
    AREA_OF_EFFECT(Sprite.SpriteId.UPGRADE_AOE, "Area of Effect");
    // MORE_MONEY_PER_KILL ???

    final Sprite.SpriteId spriteId;
    final String displayName;

    UpgradeType(Sprite.SpriteId spriteId, String displayName) {
        this.spriteId = spriteId;
        this.displayName = displayName;
    }

}

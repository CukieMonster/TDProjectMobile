package com.tdproject.graphics;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.example.myapplication.R;
import com.tdproject.main.GameWindow;

import javax.vecmath.Vector2d;

import lombok.Getter;
import lombok.Setter;

// This class has specific Android functionality
public abstract class Sprite {

    protected Context context;

    //public enum Type { BACKGROUND, TOWER, MISSILE, ENEMY, BUTTON, ITEM }

    public final static int TOWER_WIDTH = 64;
    public final static int TOWER_HEIGHT = 64;
    public final static int MISSILE_WIDTH = 5;
    public final static int MISSILE_HEIGHT = 5;
    public final static int ENEMY_WIDTH = 64;
    public final static int ENEMY_HEIGHT = 64;
    public final static int ITEM_WIDTH = 64;
    public final static int ITEM_HEIGHT = 64;
    public final static int BUTTON_WIDTH = 64;
    public final static int BUTTON_HEIGHT = 64;
    public final static int MENU_BUTTON_WIDTH = 200;
    public final static int MENU_BUTTON_HEIGHT = 100;
    public final static int BACKGROUND_WIDTH = 1920;
    public final static int BACKGROUND_HEIGHT = 1080;

    public enum SpriteId {

        BACKGROUND(null, R.drawable.background),

        BUTTON_MAIN_MENU_PLAY(null, R.drawable.play_default),
        BUTTON_MAIN_MENU_INVENTORY(null, R.drawable.inventory_default),
        BUTTON_MAIN_MENU_SETTINGS(null, R.drawable.settings_default),
        BUTTON_MAIN_MENU_QUIT(null, R.drawable.quit_default),

        BUTTON_FAST_FORWARD(null, R.drawable.fast_forward_default),
        BUTTON_SKIP(null, R.drawable.skip_default),
        BUTTON_PAUSE(null, R.drawable.pause_default),
        BUTTON_CANCEL_BUILD(null, R.drawable.cancel_building_default),
        BUTTON_BUILD_TOWER_1(null, R.drawable.build_tower_1_default),
        BUTTON_DELETE_TOWER(null, R.drawable.delete_default),

        TOWER_1(null, R.drawable.tower_blue_0_default),
        MISSILE(null, R.drawable.homing_missile),

        UPGRADE_DAMAGE(null, R.drawable.damage),
        UPGRADE_SPEED(null, R.drawable.damage),
        UPGRADE_RANGE(null, R.drawable.damage),
        UPGRADE_SPLASH(null, R.drawable.damage),
        UPGRADE_SLOW(null, R.drawable.damage),
        UPGRADE_DOT(null, R.drawable.damage),
        UPGRADE_AOE(null, R.drawable.damage),

        ENEMY_0(null, R.drawable.enemy_0),
        ENEMY_1(null, R.drawable.enemy_1),
        ENEMY_2(null, R.drawable.enemy_2),
        ENEMY_3(null, R.drawable.enemy_3),
        ENEMY_4(null, R.drawable.enemy_4),

        ITEM_TOWER(null, R.drawable.tower),
        ITEM_BASE(null, R.drawable.base),
        ITEM_ENEMY(null, R.drawable.enemy),

        MISSING_SPRITE(null, R.drawable.missing_sprite);

        final String path;
        final Integer resourceId;

        SpriteId(String path, Integer resourceId) {
            this.path = path;
            this.resourceId = resourceId;
        }

    }
//    public final static int BUTTON_IDS[][] = {
//            { R.drawable.play_default, R.drawable.play_hover, R.drawable.play_inactive },
//            { R.drawable.build_tower_1_default, R.drawable.build_tower_1_hover, R.drawable.build_tower_1_inactive },
//            { R.drawable.play_default }
//    };

    @Setter
    @Getter
    protected Vector2d position;    // position is bottom left for enemy, tower, item, but is the center of button, homing missile, background
    @Getter
    protected Bitmap sprite;

//    public void draw(Object g) {
//        ((Graphics)g).drawImage(sprite, (int) centerPosition.x, (int) centerPosition.y, null);
//    }

    public void drawCentered(Object g) {
        // TODO: fix drawing
//        System.out.println("Drawing at position ("+ position.x + ", " + position.y + ")");
        float x = (float) (position.x - sprite.getWidth() / 2);
        float y = (float) (position.y - sprite.getHeight() / 2);
        ((Canvas)g).drawBitmap(sprite, x, y, null);
    }

    protected void loadSprite(SpriteId id, int width, int height) {
        sprite = loadSpriteFromResources(id, width, height);
    }

    protected Bitmap loadSpriteFromResources(SpriteId id, int width, int height) {
        Bitmap sprite;
        if (id == null) {
            sprite = BitmapFactory.decodeResource(GameWindow.context.getResources(), SpriteId.MISSING_SPRITE.resourceId);
        } else {
            sprite = BitmapFactory.decodeResource(GameWindow.context.getResources(), id.resourceId);
        }
        return Bitmap.createScaledBitmap(sprite, width, height, false);
    }
}

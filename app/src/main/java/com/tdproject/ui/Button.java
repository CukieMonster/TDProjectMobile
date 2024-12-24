package com.tdproject.ui;

import com.tdproject.graphics.Sprite;
import com.tdproject.inputs.MyEvent;

import android.graphics.Bitmap;
import lombok.Getter;
import java.util.EnumMap;
import java.util.Map;
import java.util.function.Consumer;

// This class has specific Android functionality
public class Button extends Sprite {

    private final Map<ButtonVariant, Bitmap> sprites = new EnumMap<>(ButtonVariant.class);
    protected Consumer<Button> action;
    private boolean mouseOver;
    private boolean mousePressed;
    private Rectangle bounds;
    @Getter
    private boolean visible = false;
    @Getter
    private boolean active = true;

    public Button(SpriteId spriteId, Consumer<Button> action) {
        this(spriteId, action, true, BUTTON_WIDTH, BUTTON_HEIGHT);
    }

    public Button(SpriteId spriteId, Consumer<Button> action, int width, int height) {
        this(spriteId, action, true, width, height);
    }

    public Button(SpriteId spriteId, Consumer<Button> action, boolean defaultState) {
        this(spriteId, action, defaultState, BUTTON_WIDTH, BUTTON_HEIGHT);
    }

    public Button(SpriteId spriteId, Consumer<Button> action, boolean defaultState, int width, int height) {
        this.visible = defaultState;
        this.action = action;

        loadSprite(spriteId, width, height);
    }

    public void initBounds() {
        bounds = new Rectangle((int) position.x - sprite.getWidth() / 2, (int) position.y - sprite.getHeight() / 2, sprite.getWidth(), sprite.getHeight());
    }

    public boolean mouseReleased(MyEvent e) {
        if (visible & active && isIn(e)) {
            activate();
            return true;
        }
        return false;
    }

    private boolean isIn(MyEvent e) {
        return bounds.contains(e.getX(), e.getY());
    }

    private void activate() {
        action.accept(null);
    }

    public void mouseMoved(MyEvent e) {
        boolean mouseOver = visible && isIn(e);
        if (this.mouseOver != mouseOver) {
            this.mouseOver = mouseOver;
            //updateSprite();
        }
    }

    // TODO implement different sprites for button pressed and hovered
//    @Override
//    protected void loadSprite(int buttonId) {
//        for (ButtonVariant variant : ButtonVariant.values()) {
//            switch (variant) {
//                case DEFAULT -> sprites.put(variant, loadSpriteFromResources(R.drawable.))
//            }
//            sprites.put(variant, loadSpriteFromResources(imagePath + variant.path));
//        }
//        sprite = sprites.get(ButtonVariant.DEFAULT);
//    }

    public void setVisible(boolean visible) {
        this.visible = visible;
        //updateSprite();
    }

    public void setActive(boolean active) {
        this.active = active;
        //updateSprite();
    }

    private void updateSprite() {
        if (visible && active && mouseOver) {
            System.out.println("Use sprite " + ButtonVariant.HOVER);
            sprite = sprites.get(ButtonVariant.HOVER);
        } else if (visible && active) {
            System.out.println("Use sprite " + ButtonVariant.DEFAULT);
            sprite = sprites.get(ButtonVariant.DEFAULT);
        } else {
            System.out.println("Use sprite " + ButtonVariant.INACTIVE);
            sprite = sprites.get(ButtonVariant.INACTIVE);
        }
    }

    private enum ButtonVariant {

        DEFAULT("_default.png"),
        HOVER("_hover.png"),
        INACTIVE("_inactive.png");

        final String path;

        ButtonVariant(String path) {
            this.path = path;
        }
    }

}

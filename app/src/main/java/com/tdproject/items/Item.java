package com.tdproject.items;

import com.tdproject.graphics.Sprite;

import java.util.Random;

public class Item extends Sprite {
    private int level;
    private ItemParameters.Rarity rarity;
    private ItemParameters.ItemType itemType;
    private ItemParameters.Attribute[] attributes;
    private int[] maxModifierValues;
    private int[] modifierValues;
//    private BufferedImage sprite;

    private Random random = new Random();

    public Item(int lv, ItemParameters.Rarity r) {
        level = getLevel(lv);
        rarity = r;
        itemType = ItemParameters.ItemType.values()[random.nextInt(ItemParameters.ItemType.values().length)];
        attributes = new ItemParameters.Attribute[rarity.ordinal() + 1];
        for (int i = 0; i < attributes.length; i++) {
            attributes[i] = getAttribute();
        }
        maxModifierValues = new int[attributes.length];
        for (int i = 0; i < maxModifierValues.length; i++) {
            maxModifierValues[i] = getMaxModifierValue(i, lv);
        }
        modifierValues = new int[attributes.length];
        for (int i = 0; i < modifierValues.length; i++) {
            modifierValues[i] = rerollAttribute(i);
        }
        loadSprite(switch (itemType) {
            case Tower -> SpriteId.ITEM_TOWER;
            case Base -> SpriteId.ITEM_BASE;
            case Enemy -> SpriteId.ITEM_ENEMY;
        }, Sprite.ITEM_WIDTH, Sprite.ITEM_HEIGHT);
        //loadImage();
    }

    public int rerollAttribute(int index) {
        //rolls value between 0.8 and 1.0 times max value
        if (attributes[index] == ItemParameters.Attribute.Cost) {
            return -(int)((1 - random.nextInt(20) / 100F) * maxModifierValues[index]);
        }
        else {
            return (int)((1 - random.nextInt(20) / 100F) * maxModifierValues[index]);
        }
    }

    private int getLevel(int lv) {
        if (lv < 1) {
            return 1;
        }
        else if (lv < ItemParameters.MAX_LEVEL) {
            return lv;
        }
        else {
            return ItemParameters.MAX_LEVEL;
        }
    }

    private ItemParameters.Attribute getAttribute() {
        if (itemType != ItemParameters.ItemType.Base) {
            return ItemParameters.Attribute.values()[random.nextInt(ItemParameters.Attribute.values().length - 1)];
        }
        else {
            return ItemParameters.Attribute.Health;
        }
    }

    private int getMaxModifierValue(int index, int lv) {
        return (int)(((double)lv) / ItemParameters.MAX_LEVEL * ItemParameters.MAX_POSSIBLE_MODIFIER_VALUE[attributes[index].ordinal()]);
    }

//    private void loadImage() {
//        InputStream is;
//        is = getClass().getResourceAsStream("/items/" + itemType + ".png");
//        try {
//            sprite = ImageIO.read(is);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    // Getters and setters
//    public BufferedImage getSprite() {
//        return sprite;
//    }
}

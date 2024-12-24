package com.tdproject.main;

import lombok.Getter;

public class Modifiers {

    private Modifiers() {

    }
    @Getter
    private static int multipleUpgradePaths = 1;
    private static int dropRate = 100;             //in %, will be multiplied with Enemy.value
    private static int rareDropRate;
    private static int legendaryDropRate;
    private static int baseHealth;
    private static int startMoney;

    private static double[][] towerModifiers;
    private static double[] attackBoost;
    private static double[] attackSpeedBoost;
    private static double[] attackRangeBoost;
    private static double[] towerCostReduction;

    public static void readSaveFile() {
        //TODO
    }

    public static void updateItemsModifiers() {
        baseHealth = 100;
        //reset
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                towerModifiers[i][j] = 1;
            }
        }
        for (int i = 0; i < 3; i++) {
            attackBoost[i] = 1;
            attackSpeedBoost[i] = 1;
            attackRangeBoost[i] = 1;
            towerCostReduction[i] = 1;
        }
        //TODO: active main.tdproject.items
    }

    // Getters and setters
    public static int getDropRate() {
        return dropRate;
    }

    public static int getRareDropRate() {
        return rareDropRate;
    }

    public static int getLegendaryDropRate() {
        return legendaryDropRate;
    }
}

package com.tdproject.towers;

public final class TowerParameters {

    private TowerParameters() {

    }

    public static final int MAX_UPGRADE_LEVEL = 7;
    public static final double DAMAGE_UPGRADE = 1.2;
    public static final double SPEED_UPGRADE = 0.05;
    public static final double RANGE_UPGRADE = 1;
    public static final double SPLASH_UPGRADE = 0.1;
    public static final int SLOW_UPGRADE = 10;
    public static final int DOT_UPGRADE = 1;

    public static final int[] DAMAGE = { 500, 1000, 0 };
    public static final double[] ATTACK_SPEED = { 0.5, 1, 0 };    //delay between attacks in seconds
    public static final double SLOW = 0.5F;
    public static final int[] RANGE = { 1, 2, 1 };
    public static final int[] COST = { 10, 15, 15 };
    public static final double[] MISSILE_SPEED = { 10, 5 , 0 };
}

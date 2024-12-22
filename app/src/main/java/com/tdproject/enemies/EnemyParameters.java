package com.tdproject.enemies;

public final class EnemyParameters {

    private EnemyParameters() {

    }
    public static final int WAVE_GROWTH = 3;
    public static final int SPAWN_INTERVAL = 60;    //updates between enemy spawns
    public static final int WAVE_INTERVAL = 60 * 5; //updates between waves
    public static final int[] VALUE = { 2, 5, 10, 15, 20 };
    public static final int[] MAX_HP = { 1000, 2000, 5000, 10000, 10000 };
    public static final int[] SPEEDS = { 1, 2, 3 };
    public static final int[] SPEED_TIER = { 1, 2, 0, 0, 1 };
    public static final int[] PROGRESS = { 1, 2, 3, 4, 5 };
}

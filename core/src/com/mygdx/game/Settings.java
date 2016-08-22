package com.mygdx.game;

/**
 * Created by Jonneh on 04/07/2016.
 */
public class Settings {
    public final static int GAME_HEIGHT = 320;
    public final static int GAME_WIDTH = 480;

    public final static int EASY_MODE = 5;
    public final static int MEDIUM_MODE = 5;
    public final static int HARD_MODE = 1;

    public static boolean soundEnabled;
    public static boolean musicEnabled;
    public static int difficulty = EASY_MODE;

    static{
        soundEnabled = true;
        musicEnabled = true;
    }

    static void setSound(boolean b){
        soundEnabled = b;
    }

    static void setMusic(boolean b){
        musicEnabled = b;
    }
}

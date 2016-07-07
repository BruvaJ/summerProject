package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Jonneh on 04/07/2016.
 */
public class Assets {
   public static Texture mainMenu;
   public static TextureAtlas mainMenuAtlas;
   public static TextureRegion backgroundRegion;
   public static TextureRegion quizRegion;
   public static TextureRegion playRegion;

   public static Sound clickSound;

   public static String englishVocab[];
   public static String japaneseVocab[];

    // FileIO isn't static meaning load can't be static... workaround?
    public static FileIO fileIO = new FileIO();


    public static Texture loadTexture(String file){
        return new Texture(Gdx.files.internal(file));
    }

    // need to use an assetmanager here
    // http://stackoverflow.com/questions/32448088/how-do-i-make-textbuttons-using-libgdx/32452856#32452856
    public static void load() {
        JapaneseGenerator jg = new JapaneseGenerator();

        mainMenuAtlas = new TextureAtlas(Gdx.files.internal("mainMenu.txt"));
        backgroundRegion = mainMenuAtlas.findRegion("background");
        quizRegion = mainMenuAtlas.findRegion("quiz");
        playRegion = mainMenuAtlas.findRegion("play");

        clickSound = Gdx.audio.newSound(Gdx.files.internal("click.wav"));

        englishVocab = fileIO.readFile("englishVocab.txt");
        japaneseVocab = fileIO.readFile("japaneseVocab.txt");
    }

    public static void playSound(Sound sound) {
        if(Settings.soundEnabled)
            sound.play(1);
    }
}


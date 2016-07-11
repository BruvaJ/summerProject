package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;

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

   public static ArrayList<VocabItem> vocab = new ArrayList<VocabItem>();
   public static BitmapFont jFont;
   public static BitmapFont eFont;

    public static Texture loadTexture(String file){
        return new Texture(Gdx.files.internal(file));
    }

    // need to use an assetmanager here　once loading in lots of data
    // http://stackoverflow.com/questions/32448088/how-do-i-make-textbuttons-using-libgdx/32452856#32452856
    public static void load() {

        mainMenuAtlas = new TextureAtlas(Gdx.files.internal("mainMenu.txt"));
        backgroundRegion = mainMenuAtlas.findRegion("background");
        quizRegion = mainMenuAtlas.findRegion("quiz");
        playRegion = mainMenuAtlas.findRegion("play");

        clickSound = Gdx.audio.newSound(Gdx.files.internal("click.wav"));

        // at a later date there will be an extra file "Count" that may or may not be drawn. Different constructor can be used.
        String[] eVocab = FileIO.readFile("eVocab.txt").split("\n");
        String[] jVocab = FileIO.readFile("jVocab.txt").split("\n");
        createVocabItems(eVocab, jVocab);

        jFont = JapaneseGenerator.generate(jVocab);
        eFont = new BitmapFont();
    }

    // should this be a class?
    private static void createVocabItems(String[] eVocab, String[] jVocab) {
        // error checking?  if eVocab.length == jVocab.length
        for(int i = 0; i < eVocab.length; i++){
            vocab.add(new VocabItem(eVocab[i], jVocab[i], i));
        }
    }


    public static void playSound(Sound sound) {
        if(Settings.soundEnabled)
            sound.play(1);
    }
}


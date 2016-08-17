package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.StringBuilder;

import java.util.ArrayList;

/**
 * Created by Jonneh on 04/07/2016.
 */
public class Assets {
   public static TextureAtlas mainMenuAtlas;
   public static TextureRegion backgroundRegion;
   public static TextureRegion quizRegion;
   public static TextureRegion playRegion;

   public static Sound clickSound;

   public static ArrayList<QuizItem> vocab = new ArrayList<QuizItem>();
   public static BitmapFont jFont;
   public static BitmapFont eFont;
   public static final Skin skin = new Skin();
    public static Texture mySoldier;
    public static Texture enemySoldier;
    public static Texture lifeCounter;

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

        mySoldier = new Texture(Gdx.files.internal("crusader.png"));
        enemySoldier = new Texture(Gdx.files.internal("samurai.png"));
        lifeCounter = new Texture(Gdx.files.internal("heart.png"));

        // at a later date there will be an extra file "Count" that may or may not be drawn. Different constructor can be used.
        String[] eVocab = FileIO.readFile("eVocab.txt").split("\n");
        String[] jVocab = FileIO.readFile("jVocab.txt").split("\n");
        String[] vocabCount = FileIO.readFile("count.txt", eVocab.length).split("\n");
        createVocabItems(eVocab, jVocab, vocabCount);

        jFont = JapaneseGenerator.generate(jVocab);
        eFont = new BitmapFont();

        makeSkin();
    }

    private static void makeSkin() {
        TextButton.TextButtonStyle jButton = new TextButton.TextButtonStyle();
        skin.add("jFont", jFont, BitmapFont.class);
        skin.addRegions(new TextureAtlas(Gdx.files.internal("uiskin.atlas")));
        jButton.font = skin.getFont("jFont");
        jButton.up = skin.newDrawable("default-round");
        jButton.down = skin.newDrawable("default-round-down");

        skin.add("jButton", jButton, TextButton.TextButtonStyle.class);
        FileHandle fileHandle = Gdx.files.internal("uiskin.json");

        // unecessary code
        FileHandle atlasFile = fileHandle.sibling("uiskin.atlas");

        if (atlasFile.exists()) {
            skin.addRegions(new TextureAtlas(atlasFile));
        }
        skin.load(fileHandle);
    }

    // should this be a class?
    private static void createVocabItems(String[] eVocab, String[] jVocab, String[] vocabCount) {
        // error checking?  if eVocab.length == jVocab.length
        for(int i = 0; i < eVocab.length; i++){
            vocab.add(new QuizItem(eVocab[i], jVocab[i], Integer.parseInt(vocabCount[i]), i));
        }
    }

    public static void playSound(Sound sound) {
        if(Settings.soundEnabled)
            sound.play(1);
    }
}


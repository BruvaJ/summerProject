package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import java.util.ArrayList;


/**
 * Created by Jonneh on 04/07/2016.
 */

class MainMenu extends ScreenAdapter {
    private final Stage stage = new Stage(new StretchViewport(Settings.GAME_WIDTH, Settings.GAME_HEIGHT));
    private final ArrayList<Actor> mainButtons = new ArrayList<Actor>();
    private final ArrayList<Actor> optionsButtons = new ArrayList<Actor>();
    private final ArrayList<Actor> vocabButtons = new ArrayList<Actor>();

    private final static int TAB_HEIGHT = 50;

    private LanguageApp game;

    MainMenu(LanguageApp game){
        this.game = game;
        create();
    }

    private void create() {
        Gdx.input.setInputProcessor(stage);
        createMainButtons();
        createOptionButtons();
        createVocabButtons();
        createTab();
        addButtons(vocabButtons);
        addButtons(mainButtons);
        addButtons(optionsButtons);

        setVisibility(mainButtons, optionsButtons, vocabButtons);
    }

    private void createVocabButtons() {
        Table contents = new Table();
        contents.defaults().height((Settings.GAME_HEIGHT - TAB_HEIGHT) / 10);
        contents.defaults().width(Settings.GAME_WIDTH / 2);

        for (int i = 0; i< Assets.vocab.size(); i++
             ) {
            contents.add(new TextButton(Assets.vocab.get(i).geteVocab(), Assets.skin));
            contents.add(new TextButton(Assets.vocab.get(i).getjVocab(), Assets.skin, "jButton"));
            contents.row();
        }


        final ScrollPane scroller = new ScrollPane(contents);
        final Table frame = new Table();
        frame.setFillParent(true);
        frame.padBottom(TAB_HEIGHT);
        frame.add(scroller);
        vocabButtons.add(frame);
    }

    private void createOptionButtons() {
        final Button soundButton = new CheckBox("Sound", Assets.skin);
        soundButton.setChecked(Settings.soundEnabled);
        final Button musicButton = new CheckBox("Music", Assets.skin);
        musicButton.setChecked(Settings.musicEnabled);


        soundButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event,x,y);
                if(Settings.soundEnabled)
                    Settings.setSound(false);
                else if(!Settings.soundEnabled)
                    Settings.setSound(true);
            }
        });

        musicButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event,x,y);
                if(Settings.musicEnabled)
                    Settings.setMusic(false);
                else if(!Settings.musicEnabled)
                    Settings.setMusic(true);
            }
        });
        soundButton.setPosition(Settings.GAME_WIDTH/5, Settings.GAME_HEIGHT - Settings.GAME_HEIGHT / 3);
        musicButton.setPosition(Settings.GAME_WIDTH/5, soundButton.getY() - soundButton.getHeight() * 2);

        optionsButtons.add(soundButton);
        optionsButtons.add(musicButton);
    }

    private void createTab() {

        final TextButton mainMenuButton = new TextButton("Main", Assets.skin);
        final TextButton vocabButton = new TextButton("Vocab", Assets.skin);
        final TextButton optionsButton = new TextButton("Options", Assets.skin);

        Table tabMenu = new Table();
        tabMenu.defaults().height(TAB_HEIGHT);
        // should be list. add with loop (or sthg similar...)
        // then, the division by three should  perhaps be changed for a check of array size? Easier maintenance...
        tabMenu.defaults().width(Settings.GAME_WIDTH/3);
        tabMenu.left().bottom();
        tabMenu.add(mainMenuButton);
        tabMenu.add(vocabButton);
        tabMenu.add(optionsButton);
        stage.addActor(tabMenu);


        optionsButton.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                menuSelection();
                setVisibility(optionsButtons, mainButtons, vocabButtons);
            }
        });

        mainMenuButton.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                menuSelection();
                setVisibility(mainButtons, optionsButtons, vocabButtons);
            }
        });

        vocabButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                menuSelection();
                setVisibility(vocabButtons, mainButtons, optionsButtons);
            }
        });
    }

    private void menuSelection() {
        Assets.playSound(Assets.selection);
    }

    private void setVisibility(ArrayList<Actor>onButtons, ArrayList<Actor> offButtons1, ArrayList<Actor> offButtons2) {
        for (Actor b:onButtons
                ) {
            b.setVisible(true);
        }
        for (Actor b:offButtons1
             ) {
            b.setVisible(false);
        }
        for (Actor b:offButtons2
             ) {
            b.setVisible(false);
        }
    }

    private void addButtons(ArrayList<Actor> mainButtons) {
        for (Actor button: mainButtons
             ) {
            stage.addActor(button);
        }
    }

    private void createMainButtons() {
        final TextButton quizButton = new TextButton("QUIZ", Assets.skin);
        final TextButton playButton = new TextButton("PLAY", Assets.skin);
        final TextButton easyButton = new CheckBox("Easy", Assets.skin);
        easyButton.setChecked(true);
        Settings.difficulty = Settings.EASY_MODE;
        final Button mediumButton = new CheckBox("Medium", Assets.skin);
        final Button hardButton = new CheckBox("Hard", Assets.skin);

        easyButton.setPosition(300, 170);
        mediumButton.setPosition(300, 150);
        hardButton.setPosition(300, 130);
        quizButton.setPosition(100, 200);
        playButton.setPosition(300, 200);

        quizButton.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                dispose();
                modeSelectSound();
                game.setScreen(new QuizGame(game));
            }
        });

        playButton.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                dispose();
                modeSelectSound();
                game.setScreen(new BattleGame(game, Settings.difficulty));
            }
        });
        easyButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event,x,y);
                hardButton.setChecked(false);
                mediumButton.setChecked(false);
                Settings.difficulty = Settings.EASY_MODE;
            }
        });
        mediumButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event,x,y);
                easyButton.setChecked(false);
                hardButton.setChecked(false);
                Settings.difficulty = Settings.MEDIUM_MODE;
            }
        });
        hardButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event,x,y);
                easyButton.setChecked(false);
                mediumButton.setChecked(false);
                Settings.difficulty = Settings.HARD_MODE;
            }
        });

        mainButtons.add(easyButton);
        mainButtons.add(mediumButton);
        mainButtons.add(hardButton);
        mainButtons.add(quizButton);
        mainButtons.add(playButton);
    }

    private void modeSelectSound() {
        Assets.playSound(Assets.selectMode);
    }

    public void render (float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void show() {

    }

    @Override
    public void resize (int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose () {
        stage.dispose();
    }


    @Override
    public void pause() {

    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {

    }
}
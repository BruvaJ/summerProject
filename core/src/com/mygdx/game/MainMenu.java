package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import java.util.ArrayList;


/**
 * Created by Jonneh on 04/07/2016.
 */

public class MainMenu extends ScreenAdapter {
    private final Skin skin = new Skin();
    private final Stage stage = new Stage(new StretchViewport(Settings.GAME_WIDTH, Settings.GAME_HEIGHT));
    private final ArrayList<Actor> mainButtons = new ArrayList<Actor>();
    private final ArrayList<Actor> optionsButtons = new ArrayList<Actor>();
    private final ArrayList<Actor> vocabButtons = new ArrayList<Actor>();

    private final static int TAB_HEIGHT = 50;

    LanguageApp game;

    public MainMenu(LanguageApp game){
        create();
        this.game = game;
    }

    private void create() {
        Gdx.input.setInputProcessor(stage);

        //workaround for json problem. Not to stay here. New class, assets, or japaneseGenerator?
        // can programatically add all details to button before putting into json
        TextButtonStyle jButton = new TextButtonStyle();
        skin.add("jFont", Assets.jFont, BitmapFont.class);
        jButton.font = skin.getFont("jFont");
        skin.add("jButton", jButton, TextButtonStyle.class);
        FileHandle fileHandle = Gdx.files.internal("uiskin.json");
        FileHandle atlasFile = fileHandle.sibling("uiskin.atlas");

        if (atlasFile.exists()) {
            skin.addRegions(new TextureAtlas(atlasFile));
        }
        skin.load(fileHandle);

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

        for (int i = 0; i< Assets.englishVocab.length; i++
             ) {
            contents.add(new TextButton(Assets.englishVocab[i], skin));
            contents.add(new TextButton(Assets.japaneseVocab[i], skin, "jButton"));
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
        final TextButton testButton = new TextButton("Test", skin);
        testButton.setPosition(Settings.GAME_WIDTH/2 -50,Settings.GAME_HEIGHT/2 - 50);
        optionsButtons.add(testButton);
    }

    private void createTab() {

        final TextButton mainMenuButton = new TextButton("Main", skin);
        final TextButton vocabButton = new TextButton("Vocab", skin);
        final TextButton optionsButton = new TextButton("Options", skin);

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
                setVisibility(optionsButtons, mainButtons, vocabButtons);
            }
        });

        mainMenuButton.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                setVisibility(mainButtons, optionsButtons, vocabButtons);
            }
        });

        vocabButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                setVisibility(vocabButtons, mainButtons, optionsButtons);
            }
        });
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
        final TextButton quizButton = new TextButton("QUIZ", skin);
        final TextButton playButton = new TextButton("PLAY", skin);
        quizButton.setPosition(100, 200);
        playButton.setPosition(300, 200);

        // convert x and y to absolute position?
        quizButton.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                //System.out.println("Clicked! Is checked: " + button.isChecked());
                dispose();
                game.setScreen(new QuizGame(game));
            }
        });

        playButton.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                //System.out.println("Clicked! Is checked: " + button.isChecked());
                playButton.setText("Starting new game");
            }
        });

        mainButtons.add(quizButton);
        mainButtons.add(playButton);
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
        skin.dispose();
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
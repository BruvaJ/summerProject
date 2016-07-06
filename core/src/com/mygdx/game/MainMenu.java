package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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

public class MainMenu implements Screen {
    Skin skin;
    Stage stage;
    Table tabMenu;
    final ArrayList<Actor> mainButtons = new ArrayList<Actor>();
    final ArrayList<Actor> optionsButtons = new ArrayList<Actor>();
    final ArrayList<Actor> vocabButtons = new ArrayList<Actor>();

    final static int TAB_HEIGHT = 50;

    FileIO fileIO = new FileIO();
    private String[] vocab = fileIO.readFile("vocab.txt");

    LanguageApp game;

    public MainMenu(LanguageApp game){
        create();
        this.game = game;
    }

    public void create() {
        stage = new Stage(new StretchViewport(Settings.GAME_WIDTH, Settings.GAME_HEIGHT));
        Gdx.input.setInputProcessor(stage);

        skin = new Skin();

        // to be changed to my own file
        Pixmap pixmap = new Pixmap(100, 100, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.GREEN);
        pixmap.fill();

        skin.add("white", new Texture(pixmap));

        BitmapFont bfont = new BitmapFont();
        skin.add("default", bfont);

        // should seperate into class of its own?
        TextButtonStyle playButtonStyle = new TextButtonStyle();
        playButtonStyle.up = skin.newDrawable("white", Color.DARK_GRAY);
        playButtonStyle.down = skin.newDrawable("white", Color.DARK_GRAY);
        playButtonStyle.checked = skin.newDrawable("white", Color.BLUE);
        playButtonStyle.over = skin.newDrawable("white", Color.LIGHT_GRAY);

        playButtonStyle.font = skin.getFont("default");


        skin.add("default", playButtonStyle);

        createMainButtons(playButtonStyle);
        createOptionButtons(playButtonStyle);
        createVocabButtons(playButtonStyle);
        createTab(playButtonStyle);
        addButtons(vocabButtons);
        addButtons(mainButtons);
        addButtons(optionsButtons);

        setVisibility(mainButtons, optionsButtons, vocabButtons);
    }

    private void createVocabButtons(TextButtonStyle style) {
        Table myTable = new Table();
        myTable.defaults().height((Settings.GAME_HEIGHT - TAB_HEIGHT) / 10);

        for (String word:vocab
             ) {
            myTable.add(new TextButton(word, style));
            myTable.row();
        }

        final ScrollPane scroller = new ScrollPane(myTable);
        final Table finalTable = new Table();
        finalTable.setFillParent(true);
        finalTable.padBottom(TAB_HEIGHT);
        finalTable.add(scroller);
        vocabButtons.add(finalTable);
    }

    private void createOptionButtons(TextButtonStyle style) {
        final TextButton testButton = new TextButton("Test", style);
        testButton.setPosition(Settings.GAME_WIDTH/2 -50,Settings.GAME_HEIGHT/2 - 50);
        optionsButtons.add(testButton);
    }

    private void createTab(TextButtonStyle style) {

        final TextButton mainMenuButton = new TextButton("Main", style);
        final TextButton vocabButton = new TextButton("Vocab", style);
        final TextButton optionsButton = new TextButton("Options", style);
        // should be list. add with loop (or sthg similar...)
        tabMenu = new Table();
        tabMenu.defaults().height(TAB_HEIGHT);
        // the division by three should  perhaps be changed for a check of array size? Easier maintenance... though
        // this would be better performance and no foreseeable extra options.
        tabMenu.defaults().width(Settings.GAME_WIDTH/3);
        tabMenu.left().bottom();
        tabMenu.add(mainMenuButton);
        tabMenu.add(vocabButton);
        tabMenu.add(optionsButton);
        stage.addActor(tabMenu);

        // the visibility setting will all use same method. order dictates which are set
        // invisible and which visible once more lists etc are made. Complication with how option buttons are implemented?
        optionsButton.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                // this should be in a method selectOptions()
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

    private void createMainButtons(TextButtonStyle playButtonStyle) {
        final TextButton quizButton = new TextButton("QUIZ", playButtonStyle);
        final TextButton playButton = new TextButton("PLAY", playButtonStyle);
        quizButton.setPosition(100, 200);
        playButton.setPosition(300, 200);

        // convert x and y to absolute position?
        quizButton.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                //System.out.println("Clicked! Is checked: " + button.isChecked());
                quizButton.setText("Starting new quiz");
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
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
       // Table.setDebug(true);
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
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
    Table table;
    ArrayList<TextButton> mainButtons;

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

        // should be list. add with loop (or sthg similar...)
        table = new Table();
        table.defaults().height(50);
        table.defaults().width(Settings.GAME_WIDTH/3);
        final TextButton mainMenuButton = new TextButton("Main", playButtonStyle);
        final TextButton vocabButton = new TextButton("Vocab", playButtonStyle);
        final TextButton optionsButton = new TextButton("Options", playButtonStyle);


        // the division by three should  perhaps be changed for a check of array size? Easier maintenance... though
        // this would be better performance and no foreseeable extra options.
        table.left().bottom();
        table.add(mainMenuButton);
        table.add(vocabButton);
        table.add(optionsButton);
        stage.addActor(table);

        skin.add("default", playButtonStyle);

        final TextButton quizButton = new TextButton("QUIZ", playButtonStyle);
        final TextButton playButton = new TextButton("PLAY", playButtonStyle);
        quizButton.setPosition(100, 200);
        playButton.setPosition(300, 200);

        stage.addActor(quizButton);
        stage.addActor(playButton);


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

        optionsButton.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                // this should be in a method selectOptions()
                playButton.setVisible(false);
                quizButton.setVisible(false);
            }
        });

        mainMenuButton.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                playButton.setVisible(true);
                quizButton.setVisible(true);
            }
        });

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
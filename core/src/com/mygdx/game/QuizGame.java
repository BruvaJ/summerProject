package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Jonneh on 07/07/2016.
 */
public class QuizGame extends ScreenAdapter{
    private final Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
    private final Stage stage = new Stage(new StretchViewport(Settings.GAME_WIDTH, Settings.GAME_HEIGHT));
    private final Table answerGrid = new Table();

    private final ArrayList<TextButton> answerButtons = new ArrayList<TextButton>(4);
    private final ArrayList<QuizWord> answers = new ArrayList<QuizWord>(4);
    private final Random rand = new Random();

    LanguageApp game;

    public QuizGame(LanguageApp game){
        create();
        this.game = game;
    }

    private void create(){
        Gdx.input.setInputProcessor(stage);
        buildUI();
        buildAnswers();
//      setQuestion();
//
        setAnswers();
        setQuestion();
    }

    private void setQuestion() {
        for (int i = 0; i < 4; i++
                ){
            answerButtons.get(i).setText(answers.get(i).getWord());
        }
    }

    private void buildAnswers() {
         for (int i = 0; i < 4; i++
                 ){
             answers.add(new QuizWord());
         }
     }

    // need to check that same word isn't used.
    private void setAnswers() {

    }

    private void buildUI() {
//       final TextButton button = new TextButton("Click me", skin);
//       button.setWidth(200f);
//       button.setHeight(20f);
//       button.setPosition(Gdx.graphics.getWidth() /2 - 100f, Gdx.graphics.getHeight()/2 - 10f);

//       button.addListener(new ClickListener(){
//           @Override
//           public void clicked(InputEvent event, float x, float y){
//               button.setText("You clicked the button");
//           }
//       });
        answerGrid.setFillParent(true);
        answerGrid.defaults();

//     stage.addActor(button);
        for (int i = 0; i < 4; i++){
            TextButton tempButton = (new TextButton("", skin));
            answerButtons.add(tempButton);
            answerGrid.add(tempButton);
        }
        stage.addActor(answerGrid);
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void hide() {
        super.hide();
    }

    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void resume() {
        super.resume();
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}

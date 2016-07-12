package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Jonneh on 07/07/2016.
 */
public class QuizGame extends ScreenAdapter{
    private final Stage stage = new Stage(new StretchViewport(Settings.GAME_WIDTH, Settings.GAME_HEIGHT));
    private final Table topTab = new Table();
    private final Table answerGrid = new Table();
    private final Table questionGrid = new Table();


    // by using Button and casting to text or image would it be easier to create?
    // maybe its just cleaner to have separate buttons / lists of buttons...
    private QuizButton questionButton;

    private final ArrayList<QuizButton> answerButtons = new ArrayList<QuizButton>(4);

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
        setAnswers();
        buildQuestion();
        setQuestion();
    }

    private void setQuestion() {
        questionButton.setText(questionButton.getjVocab());
    }

    private void buildQuestion() {
        QuizButton tempItem = answerButtons.get(rand.nextInt(answerButtons.size()));
        questionButton.set(tempItem.geteVocab(), tempItem.getjVocab(), tempItem.getIndex());
    }

    private void setAnswers() {
        for (int i = 0; i < 4; i++
                ){
          answerButtons.get(i).setText(answerButtons.get(i).geteVocab());
          answerButtons.get(i).addListener(new ClickListener(){
           @Override
           // close but no cigar!!
           public void clicked(InputEvent event, float x, float y){
               QuizButton button = (QuizButton)event.getListenerActor();
               checkAnswer(button);
           }
       });
        }
    }

    private void checkAnswer(QuizButton button) {
        if (button.getIndex() == questionButton.getIndex()){
            // show success
            // add score
            newQuestion();
        }else{
            // no score. show fail (refer to failed button)
        }
    }

    private void newQuestion() {
        buildAnswers();
        setAnswers();
        buildQuestion();
        setQuestion();
    }

    private void buildAnswers() {
        // need to check that same word isn't used
         for (int i = 0; i < 4; i++
                 ){
             VocabItem tempItem = Assets.vocab.get(rand.nextInt(Assets.vocab.size()));
             answerButtons.get(i).set(tempItem.geteVocab(), tempItem.getjVocab(), tempItem.getIndex());
         }
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
//        answerGrid.setFillParent(true);
        topTab.setPosition(Settings.GAME_WIDTH / 2, Settings.GAME_HEIGHT / 5);
        topTab.setDebug(true);


        answerGrid.center();
        answerGrid.setPosition(Settings.GAME_WIDTH/2, Settings.GAME_HEIGHT/2 - Settings.GAME_HEIGHT/4);

//     stage.addActor(button);
        for (int i = 0; i < 4; i++){
            QuizButton tempButton = (new QuizButton("", Assets.skin));
            answerButtons.add(tempButton);
            answerGrid.add(tempButton);
        }
        questionButton = new QuizButton("", Assets.skin, "jButton");
        questionButton.center();
        questionGrid.setPosition(Settings.GAME_WIDTH/2,Settings.GAME_HEIGHT/2 + Settings.GAME_HEIGHT/4);
        questionGrid.add(questionButton);

        stage.addActor(answerGrid);

        stage.addActor(topTab);
        stage.addActor(questionGrid);
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
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

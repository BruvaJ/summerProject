package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
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

    private final static int J_E = 0;
    private final static int E_J = 1;
    private final static int GAME_MODE = -1;
    private final static int PLAY_COUNT = 20;
    private int questionsAnswered = 0;
    private int score = 0;


    // by using Button and casting to text or image would it be easier to create?
    private QuizButton questionButton;

    private final TextArea scoreText = new TextArea("", Assets.skin);

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
        addListeners();
        play();
    }

    private void addListeners() {
        for (QuizButton b : answerButtons
                ) {
            b.addListener(new ClickListener() {
                @Override
                // close but no cigar!!
                public void clicked(InputEvent event, float x, float y) {
                    QuizButton button = (QuizButton) event.getListenerActor();
                    checkAnswer(button);
                }
            });
        }
    }
    private void play() {
        if(questionsAnswered < PLAY_COUNT) {
            newQuestion();
        }
        else{
            // end game
        }

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
        }
    }

    private void checkAnswer(QuizButton button) {
        questionsAnswered = questionsAnswered + 1;
        if (button.getIndex() == questionButton.getIndex()){
            // show success
            score ++;
            play();
        }else{
            // no score. show fail (refer to failed button)
            play();
        }
    }

    private void newQuestion() {
        buildAnswers();
        setAnswers();
        buildQuestion();
        setQuestion();
        setScore();
    }

    private void setScore() {
        scoreText.setText("Score:" + score + "/ 20");
    }

    private void buildAnswers() {
        // need to check that same word isn't used
         for (int i = 0; i < 4; i++
                 ){
             VocabItem tempItem = Assets.vocab.get(rand.nextInt(Assets.vocab.size()));
             answerButtons.get(i).set(tempItem.geteVocab(), tempItem.getjVocab(), tempItem.getIndex());
//             checkUnique();
         }
     }

//  private void checkUnique() {
//      // perhaps this should be more complicated when choosing vocab. depend on how many times its been seen before for example
//      for (int i = 0; i < 4; i++) {
//          for (QuizButton answer : answerButtons
//                  ) {
//              if (answerButtons.get(i).getIndex() == answer.getIndex())
//                  buildAnswers();
//          }
//      }
//  }


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
        scoreText.setDisabled(true);
        scoreText.setPosition(Settings.GAME_WIDTH - scoreText.getWidth(), Settings.GAME_HEIGHT - scoreText.getHeight());
//       topTab.add(scoreText);
//       topTab.center();
//       topTab.setPosition(Settings.GAME_WIDTH / 2, Settings.GAME_HEIGHT - scoreText.getHeight());
//       topTab.setWidth(Settings.GAME_WIDTH);
//       topTab.setDebug(true);



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

        stage.addActor(scoreText);
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
        for (QuizButton b:answerButtons
             ) {
            b.isDisabled();
        }
        super.dispose();
    }
}

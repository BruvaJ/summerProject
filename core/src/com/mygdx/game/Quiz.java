package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import java.util.*;

/**
 * Created by Jonneh on 07/07/2016.
 */
abstract class Quiz extends ScreenAdapter{
    final Stage stage = new Stage(new StretchViewport(Settings.GAME_WIDTH, Settings.GAME_HEIGHT));
    private final Table topTab = new Table();
    private final Table answerGrid = new Table();
    private final Table questionGrid = new Table();

    private final static int J_E = 0;
    private final static int E_J = 1;
    private final static int GAME_MODE = -1;
    final static int PLAY_COUNT = 10;
    final static int GAME_RUNNING = 0;
    final static int GAME_OVER = 2;
    static ArrayList<Integer> answerIndices;
    static List<QuizItem> answerSet;
    static int currentPos = 0;
    private int questionsAnswered = 0;
    int score = 0;
    int gameState = 0;

    Label completionMessage;
    private QuizButton questionButton;

    final TextButton backButton = new TextButton("<< ", Assets.skin);
    final TextArea scoreText = new TextArea("", Assets.skin);

    final ArrayList<QuizButton> answerButtons = new ArrayList<QuizButton>(4);

    private final Random rand = new Random();

    LanguageApp game;

    public Quiz(LanguageApp game){
        gameState = GAME_RUNNING;
        create();
        this.game = game;
    }

    protected void create(){
        answerIndices = new ArrayList<Integer>();
        answerSet = new ArrayList<QuizItem>();
        Gdx.input.setInputProcessor(stage);
        createAnswerSet();
        createUniqueIndex();
        buildUI();
        addListeners();
        setScore();
        play();
    }

    private void buildCompletionMessage() {
        completionMessage = new Label("", Assets.skin);
        setCompletionMessage();
        stage.addActor(completionMessage);
    }

    protected void setCompletionMessage(){
        hideAll();
        completionMessage.setAlignment(Align.center);
        completionMessage.setPosition(Settings.GAME_WIDTH/2,
                Settings.GAME_HEIGHT/2);
    }

    private void hideAll() {
        for (Actor a:stage.getActors()
             ) {
            a.setVisible(false);
        }
    }

    protected void createAnswerSet() {
        Collections.sort(Assets.vocab, QuizItem.CountComparator);
        for(int i = 0; answerSet.size() < PLAY_COUNT * 4; i++){
            answerSet.add(Assets.vocab.get(i));
        }
    }

    protected void addListeners() {
        for (QuizButton b : answerButtons
                ) {
            b.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    QuizButton button = (QuizButton) event.getListenerActor();
                    checkAnswer(button);
                }
            });
        }

        backButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                save();
                quit();
            }
        });
    }



    private void quit() {
        answerSet.clear();
        answerIndices.clear();
        currentPos = 0;
        dispose();
        game.setScreen(new MainMenu(game));
    }

    protected void save() {
        Collections.sort(Assets.vocab, QuizItem.IndexComparator);
        FileIO.saveFile("count.txt");
    }

    protected void play() {
        if(gameState == GAME_RUNNING) {
            newQuestion();
        }else{
            endGame();
        }
    }

    protected void endGame() {
        buildCompletionMessage();
        stage.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                save();
                quit();
            }
        });
    }

    private void setQuestion() {
        questionButton.setText(questionButton.geteVocab());
    }

    private void buildQuestion() {
        QuizButton tempItem = answerButtons.get(rand.nextInt(answerButtons.size()));
        questionButton.set(tempItem.getVocabItem());
    }

    private void setAnswers() {
        for (int i = 0; i < 4; i++
                ){
            answerButtons.get(i).setText(answerButtons.get(i).getjVocab());
        }
    }

    protected void checkAnswer(QuizButton selection) {
        questionsAnswered = questionsAnswered + 1;
        checkGameState();
        if (selection.getIndex() == questionButton.getIndex()){
            // show success
            correctAnswer(selection);
        }else{
            // no score. show fail (refer to failed button)
            incorrectAnswer(selection);
        }
    }

    protected void incorrectAnswer(QuizButton selection){
        Assets.playSound(Assets.incorrect);
    }

    protected void correctAnswer(QuizButton button){
        Assets.playSound(Assets.correct);
    }


    protected void checkGameState() {
        if(questionsAnswered >= PLAY_COUNT)
            gameState = GAME_OVER;
    }

    protected void newQuestion() {
        buildAnswers();
        setAnswers();
        buildQuestion();
        setQuestion();
    }

    void setScore() {
        scoreText.setText("Score:    " + score + "/" + PLAY_COUNT);
    }

    private void buildAnswers() {
        for (int i = 0; i < 4; i++
                ){
            Gdx.app.log("currentPos",String.valueOf(currentPos + i));
            QuizItem tempItem = answerSet.get(answerIndices.get(i + currentPos));
            answerSet.remove(answerIndices.get(i + currentPos));
            answerButtons.get(i).set(tempItem);
//             checkUnique();
        }
        currentPos += 4;
    }

    private void createUniqueIndex() {
        for(int i = 0; i < answerSet.size(); i++){
            answerIndices.add(i);
        }
        Collections.shuffle(answerIndices);
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

    protected void buildUI() {
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
        backButton.setPosition(0, Settings.GAME_HEIGHT - backButton.getHeight());

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
            QuizButton tempButton = (new QuizButton("", Assets.skin, "jButton"));
            answerButtons.add(tempButton);
            answerGrid.add(tempButton);
        }
        questionButton = new QuizButton("", Assets.skin);
        questionButton.center();
        questionGrid.setPosition(Settings.GAME_WIDTH/2,Settings.GAME_HEIGHT/2 + Settings.GAME_HEIGHT/4);
        questionGrid.add(questionButton);

        stage.addActor(answerGrid);

        stage.addActor(scoreText);
        stage.addActor(backButton);

        stage.addActor(questionGrid);
    }

    void toggleButtons(boolean val) {
        for (QuizButton b:answerButtons
                ) {
            b.setVisible(val);
        }
        questionButton.setVisible(val);
    }

    void setCurrentPos(int newValue){
        currentPos = newValue;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(80/255f, 180/255f, 180/255f, 1);
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
        stage.dispose();
        super.dispose();
    }
}

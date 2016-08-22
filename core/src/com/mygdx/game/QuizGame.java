package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

/**
 * Created by Jonneh on 07/07/2016.
 */
public class QuizGame extends Quiz{
    private Label feedbackMessage;
    private Label feedbackMessage2;
    private static final int INCORRECT = 0;
    private static final int CORRECT = 1;


    public QuizGame(LanguageApp game) {
        super(game);
    }

    @Override
    protected void create() {
        super.create();
        buildFeedbackMessage();
    }

    private void buildFeedbackMessage() {
        feedbackMessage = new Label(" ", Assets.skin);
        feedbackMessage.setAlignment(Align.center);
        feedbackMessage.setPosition(Settings.GAME_WIDTH/2, Settings.GAME_HEIGHT/2);
        stage.addActor(feedbackMessage);
        feedbackMessage.setVisible(false);
        feedbackMessage2 = new Label(" ", Assets.skin, "jFont", Color.BLACK);
        feedbackMessage2.setAlignment(Align.center);
        feedbackMessage2.setPosition(Settings.GAME_WIDTH/2, Settings.GAME_HEIGHT/2 - feedbackMessage.getHeight() * 2);
        stage.addActor(feedbackMessage2);
        feedbackMessage2.setVisible(false);
    }

    @Override
    protected void correctAnswer(QuizButton selection) {
        super.correctAnswer(selection);
        score ++;
        setScore();
        selection.getQuizItem().upCount();
        setFeedbackMessage(selection.geteVocab(), selection.getjVocab(), CORRECT);
    }

    @Override
    protected void setCompletionMessage() {
        super.setCompletionMessage();
        completionMessage.setText("Good job, you got " + score + " out of " + PLAY_COUNT);
    }

    @Override
    protected void incorrectAnswer(QuizButton selection) {
        super.incorrectAnswer(selection);
        toggleButtons(false);
        setFeedbackMessage(selection.geteVocab(), selection.getjVocab(), INCORRECT);
    }

    private void setFeedbackMessage(String e, String j, int answer) {
        toggleButtons(false);
        feedbackMessage.setVisible(true);
        feedbackMessage2.setVisible(true);
        if(answer==CORRECT){
            feedbackMessage.setText("That's right, '" + e + "' is");
            feedbackMessage2.setText(j);
        }else if(answer==INCORRECT){
            feedbackMessage.setText("Sorry, '" + e + "' is");
            feedbackMessage2.setText(j);
        }else{
            //do nothing
        }
        final ClickListener stageListener = new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                toggleButtons(true);
                feedbackMessage.setVisible(false);
                feedbackMessage2.setVisible(false);
                stage.removeListener(this);
                play();
            }
        };
        stage.addListener(stageListener);
    }

    @Override
    protected void endMusic() {
        Assets.stopMusic(Assets.quiz);
    }

    @Override
    protected void startMusic() {
        Assets.playMusic(Assets.quiz);
    }


}
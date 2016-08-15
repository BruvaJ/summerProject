package com.mygdx.game;

/**
 * Created by Jonneh on 07/07/2016.
 */
public class QuizGame extends Quiz{
    public QuizGame(LanguageApp game) {
        super(game);
    }

    @Override
    protected void incorrectAnswer() {
        // do nothing
    }

    @Override
    protected void create() {
        super.create();
    }

    @Override
    protected void correctAnswer(QuizButton button) {
        score ++;
        setScore();
        button.getQuizItem().upCount();
    }
}
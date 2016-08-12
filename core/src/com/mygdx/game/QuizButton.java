package com.mygdx.game;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

/**
 * Created by Jonneh on 07/07/2016.
 */
public class QuizButton extends TextButton{

    private QuizItem quizItem;

    public QuizButton(String text, Skin skin){
        super(text, skin);
    }

    public QuizButton(String text, Skin skin, String styleName) {
        super(text, skin, styleName);
    }

    public void set(QuizItem vocab) {
        this.quizItem = vocab;
    }

    public QuizItem getVocabItem(){
        return quizItem;
    }

    public int getIndex() {
        return quizItem.getIndex();
    }

    public String geteVocab() {
        return quizItem.geteVocab();
    }

    public String getjVocab() {
        return quizItem.getjVocab();
    }

    public QuizItem getQuizItem() {
        return quizItem;
    }
}

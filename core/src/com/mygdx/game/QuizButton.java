package com.mygdx.game;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

/**
 * Created by Jonneh on 07/07/2016.
 */
public class QuizButton extends TextButton{
    private QuizItem vocab;

    public QuizButton(String text, Skin skin){
        super(text, skin);
    }

    public QuizButton(String text, Skin skin, String styleName) {
        super(text, skin, styleName);
    }

    public void set(QuizItem vocab) {
        this.vocab = vocab;
    }

    public QuizItem getVocabItem(){
        return vocab;
    }

    public int getIndex() {
        return vocab.getIndex();
    }

    public String geteVocab() {
        return vocab.geteVocab();
    }

    public String getjVocab() {
        return vocab.getjVocab();
    }
}

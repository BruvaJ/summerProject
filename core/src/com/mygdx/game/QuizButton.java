package com.mygdx.game;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

/**
 * Created by Jonneh on 07/07/2016.
 */
public class QuizButton extends TextButton{
    private String eVocab;
    private String jVocab;
    private int index = -1;

    public QuizButton(String text, Skin skin){
        super(text, skin);
    }

    public QuizButton(String text, Skin skin, String styleName) {
        super(text, skin, styleName);
    }

    public void set(String eVocab, String jVocab, int index) {
        this.eVocab = eVocab;
        this.jVocab = jVocab;
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public String geteVocab() {
        return eVocab;
    }

    public String getjVocab() {
        return jVocab;
    }
}

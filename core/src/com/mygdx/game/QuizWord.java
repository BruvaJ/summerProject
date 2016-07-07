package com.mygdx.game;

import com.badlogic.gdx.utils.Pool;

/**
 * Created by Jonneh on 07/07/2016.
 */
public class QuizWord {
    private String word;
    private int index;

    public QuizWord(){
    }

    public void set(String word, int index){
        this.word = word;
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public String getWord() {
        return word;
    }
}

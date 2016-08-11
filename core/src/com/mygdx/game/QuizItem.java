package com.mygdx.game;

/**
 * Created by Jonneh on 11/07/2016.
 */


public class QuizItem {
    private String eVocab;
    private String jVocab;
    private int index;
    private int count;

    public QuizItem(String eVocab, String jVocab, int index){
        this.eVocab = eVocab;
        this.jVocab = jVocab;
        this.index = index;
        count = 0;
    }

    public String geteVocab() {
        return eVocab;
    }

    public String getjVocab() {
        return jVocab;
    }

    public int getIndex() {
        return index;
    }
}

package com.mygdx.game;

import java.util.Comparator;

/**
 * Created by Jonneh on 11/07/2016.
 */


public class QuizItem implements Comparable<QuizItem>{
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

    public int getCount(){
        return count;
    }

    public static Comparator<QuizItem> CountComparator = new Comparator<QuizItem>() {
        @Override
        public int compare(QuizItem o1, QuizItem o2) {
            return o1.getCount() - o2.getCount();
        }
    };

    @Override
    public int compareTo(QuizItem o) {
        int compareCount = o.getCount();

        return this.getCount() - compareCount;
    }
}

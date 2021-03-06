package com.mygdx.game;

import java.util.Comparator;

/**
 * Created by Jonneh on 11/07/2016.
 */


public class QuizItem implements Comparable<QuizItem>{
    private String eVocab;
    private String jVocab;
    private int count;
    private int index;


    public QuizItem(String eVocab, String jVocab, int count, int index){
        this.eVocab = eVocab;
        this.jVocab = jVocab;
        this.count = count;
        this.index = index;
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

    public void upCount(){
        count++;
    }

    public static Comparator<QuizItem> CountComparator = new Comparator<QuizItem>() {
        @Override
        public int compare(QuizItem o1, QuizItem o2) {
            return o1.getCount() - o2.getCount();
        }
    };

    public static Comparator<QuizItem> IndexComparator = new Comparator<QuizItem>() {
        @Override
        public int compare(QuizItem o1, QuizItem o2) {
            return o1.getIndex() - o2.getIndex();
        }
    };


    @Override
    public int compareTo(QuizItem o) {
        int compareCount = o.getCount();

        return this.getCount() - compareCount;
    }
}

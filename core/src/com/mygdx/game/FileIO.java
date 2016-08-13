package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.StringBuilder;

import java.util.ArrayList;

/**
 * Created by Jonneh on 06/07/2016.
 */
public class FileIO {
    private static FileHandle handle;

    public FileIO(){
    }


    public static String readFile(String path){
        return readFile(path, 0);
    }

    // requires some error handling.... exists().
    public static String readFile(String path, int size){
        String vocabString;
        handle = Gdx.files.internal(path);
        if(handle.exists()) {
            vocabString = handle.readString();
            return vocabString;
        }
        vocabString = createCount(size);
        return vocabString;
    }

    // need to make an empty count file if not already in existence
    private static String createCount(int size) {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < size; i++){
            sb.append("0\n");
        }
        return sb.toString();
    }

    public static void saveFile(String path) {
        handle = Gdx.files.local(path);
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < Assets.vocab.size(); i++){
            sb.append(Assets.vocab.get(i).getCount());
            sb.append("\n");
        }
        handle.writeString(sb.toString(), false);
    }

}

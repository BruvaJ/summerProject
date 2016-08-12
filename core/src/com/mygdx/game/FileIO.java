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
    private static String newCount;

    public FileIO(){
    }


    // requires some error handling.... exists().
    public static String readFile(String path){
        handle = Gdx.files.internal(path);
        String vocabString = handle.readString();
        return vocabString;
    }

    public static void saveFile(String path) {
        handle = Gdx.files.local(path);
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < Assets.vocab.size(); i++){
            sb.append(Assets.vocab.get(i).getCount());
            sb.append("/n");
        }
        handle.writeString(sb.toString(), false);
    }

}

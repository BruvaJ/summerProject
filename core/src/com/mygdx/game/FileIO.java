package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
/**
 * Created by Jonneh on 06/07/2016.
 */
public class FileIO {
    private static FileHandle handle;

    public FileIO(){
    }

    public static String readFile(String path){
        handle = Gdx.files.internal(path);
        String vocabString = handle.readString();
        return vocabString;
    }
}

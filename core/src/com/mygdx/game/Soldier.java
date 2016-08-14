package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by BigBoy on 13/08/2016.
 */
public class Soldier extends Sprite {
    public int getCurrentPosition() {
        return currentPosition;
    }

    int currentPosition;

    public Soldier(Texture texture, int startPosition) {
        super(texture);
        this.currentPosition = startPosition;
    }

    public void advance(int speed){
        currentPosition+= speed;
    }
}

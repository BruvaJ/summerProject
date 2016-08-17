package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * Created by BigBoy on 13/08/2016.
 */
public class Soldier extends Image {
    public int getCurrentPosition() {
        return currentPosition;
    }

    int currentPosition;

    public Soldier(Texture texture, int x, int y) {
        super(texture);
        this.setPosition(x, y);
        this.currentPosition = x;
    }

    public void advance(int speed){
        currentPosition+= speed;
        super.setPosition(currentPosition, this.getY());
    }
}

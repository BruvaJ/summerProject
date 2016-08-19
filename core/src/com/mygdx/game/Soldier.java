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

    private int currentPosition;
    private int health;

    public Soldier(Texture texture, int x, int y, int health) {
        super(texture);
        this.health = health;
        this.setPosition(x, y);
        this.currentPosition = x;
    }

    public void advance(int speed){
        currentPosition+= speed;
        super.setPosition(currentPosition, this.getY());
    }

    void loseHealth(){
        health += -1;
    }

    public int getHealth() {
        return health;
    }
}

package com.mygdx.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by BigBoy on 13/08/2016.
 */
public class BattleGame extends Quiz{
    private SpriteBatch batch;
    private Texture texture;
    private Sprite sprite;
    int startPosition = 5;

    public BattleGame(LanguageApp game) {
        super(game);
    }

    @Override
    protected void create() {
        super.create();
        batch = new SpriteBatch();
        texture = new Texture(Gdx.files.internal("stickman.png"));
        sprite = new Sprite(texture);
        sprite.setSize(10, 10);
    }

    @Override
    protected void saveAndQuit() {
        // do nothing
    }

    @Override
    protected void checkGameState() {
        // do nothing
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        batch.begin();
        startPosition++;
        batch.draw(sprite, startPosition, 5);
        batch.end();
    }

    @Override
    protected void correctAnswer(QuizButton button) {
    }
}

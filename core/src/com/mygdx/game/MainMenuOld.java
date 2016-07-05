package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Jonneh on 04/07/2016.
 */
public class MainMenuOld extends ScreenAdapter{
    LanguageApp game;
    OrthographicCamera cam;
    Rectangle quizBounds;
    Rectangle playBounds;

    Vector3 touchPoint;

    public MainMenuOld(LanguageApp game){
        this.game = game;
        cam = new OrthographicCamera(480, 320);
        cam.position.set(480/2, 320/2, 0);
        quizBounds = new Rectangle(40, 150, 180, 100);
        playBounds = new Rectangle(260, 150, 180, 100);
        touchPoint = new Vector3();
    }

    public void update(){
        if(Gdx.input.justTouched()){
            cam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));

            if(playBounds.contains(touchPoint.x, touchPoint.y)){
                Assets.playSound(Assets.clickSound);
                return;
            }
            if(quizBounds.contains(touchPoint.x, touchPoint.y)){
                Assets.playSound(Assets.clickSound);
                return;
            }
        }
    }

    public void draw(){
        GL20 gl = Gdx.gl;
        gl.glClearColor(1,0,0,1);
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        cam.update();
        game.batcher.setProjectionMatrix(cam.combined);

        game.batcher.disableBlending();
        game.batcher.begin();
        game.batcher.draw(Assets.backgroundRegion, 0, 0, 480, 320);
        game.batcher.end();

        game.batcher.enableBlending();
        game.batcher.begin();
        game.batcher.draw(Assets.quizRegion, 40, 150, 180, 100);
        game.batcher.draw(Assets.playRegion, 260, 150, 180, 100);
        game.batcher.end();
    }

    @Override
    public void render(float delta) {
        update();
        draw();
    }
}

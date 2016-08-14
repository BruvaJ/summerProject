package com.mygdx.game;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by BigBoy on 13/08/2016.
 */

class BattleGame extends Quiz{
    private SpriteBatch batch;
    private ArrayList<Soldier> mySoldiers;
    private ArrayList<Soldier> enemySoldiers;
    private double currentTime = 0f;

    private final static int SPAWN_RATE = 5;
    private final static int MY_START_POSITION = 5;
    private final static int ENEMY_START_POSITION = Settings.GAME_WIDTH-5;
    private final static int MY_SPEED = 1;
    private final static int ENEMY_SPEED = -1;

    BattleGame(LanguageApp game) {
        super(game);
    }

    @Override
    protected void create() {
        super.create();
        batch = new SpriteBatch();
        mySoldiers = new ArrayList<Soldier>();
        enemySoldiers = new ArrayList<Soldier>();
    }

    @Override
    protected void createAnswerSet() {
        Collections.shuffle(Assets.vocab);
        for(int i = 0; answerSet.size() < Assets.vocab.size() ; i++){
            answerSet.add(Assets.vocab.get(i));
        }
    }

    @Override
    protected void newQuestion() {
        if(answerSet.size() + currentPos >= answerSet.size()) {
            Collections.shuffle(answerIndices);
            resetCurrentPos();
        }
        super.newQuestion();
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
        currentTime += delta;
        if(currentTime > SPAWN_RATE){
            spawnEnemy();
            resetTime();
        }
        batch.begin();
        moveSoldiers();
        batch.end();
    }

    private void moveSoldiers() {
        for (Soldier s:mySoldiers
                ) {
            s.advance(MY_SPEED);
            batch.draw(s, s.getCurrentPosition(), Settings.GAME_HEIGHT/2);
        }
        for (Soldier s:enemySoldiers
                ) {
            s.advance(ENEMY_SPEED);
            batch.draw(s, s.getCurrentPosition(), Settings.GAME_HEIGHT/2);
        }
    }

    @Override
    protected void buildUI() {
        super.buildUI();
        scoreText.setVisible(false);
    }

    private void resetTime() {
        currentTime = 0;
    }

    private void spawnEnemy() {
        enemySoldiers.add(new Soldier(Assets.mySoldier, ENEMY_START_POSITION));
    }

    @Override
    protected void correctAnswer(QuizButton button) {
        mySoldiers.add(new Soldier(Assets.mySoldier, MY_START_POSITION));
    }

    private void resetCurrentPos(){
        setCurrentPos(0);
    }

}

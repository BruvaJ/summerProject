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
    private double myPenalty = 0f;
    private boolean mustWait = false;

    private final static int SPAWN_RATE = 5;
    private final static int PENALTY_TIME = 5;
    private final static int MY_START_POSITION = 5;
    private final static int ENEMY_START_POSITION = Settings.GAME_WIDTH-5;
    private final static int MY_SPEED = 1;
    private final static int ENEMY_SPEED = -1;

    private int enemyLife;
    private int myLife;

    BattleGame(LanguageApp game) {
        super(game);
    }

    @Override
    protected void create() {
        super.create();
        enemyLife = 3;
        myLife = 3;
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
        if(mustWait && myPenalty < PENALTY_TIME) {
            myPenalty += delta;
        }else{
            toggleButtons(true);
        }
        super.render(delta);
        currentTime += delta;
        if(currentTime > SPAWN_RATE){
            spawnEnemy();
            resetTime();
        }
        batch.begin();
        checkColision();
        checkExitScreen();
        moveSoldiers();
        batch.end();
    }

    private void checkExitScreen() {
        if(!mySoldiers.isEmpty() && mySoldiers.get(0).getCurrentPosition() > Settings.GAME_WIDTH) {
            mySoldiers.remove(0);
            enemyLife--;
        }
        if(!enemySoldiers.isEmpty() && enemySoldiers.get(0).getCurrentPosition() < 0) {
            enemySoldiers.remove(0);
            myLife--;
        }
    }

    private void checkColision() {
        if(!mySoldiers.isEmpty()&&!enemySoldiers.isEmpty()){
            if(mySoldiers.get(0).getCurrentPosition() + mySoldiers.get(0).getWidth() > enemySoldiers.get(0).getCurrentPosition()) {
                mySoldiers.remove(0);
                enemySoldiers.remove(0);
            }
        }
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

    @Override
    protected void incorrectAnswer() {
        setPenalty();
        toggleButtons(false);
    }

    private void toggleButtons(boolean val) {
        for (QuizButton b:answerButtons
             ) {
            b.setVisible(val);
        }
        questionButton.setVisible(val);
    }

    private void setPenalty() {
        myPenalty = 0;
        mustWait = true;
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

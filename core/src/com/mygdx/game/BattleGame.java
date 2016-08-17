package com.mygdx.game;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by BigBoy on 13/08/2016.
 */

class BattleGame extends Quiz{
    private SpriteBatch batch;
    private ArrayList<Soldier> mySoldiers;
    private ArrayList<Soldier> enemySoldiers;
    private ArrayList<Image> myLifeSprites;
    private ArrayList<Image> enemyLifeSprites;
    private double currentTime = 0f;
    private double myPenalty = 0f;
    private boolean mustWait = false;

    private int spawnRate;
    private int lifePositionY;
    private final static int START_LIFE = 3;
    private final static int PENALTY_TIME = 5;
    private final static int MY_START_POSITION = 5;
    private final static int ENEMY_START_POSITION = Settings.GAME_WIDTH-5;
    private final static int MY_SPEED = 1;
    private final static int ENEMY_SPEED = -1;

//    private int enemyLife;
//    private int myLife;

    BattleGame(LanguageApp game, int spawnRate) {
        super(game);
        this.spawnRate = spawnRate;
    }

    @Override
    protected void create() {
        super.create();
        batch = new SpriteBatch();
        myLifeSprites = new ArrayList<Image>();
        enemyLifeSprites =  new ArrayList<Image>();
        mySoldiers = new ArrayList<Soldier>();
        enemySoldiers = new ArrayList<Soldier>();
        lifePositionY = Settings.GAME_HEIGHT - (int)backButton.getHeight() - Assets.lifeCounter.getHeight();
        createHealthBars(myLifeSprites, Assets.lifeCounter.getWidth() * (START_LIFE - 1), - Assets.lifeCounter.getWidth());
        createHealthBars(enemyLifeSprites, Settings.GAME_WIDTH - Assets.lifeCounter.getWidth()*START_LIFE, Assets.lifeCounter.getWidth());
        drawLife();
    }

    private void createHealthBars(ArrayList<Image> s, int x, int increment) {
        for(int i = 0; i < START_LIFE; i++ ){
            Image life = new Image(Assets.lifeCounter);
            life.setPosition(x, lifePositionY);
            s.add(life);
            x += increment;
        }
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
        if(currentTime > spawnRate){
            spawnEnemy();
            resetTime();
        }
        checkCollision();
        checkExitScreen();
        moveSoldiers();
    }

    private void drawLife() {
        for(int i = 0; i < myLifeSprites.size(); i ++){
            stage.addActor(myLifeSprites.get(i));
            stage.addActor(enemyLifeSprites.get(i));
        }
    }

    private void checkExitScreen() {
        if(!mySoldiers.isEmpty() && mySoldiers.get(0).getCurrentPosition() > Settings.GAME_WIDTH) {
            removeActor(mySoldiers.get(0));
            mySoldiers.remove(0);
            loseHealth(enemyLifeSprites);
        }
        if(!enemySoldiers.isEmpty() && enemySoldiers.get(0).getCurrentPosition() < 0 - enemySoldiers.get(0).getWidth()) {
            removeActor(enemySoldiers.get(0));
            enemySoldiers.remove(0);
           loseHealth(myLifeSprites);
        }
    }

    private void loseHealth(ArrayList<Image> s) {
        removeActor(s.get(0));
        s.remove(0);
    }

    private void checkCollision() {
        if(!mySoldiers.isEmpty()&&!enemySoldiers.isEmpty()){
            if(mySoldiers.get(0).getCurrentPosition() + mySoldiers.get(0).getWidth() > enemySoldiers.get(0).getCurrentPosition()) {
                Soldier myCollision = mySoldiers.get(0);
                Soldier enemyCollision = enemySoldiers.get(0);
                removeSoldiers(myCollision, enemyCollision);
                mySoldiers.remove(0);
                enemySoldiers.remove(0);
            }
        }
    }

    private void removeActor(Actor actor){
        for(int i = 0; i < stage.getActors().size; i++){
            if(stage.getActors().get(i).equals(actor)){
                stage.getActors().get(i).remove();
                break;
            }
        }
    }

    private void removeSoldiers(Soldier myCollision, Soldier enemySoldier) {
        for (int i = 0; i < stage.getActors().size; i++) {
            if(stage.getActors().get(i).equals(myCollision)){
                stage.getActors().get(i).remove();
                i--;
            }
            if(stage.getActors().get(i).equals(enemySoldier)){
                stage.getActors().get(i).remove();
                i--;
            }
        }
    }

    private void moveSoldiers() {
        for (Soldier s:mySoldiers
                ) {
            s.advance(MY_SPEED);
        }
        for (Soldier s:enemySoldiers
                ) {
            s.advance(ENEMY_SPEED);
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
        Soldier newEnemy = new Soldier(Assets.enemySoldier, ENEMY_START_POSITION,
                Settings.GAME_HEIGHT / 2 - Assets.enemySoldier.getHeight() / 2);
        enemySoldiers.add(newEnemy);
        stage.addActor(newEnemy);
    }

    @Override
    protected void correctAnswer(QuizButton button) {
        Soldier newSoldier = new Soldier(Assets.mySoldier, MY_START_POSITION,
                Settings.GAME_HEIGHT/2 - Assets.mySoldier.getHeight() / 2);
        mySoldiers.add(newSoldier);
        stage.addActor(newSoldier);
    }

    private void resetCurrentPos(){
        setCurrentPos(0);
    }
}

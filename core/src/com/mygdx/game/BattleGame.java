package com.mygdx.game;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by BigBoy on 13/08/2016.
 */

class BattleGame extends Quiz{
    private ArrayList<Soldier> mySoldiers;
    private ArrayList<Soldier> enemySoldiers;
    private ArrayList<Image> myLifeSprites;
    private ArrayList<Image> enemyLifeSprites;
    private double currentTime = 0f;
    private double myPenalty = 0f;
    private boolean mustWait = false;

    private int spawnRate;
    private int lifePositionY;
    private TextButton pauseButton;
    private final static int START_LIFE = 3;
    private final static int PENALTY_TIME = 5;
    private final static int MY_SPEED = 1;
    private final static int ENEMY_SPEED = -1;
    private final static int GAME_PAUSED = 1;

    BattleGame(LanguageApp game, int spawnRate) {
        super(game);
        this.spawnRate = spawnRate;
    }

    @Override
    protected void create() {
        super.create();
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
        if(currentPos >= answerSet.size()) {
            Collections.shuffle(answerIndices);
            resetCurrentPos();
        }
        super.newQuestion();
    }

    @Override
    protected void save() {
        // do nothing
    }

    @Override
    protected void checkGameState() {
        // do nothing
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        if(gameState == GAME_RUNNING) {
            if (mustWait && myPenalty < PENALTY_TIME) {
                myPenalty += delta;
            } else {
                toggleButtons(true);
            }
            super.render(delta);
            currentTime += delta;
            if (currentTime > spawnRate) {
                spawnSoldier(Assets.enemySoldier, Settings.GAME_WIDTH,
                        Settings.GAME_HEIGHT / 2 - Assets.enemySoldier.getHeight() / 2, enemySoldiers);
                resetTime();
            }
            checkCollision();
            checkExitScreen();
            moveSoldiers();
        }else{
            // do nothing
        }
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
        if(s.size() < 1) {
            pauseGame();
            endGame();
        }
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
        pauseButton = new TextButton("||", Assets.skin);
        pauseButton.setPosition(0 + backButton.getWidth(), Settings.GAME_HEIGHT - pauseButton.getHeight());
        stage.addActor(pauseButton);
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

    @Override
    protected void correctAnswer(QuizButton button) {
        spawnSoldier(Assets.mySoldier, 0 - Assets.mySoldier.getWidth(),
                Settings.GAME_HEIGHT/2 - Assets.mySoldier.getHeight() / 2, mySoldiers);
    }

    private void spawnSoldier(Texture t, int x, int y, ArrayList<Soldier> soldierGroup) {
        Soldier newSoldier = new Soldier(t, x, y);
        soldierGroup.add(newSoldier);
        stage.addActor(newSoldier);
    }

    private void resetCurrentPos(){
        setCurrentPos(0);
    }


    private void pauseGame() {
        switch(gameState){
            case GAME_RUNNING: gameState = GAME_PAUSED;
                toggleButtons(false);
                break;
            case GAME_PAUSED: gameState = GAME_RUNNING;
                toggleButtons(true);
                break;
        }
    }

    @Override
    protected void addListeners() {
        super.addListeners();
        pauseButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                pauseGame();
            }
        });
    }

    @Override
    protected void setCompletionMessage() {
        if(myLifeSprites.isEmpty())
            completionMessage.setText("Oh no! You lost :( Try again.");
        if(enemyLifeSprites.isEmpty())
            completionMessage.setText("Hooray! You won the game.");
        super.setCompletionMessage();
    }
}
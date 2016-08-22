package com.mygdx.game;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

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
    private ArrayList<Sprite> powerUps;
    private int streak;
    private double currentTime = 0f;
    private double myPenalty = 0f;
    private boolean mustWait = false;

    private int spawnRate;
    private TextButton pauseButton;
    private boolean bigSoldier;

    private final static boolean NOT_READY = false;
    private final static boolean READY = true;
    private final static int START_LIFE = 2;
    private final static int PENALTY_TIME = 5;
    private final static int MY_SPEED = 1;
    private final static int ENEMY_SPEED = -1;
    private final static int GAME_PAUSED = 1;
    private final static int MAX_STREAK = 3;
    private Batch batch;


    BattleGame(LanguageApp game, int spawnRate) {
        super(game);
        this.spawnRate = spawnRate;
    }

    @Override
    protected void create() {
        super.create();
        resetStreak();

        bigSoldier = NOT_READY;
        batch = new SpriteBatch();
        myLifeSprites = new ArrayList<Image>();
        enemyLifeSprites =  new ArrayList<Image>();
        powerUps = new ArrayList<Sprite>();
        mySoldiers = new ArrayList<Soldier>();
        enemySoldiers = new ArrayList<Soldier>();
        int lifeY = Settings.GAME_HEIGHT - (int)backButton.getHeight() - Assets.lifeCounter.getHeight();
        createPowerUps();
        createHealthBars(myLifeSprites,
                Assets.lifeCounter.getWidth() * (START_LIFE - 1),
                lifeY,
                - Assets.lifeCounter.getWidth());
        createHealthBars(enemyLifeSprites,
                Settings.GAME_WIDTH - Assets.lifeCounter.getWidth()*START_LIFE,
                lifeY,
                Assets.lifeCounter.getWidth());
        drawLife();
    }

    private void createPowerUps() {
        int powerUpX = 0;
        int powerUpY = Settings.GAME_HEIGHT - (int)backButton.getHeight() - Assets.lifeCounter.getHeight() - Assets.powerUp.getHeight();
        for(int i = 0; i<MAX_STREAK; i++){
            Sprite powerUp = new Sprite(Assets.powerUp);
            powerUp.setSize(Settings.GAME_WIDTH, Settings.GAME_HEIGHT);
            powerUp.setPosition(powerUpX, powerUpY);
            powerUps.add(powerUp);
            powerUpX += Assets.powerUp.getWidth();
        }
    }

    private void resetStreak() {
        streak = 0;
    }

    private void createHealthBars(ArrayList<Image> s, int x, int y, int increment) {
        for(int i = 0; i < START_LIFE; i++ ){
            Image life = new Image(Assets.lifeCounter);
            life.setPosition(x, y);
            s.add(life);
            x += increment;
        }
    }

    @Override
    protected void checkAnswer(QuizButton selection) {
        super.checkAnswer(selection);
        play();
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
                        Settings.GAME_HEIGHT / 2 - Assets.enemySoldier.getHeight() / 2, enemySoldiers, 1);
                resetTime();
            }
            checkCollision();
            checkExitScreen();
            moveSoldiers();

            renderPowerUps();



        }else{
            // do nothing
        }
    }

    private void renderPowerUps() {
        batch.begin();
        batch.setProjectionMatrix(stage.getViewport().getCamera().combined);
        showPowerUps();

        batch.end();
    }

    private void showPowerUps() {
        for(int i = 0; i < streak; i++){
            batch.draw(powerUps.get(i), powerUps.get(i).getX(), powerUps.get(i).getY());
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
        Assets.playSound(Assets.loseHealth);
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
                Assets.playSound(Assets.fight);
                Soldier myCollision = mySoldiers.get(0);
                Soldier enemyCollision = enemySoldiers.get(0);
                myCollision.loseHealth();
                enemyCollision.loseHealth();
                if(myCollision.getHealth() <= 0){
                    removeSoldier(myCollision);
                    mySoldiers.remove(0);
                }
                if(enemyCollision.getHealth() <= 0) {
                    removeSoldier(enemyCollision);
                    enemySoldiers.remove(0);
                }
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

    private void removeSoldier(Soldier collision) {
        for (int i = 0; i < stage.getActors().size; i++) {
            if(stage.getActors().get(i).equals(collision)){
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
    protected void incorrectAnswer(QuizButton selection) {
        super.incorrectAnswer(selection);
        setPenalty();
        toggleButtons(false);
        resetStreak();
    }

    private void setPenalty() {
        myPenalty = 0;
        mustWait = true;
    }

    private void resetTime() {
        currentTime = 0;
    }

    @Override
    protected void correctAnswer(QuizButton selection) {
        super.correctAnswer(selection);
        powerUp();
        if(bigSoldier) {
            Assets.playSound(Assets.fanfare);
            spawnSoldier(Assets.myBigSoldier, 0 - Assets.myBigSoldier.getWidth() / 2,
                    Settings.GAME_HEIGHT / 2 - Assets.myBigSoldier.getHeight() / 2, mySoldiers, 2);
        }else if(!bigSoldier) {
            spawnSoldier(Assets.mySoldier, 0 - Assets.mySoldier.getWidth() / 2,
                    Settings.GAME_HEIGHT / 2 - Assets.mySoldier.getHeight() / 2, mySoldiers, 1);
        }else{
            //do nothing
        }
        bigSoldier = NOT_READY;
    }

    private void powerUp() {
        if(streak < MAX_STREAK - 1){
            streak++;
        }else if(streak >= MAX_STREAK - 1){
            resetStreak();
            bigSoldier = READY;
        }
    }

    private void spawnSoldier(Texture t, int x, int y, ArrayList<Soldier> soldierGroup, int health) {
        Soldier newSoldier = new Soldier(t, x, y, health);
        soldierGroup.add(newSoldier);
        stage.addActor(newSoldier);
    }

    private void resetCurrentPos(){
        setCurrentPos(0);
    }


    private void pauseGame() {
        switch(gameState){
            case GAME_RUNNING: gameState = GAME_PAUSED;
                pauseButton.setText(" >");
                toggleButtons(false);
                break;
            case GAME_PAUSED: gameState = GAME_RUNNING;
                pauseButton.setText("||");
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
        if(myLifeSprites.isEmpty()) {
            completionMessage.setText("Oh no! You lost :( Try again.");
            Assets.playSound(Assets.gameOver);
        }else if(enemyLifeSprites.isEmpty()) {
            completionMessage.setText("Hooray! You won the game.");
            Assets.playSound(Assets.victory);
        }else{
            // do nothing
        }
        super.setCompletionMessage();
    }

    @Override
    protected void startMusic() {
        Assets.playMusic(Assets.battle);
    }

    @Override
    protected void endMusic() {
        Assets.stopMusic(Assets.battle);
    }
}
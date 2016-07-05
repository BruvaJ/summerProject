package com.mygdx.game;

/**
 * Entry point to the app. Sets up assets and loads settings.
 */

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class LanguageApp extends Game {
	public SpriteBatch batcher;
	
	@Override
	public void create () {
		batcher = new SpriteBatch();
//		Settings.load();
		Assets.load();
		setScreen(new MainMenu(this));
	}

	@Override
	public void render () {
		super.render();
	}
}

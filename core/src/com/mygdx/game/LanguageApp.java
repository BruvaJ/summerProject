package com.mygdx.game;

/**
 * Entry point to the app. Sets up assets and loads settings.
 */

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class LanguageApp extends Game {

	@Override
	public void create () {
		Assets.load();
		Assets.playSound(Assets.start);
		setScreen(new MainMenu(this));
	}

	@Override
	public void render () {
		super.render();
	}
}

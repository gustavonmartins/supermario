package com.namosca.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.namosca.game.Screens.PlayScreen;

@SuppressWarnings("unused")
public class MarioBros extends Game {
	public static final int V_WIDTH = 400;
	public static final int V_HEIGHT = 280;
	public static final float PPM=100;
	public SpriteBatch batch;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		setScreen(new PlayScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}

}

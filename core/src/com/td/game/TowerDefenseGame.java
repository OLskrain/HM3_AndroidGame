package com.td.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TowerDefenseGame extends ApplicationAdapter {
	SpriteBatch batch;
	Map map;
	Monster monster;


	@Override
	public void create () {
		batch = new SpriteBatch();
		map = new Map();
		monster = new Monster(map, 0, 1240, 280);
	}

	@Override
	public void render () {
		float dt = Gdx.graphics.getDeltaTime();
		update(dt);
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		map.render(batch);
		monster.render(batch);
		batch.end();
	}

	public void update(float dt) {
		monster.update(dt);
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}

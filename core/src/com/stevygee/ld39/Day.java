package com.stevygee.ld39;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Day {
	public static final float LENGTH = 1f;

	public static boolean hasEnded;
	public static boolean active;

	private static Texture bgTex;

	static float time;

	public static void init() {
		active = true;
		hasEnded = false;
		time = 0;

		bgTex = new Texture("InselTag.png");
	}

	public static void update(float delta) {
		if( active ) {

		} else {
			// Passive
			time += delta;
			if (time >= LENGTH) {
				time = 0;

				hasEnded = true;
			} else {
				// Charging batteries
				PowerManager.update(delta);
			}

			//Gdx.app.log("Day", "Time: " + time);
		}
	}

	public static void render(SpriteBatch batch, ShapeRenderer shapeRenderer) {
		batch.begin();
		batch.draw(bgTex, 0, 0, PartyIsland.NATIVE_WIDTH, PartyIsland.NATIVE_HEIGHT, 0, 0, PartyIsland.NATIVE_WIDTH, PartyIsland.NATIVE_HEIGHT, false, false);
		batch.end();
	}

	public static void end() {
		active = false;
	}
}

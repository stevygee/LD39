package com.stevygee.ld39;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Night {
	public static final float LENGTH = 5;

	public static boolean hasEnded;
	static int powerFailures;

	static float time;

	private static Texture bgTex;

	public static void init() {
		hasEnded = false;
		powerFailures = 0;
		time = 0;

		bgTex = new Texture("InselNacht.png");
	}

	public static void update(float delta) {
		time += delta;
		if( time >= LENGTH ) {
			time = 0;

			end();
		} else {
			// Draining batteries
			if( PowerManager.isPowerOn ) {
				PowerManager.update(delta);
			}
		}

		//Gdx.app.log("Night", "Time: " + time);
	}

	public static void render(SpriteBatch batch, ShapeRenderer shapeRenderer) {
		batch.begin();
		batch.draw(bgTex, 0, 0, PartyIsland.NATIVE_WIDTH, PartyIsland.NATIVE_HEIGHT, 0, 0, PartyIsland.NATIVE_WIDTH, PartyIsland.NATIVE_HEIGHT, false, false);
		batch.end();
	}

	private static void end() {
		hasEnded = true;
	}

	public static void dispose() {
		//music.dispose();
	}
}

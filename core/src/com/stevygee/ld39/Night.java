package com.stevygee.ld39;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

public class Night {
	public static final float LENGTH = 5;

	public static boolean hasEnded;
	static int powerFailures;

	static float time;

	public static void init() {
		hasEnded = false;
		powerFailures = 0;
		time = 0;
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

	public static void render() {
		if( PowerManager.isPowerOn ) {
			Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		} else {
			Gdx.gl.glClearColor(0, 0, 0f, 1);
		}
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}

	private static void end() {
		hasEnded = true;
	}
}
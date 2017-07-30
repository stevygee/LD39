package com.stevygee.ld39;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

public class Day {
	public static final float LENGTH = 1f;

	public static boolean hasEnded;
	public static boolean active;

	static float time;

	public static void init() {
		active = true;
		hasEnded = false;
		time = 0;
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

	public static void render() {
		Gdx.gl.glClearColor(0, 0, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}

	public static void end() {
		active = false;
	}
}
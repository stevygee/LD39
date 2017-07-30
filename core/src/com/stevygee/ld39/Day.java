package com.stevygee.ld39;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

public class Day {
	public static boolean hasEnded;

	public static void init() {
		hasEnded = false;
	}

	public static void update(float delta) {

	}

	public static void render() {
		Gdx.gl.glClearColor(0, 0, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}

	public static void end() {
		hasEnded = true;
	}
}
